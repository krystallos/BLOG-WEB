package com.example.upload;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.codeFile.service.CodeFileService;
import com.example.fileConfig.enity.fiction.FictionBook;
import com.example.fileConfig.enity.FileConfig;
import com.example.fileConfig.service.FictionFileService;
import com.example.fileConfig.web.FileConfigConteroller;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.upload.enity.FileVo;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传统一入口
 * @author Enoki
 */
@RestController
public class UploadConteroller {
    private static final Logger log = Logger.getLogger(UploadConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private CodeFileService codeFileService;
    @Resource
    private FileConfigConteroller fileConfigConteroller;
    @Resource
    private FictionFileService fictionFileService;

    /**
     * blosItem上传接口
     * blosItem --> imgBlos
     */
    @Log(title = "博客图片上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/blosItemUpload.act")
    public ResultBody blosItemUpload(@RequestParam("file") MultipartFile file){
        try {
            Map<String, String> hasMap = new HashMap<>();
            Date date = new Date();
            String ids = (new Random().nextInt(3) + System.currentTimeMillis())+"";
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
            File fileIn = new File(redisUtils.getConfig(ConfigDicEnum.assessBlosImg.message) + dateStr);
            addNewFileDirectory(fileIn);
            if (null != file.getOriginalFilename()) {
                ids += file.getOriginalFilename().lastIndexOf(".") == 1 ? ".notSub" : file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            newCreateStream(file, redisUtils.getConfig(ConfigDicEnum.assessBlosImg.message) + dateStr+"/"+ids);
            hasMap.put("dateStr",dateStr);
            hasMap.put("ids",ids);
            return new ResultBody(ApiResultEnum.SUCCESS, "/blosBoot/imgBlos/" + hasMap.get("dateStr") +"/"+ hasMap.get("ids"));
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * assFileCode上传接口(只带地址)
     * assFileCode --> assFileCode
     */
    @Log(title = "二维码图片上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/assFileCodeUpload.act")
    public ResultBody assFileCodeUpload(@RequestParam("file") MultipartFile file){
        try{
            long millis = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当日时间生成文件夹
            String nowDate = sf.format(new Date()).replace("-","");
            // 文件上传后的路径
            File fileIn = new File(redisUtils.getConfig(ConfigDicEnum.assFileCode.message) + nowDate);
            addNewFileDirectory(fileIn);
            newCreateStream(file,redisUtils.getConfig(ConfigDicEnum.assFileCode.message) + nowDate + "/"+ millis + file.getOriginalFilename());

            return new ResultBody(ApiResultEnum.SUCCESS, nowDate + "/"+ millis + file.getOriginalFilename());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * tempFile上传接口（限定图片识别）
     * tempFile --> tempFile
     */
    @Log(title = "图片识别上传", type = LogEnum.UPLOAD)
    @PostMapping("open/tempFileUpLoadCode.act")
    public ResultBody tempFileUpLoadCode(@RequestParam("file") MultipartFile file){
        try {
            //文件名称（用于存入数据库）
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String fileNameItem = sf.format(new Date()).replace("-","");
            //文件路径
            String locItem = redisUtils.getConfig(ConfigDicEnum.tempFile.message) + fileNameItem;
            File fileIn = new File(locItem);
            //转比特在转BASE64在转MD5
            byte[] bas = file.getBytes();
            String base64OrMd5 = new String(Base64.encodeBase64(bas));
            base64OrMd5 = Md5Config.getMd5(base64OrMd5);

            CodeFileEnity bo = new CodeFileEnity();
            bo.setCodeMd5(base64OrMd5);
            CodeFileEnity co = codeFileService.getCodeFile(bo);
            if(co != null){
                return new ResultBody(ApiResultEnum.SUCCESS, co.getPath());
            }else {
                addNewFileDirectory(fileIn);
                String lastFileName = "";
                if (null != file.getOriginalFilename()) {
                    if(file.getOriginalFilename().lastIndexOf(".") == -1){
                        lastFileName = ".jpg";
                    }else{
                        lastFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    }
                }
                String mills = System.currentTimeMillis() + lastFileName;
                locItem += "/" + mills;
                newCreateStream(file,locItem);

                CodeFileEnity codeFileEnity = new CodeFileEnity();
                codeFileEnity.setIds(rsaKey.uuid(null));
                codeFileEnity.setCodeMind(mills);
                codeFileEnity.setCodeType("1");
                codeFileEnity.setCodeMd5(base64OrMd5);
                codeFileEnity.setCreateDate(codeFileEnity.getNowDate(null));
                codeFileEnity.setCreateId("0");
                codeFileEnity.setPath(fileNameItem + "/" + mills);
                codeFileService.insertCodeFile(codeFileEnity);
                return new ResultBody(ApiResultEnum.SUCCESS, locItem.replace(redisUtils.getConfig(ConfigDicEnum.tempFile.message), ""));
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * tempFile上传接口(通用)
     * tempFile --> tempFile
     */
    @Log(title = "基础图片上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/tempFileUpLoad.act")
    public ResultBody tempFileUpLoad(@RequestParam("file") MultipartFile file){
        try {
            //文件名称（用于存入数据库）
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String fileNameItem = sf.format(new Date()).replace("-","");
            //文件路径
            String locItem = redisUtils.getConfig(ConfigDicEnum.tempFile.message) + fileNameItem;
            File fileIn = new File(locItem);
            addNewFileDirectory(fileIn);
            String lastFileName = "";
            if (null != file.getOriginalFilename()) {
                lastFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            String mills = System.currentTimeMillis() + lastFileName + "";
            locItem += "/" + mills;
            newCreateStream(file, locItem);
            FileVo vo = FileVo.builder().fileName(fileNameItem + "/" + mills).filePath(fileNameItem).build();
            return new ResultBody(ApiResultEnum.SUCCESS, "上传成功", vo);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * P站图片收藏上传
     * accessFile --> img
     */
    @Log(title = "PIXIV图片上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/accessFileUpload.act")
    public ResultBody accessFileUpload(@RequestParam("file") MultipartFile file, FileConfig fileConfig, HttpSession session){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            // 文件上传后的路径
            File fileIn = new File(fileConfig.getLonPathNameType());
            addNewFileDirectory(fileIn);
            //获取输入流
            InputStream input = file.getInputStream();
            //建立输出流
            FileOutputStream fileOutputStream = new FileOutputStream(fileConfig.getLonPathNameType()+"/"+file.getOriginalFilename());//创建文件输出流

            byte[] buffer = new byte[2048];//建立文件缓冲，由2048比特构成
            int len = 0;//结束标识符
            while((len=input.read(buffer))>0) {
                fileOutputStream.write(buffer,0,len);
            }
            input.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            String itemPath = fileIn.getAbsolutePath().replace(redisUtils.getConfig(ConfigDicEnum.accessFile.message),"");
            File fileInItem = new File(redisUtils.getConfig(ConfigDicEnum.assessImgFile.message) + itemPath);
            addNewFileDirectory(fileInItem);
            fileConfigConteroller.hasImgThumbna(fileIn.getAbsolutePath() + "/" + file.getOriginalFilename(),redisUtils.getConfig(ConfigDicEnum.assessImgFile.message) + itemPath + "/" + file.getOriginalFilename());
            if(person != null){
                fileConfig.setPsnId(person.getIds());
                fileConfig.setFileName(file.getOriginalFilename());
                fileConfigConteroller.insertFileTab(fileConfig);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "上传成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * fictionImg上传接口（只带地址）
     * fictionImg --> fictionImg
     */
    @Log(title = "小说图片上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/fictionImgUpload.act")
    public ResultBody fictionImgUpload(@RequestParam("file") MultipartFile file){
        try {
            Map<String, String> hasMap = new HashMap<>();
            String ids = (new Random().nextInt(3) + System.currentTimeMillis())+"";
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File fileIn = new File(redisUtils.getConfig(ConfigDicEnum.fictionImg.message) + dateStr);
            addNewFileDirectory(fileIn);
            if(file.getOriginalFilename() != null){
                ids += file.getOriginalFilename().lastIndexOf(".")==1?".notSub":file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }else{
                ids += ".notSub";
            }
            newCreateStream(file, redisUtils.getConfig(ConfigDicEnum.fictionImg.message) + dateStr+"/"+ids);
            hasMap.put("dateStr",dateStr);
            hasMap.put("ids",ids);
            return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * fictionFile上传接口（只带地址）
     * fictionFile --> fictionFile
     */
    @Log(title = "小说文件上传", type = LogEnum.UPLOAD)
    @PostMapping("upload/fictionFileUpload.act")
    public ResultBody fictionFileUpload(@RequestParam("file") MultipartFile file,HttpSession session, FictionBook fictionBook){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            Map<String, String> hasMap = new HashMap<>();
            String ids = (new Random().nextInt(3) + System.currentTimeMillis())+"";
            File fileIn = new File(redisUtils.getConfig(ConfigDicEnum.fictionFile.message) + fictionBook.getFictionId());
            addNewFileDirectory(fileIn);
            if(file.getOriginalFilename() != null){
                ids += file.getOriginalFilename().lastIndexOf(".")==1?".notSub":file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }else{
                ids += ".notSub";
            }
            newCreateStream(file, redisUtils.getConfig(ConfigDicEnum.fictionFile.message) + fictionBook.getFictionId()+"/"+ids);
            hasMap.put("fictionId",fictionBook.getFictionId());
            hasMap.put("ids",ids);

            FictionBook book = new FictionBook();
            book.getNowDate("");
            book.setFictionId(fictionBook.getFictionId());
            book.setFictionBookName(ids);
            book.setCreateId(person.getIds());
            book.setFictionOriginName(file.getOriginalFilename());
            fictionFileService.insertFictionMain(book);

            return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 创建文件夹
     */
    private void addNewFileDirectory(File fileIn){
        if (!fileIn.exists() && !fileIn.isDirectory()) {
            log.info("找不到" + fileIn + "目录，正在自行建立");
            boolean wasSuccessful = fileIn.mkdirs();//支持自主生成全新目录
            if(wasSuccessful) {
                log.info("--------------------------" + fileIn + "完整图片目录生成成功-------------------------");
            }else{
                log.info("--------------------------" + fileIn + "完整图片目录生成失败！-------------------------");
            }
        }
    }

    /**
     * stream输入流建立
     */
    private void newCreateStream(MultipartFile file, String locItem) throws IOException{
        //建立输出流
        FileOutputStream fileOutputStream = new FileOutputStream(locItem);//创建文件输出流
        //获取输入流
        InputStream input = file.getInputStream();
        byte[] buffer = new byte[2048];//建立文件缓冲，由2048比特构成
        int len = 0;//结束标识符
        while ((len = input.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, len);
        }
        input.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        //流结束
    }

}
