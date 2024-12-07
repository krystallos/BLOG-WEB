package com.example.fileConfig.web;

import com.example.fileConfig.enity.*;
import com.example.fileConfig.service.FileConfigService;
import com.example.fileConfig.service.FileUtilService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件控制页
 * @author Enoki
 */
@RestController
public class FileConfigConteroller {

    private static final Logger log = Logger.getLogger(FileConfigConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private FileConfigService fileConfigService;
    @Resource
    private FileUtilService fileUtilService;

    /*查询*/
    @Log(title = "查询图片列表", type = LogEnum.SELECT)
    @PostMapping("api/fileImageTab.act")
    public ResultBody fileImageTab(@RequestBody FileConfig fileConfig){
        try{
            fileConfig.pubImplPage(fileConfig.getNowTab(),fileConfig.getHasTab());
            fileConfig.setLonPathNameType("/img");
            fileConfig.setThumbnail("/imgItem");
            int total = fileConfigService.selectFileConfigCount(fileConfig);
            List<FileConfig> tab = total == 0 ? new ArrayList<>() : fileConfigService.selectFileConfigTab(fileConfig);
            for(FileConfig filR: tab){
                List<String> tagList = new ArrayList<>();
                if(filR.getTagName() != null && filR.getTagName().split(",").length > 0){
                    tagList.addAll(Arrays.asList(filR.getTagName().split(",")));
                }
                filR.setTagNameList(tagList);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, tab, total);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*查询*/
    @Log(title = "获取图片全部信息", type = LogEnum.SELECT)
    @PostMapping("api/getfileImageDetial.act")
    public ResultBody getfileImageDetial(@RequestBody FileUnit fileUnit){
        try{
            FileDetial detial = fileConfigService.selectDetialImage(fileUnit);
            return new ResultBody(ApiResultEnum.SUCCESS, detial);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*同步文件*/
    @Log(title = "同步文件数据", type = LogEnum.EDIT)
    @PostMapping("api/fileReady.act")
    public ResultBody fileReady(HttpSession httpSession,@RequestBody FileConfig fileConfig){
        try {

            Person person = (Person)redisUtils.get(httpSession.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            FileUtil fileUtil = new FileUtil();
            fileUtil.setPsnId(person.getIds());
            fileUtil.setIsValid("1");
            List<FileUtil> files = fileUtilService.selectFileUtilNoTab(fileUtil);//查询数据
            if(files.size()==0 || StringBlack.isBlackString(files.get(0).getFilePath())){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "请去文件路径设置内设置自己的文件管理路径后即可使用!");
            }
            //先做删除操作
            fileConfig.setPsnId(person.getIds());
            fileConfigService.delPsnImg(fileConfig);
            String locItem = redisUtils.getConfig(ConfigDicEnum.accessFile.message);
            String assessItem = redisUtils.getConfig(ConfigDicEnum.assessImgFile.message);
            int sqlNull = 0;
            for(FileUtil item : files) {//更新文件路径循环
                fileConfig.setPathName(item.getFilePath());
                fileConfig.setIsHas(item.getIsHas());
                File f = new File(fileConfig.getPathName());
                if (f.isDirectory()) {
                    sqlNull += recFile(fileConfig,locItem,assessItem,item.getIds());
                } else {
                    System.out.format("{\"name\" : \"%s\"}", f.getName());
                }
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "更新完成，共计更新" + sqlNull + "份文件");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 文件路径保存处理
     */
    public int recFile(FileConfig fileConfig, String locItem, String assessImgFile, String pathId){
        int returnA = 0;
        List<FileConfig> unionAllList = new ArrayList<>();
        File file = new File(fileConfig.getPathName());
        for(File f: file.listFiles()){
            if(f.isDirectory()){
                fileConfig.setPathName(f.getPath());
                returnA += recFile(fileConfig,locItem,assessImgFile,pathId);
            }else if(f.isFile()){
                String itemPath = f.getParentFile().toString().replace(locItem,"");
                FileConfig fileConfigItem = new FileConfig();
                fileConfigItem.setFileName(f.getName());//HtmlEscape.escapeHtml4
                fileConfigItem.setPathName(itemPath);
                fileConfigItem.setFileType(f.getName().substring(f.getName().lastIndexOf(".")+1));
                fileConfigItem.setPsnId(fileConfig.getPsnId());
                fileConfigItem.setCreateId(fileConfig.getPsnId());
                fileConfigItem.setIsHas(fileConfig.getIsHas());
                fileConfigItem.setIds(rsaKey.uuid(null));
                fileConfigItem.setDelFlag("0");
                fileConfigItem.setThumbnail(itemPath);
                fileConfigItem.setPathId(pathId);
                unionAllList.add(fileConfigItem);
                returnA++;
                File fileIn = new File(assessImgFile + itemPath);
                if(!fileIn.exists() && !fileIn.isDirectory()){
                    log.info("找不到" + fileIn + "目录，正在自行建立");
                    fileIn.mkdirs();//支持自主生成全新目录
                    log.info("--------------------------"+fileIn+"目录生成成功-------------------------");
                }
                hasImgThumbna(f.getAbsolutePath(),assessImgFile + itemPath + "/" + f.getName());
                //fileConfigService.insertFileConfig(fileConfigItem);
            }
        }
        fileConfig.setPathName(null);
        if(unionAllList.size()!=0) {
            fileConfigService.insertFileAllConfig(unionAllList);
        }
        return returnA;
    }

    /**
    * 文件上传路径选择
    */
    @Log(title = "获取文件上传路径", type = LogEnum.SELECT)
    @PostMapping("api/uploadImageItem.act")
    public ResultBody uploadImageItem(HttpSession session,@RequestBody FileEnityUt fileEnityUt){
        Person person = (Person)redisUtils.get(session.getId());
        if(person == null){//无token
            return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
        }
        FileUtil fileUtil = new FileUtil();
        fileUtil.setPsnId(person.getIds());
        List<FileUtil> fileUtilList = fileUtilService.selectFileUtilNoTab(fileUtil);
        if(fileUtilList == null || fileUtilList.size()==0){
            return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "无配置文件路径");
        }
        fileEnityUt.setListPath(fileUtilList);
        return new ResultBody(ApiResultEnum.SUCCESS, fileEnityUt);
    }

    public void insertFileTab(FileConfig fileConfig){
        List<FileConfig> unionAllList = new ArrayList<>();
        String item = fileConfig.getLonPathNameType();
        fileConfig.setIds(rsaKey.uuid(null));
        fileConfig.setFileType(fileConfig.getFileName().substring(fileConfig.getFileName().lastIndexOf(".")+1));
        fileConfig.setPathName(item.replace(redisUtils.getConfig(ConfigDicEnum.accessFile.message),""));
        fileConfig.setDelFlag("0");
        fileConfig.getUuidCreateUpdate(fileConfig.getPsnId());
        fileConfig.getNowDate("");
        fileConfig.setThumbnail(item.replace(redisUtils.getConfig(ConfigDicEnum.accessFile.message),""));
        unionAllList.add(fileConfig);
        fileConfigService.insertFileAllConfig(unionAllList);
    }

    /**
     * 生成缩略图
     * @param oldFile
     * @param newFile
     */
    public void hasImgThumbna(String oldFile, String newFile){
        try {
            Thumbnails.of(oldFile).size(150, 250).toFile(newFile);
        }catch (IOException e){
            log.info("同步" + oldFile + "文件的时候出现错误，错误：" + e.getMessage());
        }
    }

    /**
     * 删除文件
     * @return
     */
    @Log(title = "删除文件", type = LogEnum.DELETE)
    @PostMapping("api/fileDelOk.act")
    public ResultBody fileDelOk(HttpSession session,@RequestBody DelFileVo delFileVo){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            //------------------------处理文件路径----------------------------
            String[] idsItem = delFileVo.getIds().toArray(new String[delFileVo.getIds().size()]);
            List<String> item = fileConfigService.selectListPathImg(idsItem);
            String[] pathItem = item.toArray(new String[item.size()]);
            //------------------------处理文件路径----------------------------
            int a = 0;
            for(String forItem : pathItem){
                File file = new File(redisUtils.getConfig(ConfigDicEnum.accessFile.message) + forItem);
                File fileThumbnail = new File(redisUtils.getConfig(ConfigDicEnum.assessImgFile.message) + forItem);
                if(file.isDirectory() || fileThumbnail.isDirectory()){
                    continue;
                }
                boolean fileType = file.delete();
                boolean fileThumbnailType = fileThumbnail.delete();
                if(!fileType || !fileThumbnailType ){
                    a++;
                }
            }
            fileConfigService.delFlagFileAllConfig(idsItem,person.getIds());//物理删除
            return new ResultBody(ApiResultEnum.SUCCESS, a == 0?"删除完成":"删除完成，其中有" + a + "项文件因被占用删除失败");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /**
     * 人工提交页
     */
    @Log(title = "手动提交文件信息", type = LogEnum.SELECT)
    @PostMapping(value = "api/getWorkImageTemp.act")
    public ResultBody getWorkImageTemp(HttpSession session, @RequestBody FileEnityUt fileEnityUt){
        try {
            fileEnityUt = (FileEnityUt)redisUtils.get(redisUtils.getConfig(ConfigDicEnum.manualOperation.message));
            FileUtil fileUtil = new FileUtil();
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            fileUtil.setPsnId(person.getIds());
            List<FileUtil> listUtil = fileUtilService.selectFileUtilNoTab(fileUtil);
            if(fileEnityUt == null) fileEnityUt = new FileEnityUt();
            fileEnityUt.setListPath(listUtil);
            fileEnityUt.setFileName("");
            return new ResultBody(ApiResultEnum.SUCCESS, fileEnityUt);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 人工保存接口
     */
    @Log(title = "手动保存文件信息", type = LogEnum.INSERT)
    @PostMapping("api/fileGetHandWorkSaveFile.act")
    public ResultBody fileGetHandWorkSaveFile(@RequestBody FileEnityUt fileEnityUt, HttpSession session){
        try {
            Person list = (Person)redisUtils.get(session.getId());
            if(list == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            FileConfig fileConfig = new FileConfig();
            fileConfig.setPsnId(list.getIds());
            fileConfig.setPsnName(list.getPsnName());
            fileConfig.setCreateId(list.getIds());
            redisUtils.set(redisUtils.getConfig(ConfigDicEnum.manualOperation.message), fileEnityUt);
            FileUtil fileUtil = new FileUtil();
            fileUtil.setIds(fileEnityUt.getFilePath());
            fileUtil = fileUtilService.get(fileUtil);
            fileConfig.setFileType(fileEnityUt.getFileType());
            fileConfig.setIsHas(fileEnityUt.getIsHas());
            fileConfig.setPathId(fileEnityUt.getFilePath());
            fileConfig.setPathName(fileUtil.getFilePath().replace(redisUtils.getConfig(ConfigDicEnum.accessFile.message),"") + "\\" + fileEnityUt.getPathName());
            fileConfig.setThumbnail(fileConfig.getPathName());
            fileConfig.setFileName(fileEnityUt.getFileName() + "." + fileConfig.getFileType());
            fileConfigService.insertFileConfig(fileConfig);
            return new ResultBody(ApiResultEnum.SUCCESS, "添加成功");
        }catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }


}
