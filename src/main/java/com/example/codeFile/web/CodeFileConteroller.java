package com.example.codeFile.web;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.codeFile.service.CodeFileService;
import com.example.emil.web.MineEmilConteroller;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 二维码控制页
 * @author Enoki
 */
@RestController
public class CodeFileConteroller {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private CodeFileService codeFileService;

    @Value("${assFileCode}")
    private String assFileCode;
    @Value("${assessUrlBlos}")
    private String assessUrlBlos;

    private static final Logger log = Logger.getLogger(CodeFileConteroller.class);

    /*生成二维码并返回二维码路径*/
    @Log(title = "生成二维码并返回二维码URL", type = LogEnum.INSERT)
    @PostMapping("api/codeFile.act")
    public ResultBody codeFile(HttpSession httpSession, @RequestBody CodeFileEnity codeFileEnity){
        try {
            Person person = (Person)redisUtils.get(httpSession.getId());
            String passType = codeFileEnity.getPassWord();
            //生成MD5码
            codeFileEnity.setCodeMd5(Md5Config.getMd5(codeFileEnity.getCodeMind()));
            //获取是否有重复的MD5，有的话就不生成
            CodeFileEnity cf = codeFileService.getCodeFile(codeFileEnity);
            if(cf != null){
                codeFileService.updatePassWord(cf.getCodeMd5(),"1".equals(passType)?cf.getCodeMd5():"");
                CodeFileEnity vo = new CodeFileEnity();
                vo.setPath(cf.getPath());
                vo.setCodeMd5(cf.getCodeMd5());
                vo.setUrl(cf.getIds());
                return new ResultBody(ApiResultEnum.SUCCESS, vo);
            }
            if(passType!=null && passType.equals("1")){
                codeFileEnity.setPassWord(codeFileEnity.getCodeMd5());
            }else{
                codeFileEnity.setPassWord("");
            }
            //获取本地化路径
            String url = assessUrlBlos + "codeFileLook.act";
            String assFileCode = this.assFileCode;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当日时间生成文件夹
            String nowDate = sf.format(new Date()).replace("-","") ;
            //将后面的相对路径保存给数据库
            String itemLen = nowDate + "/" + System.currentTimeMillis() + ".jpg";
            //生成指定的二维码路径
            String code = assFileCode + itemLen;
            //先做插入数据获取ID，后续将路径插入
            codeFileEnity.setPath(itemLen);
            codeFileEnity = itemFileCodeOpen(codeFileEnity,person.getIds());
            url += "?ids=" + codeFileEnity.getIds();
            File fileInItem = new File(code);
            if(!fileInItem.exists() && !fileInItem.isDirectory()){
                log.info("找不到" + code + "目录，正在自行建立");
                boolean newMkdirs = fileInItem.mkdirs();//支持自主生成全新目录
                log.info("--------------------------"+fileInItem+"缩略图目录生成成功-------------------------");
            }
            /*开始生成二维码*/
            Path path = FileSystems.getDefault().getPath(code);
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix;
            try {
                matrix = writer.encode(url, BarcodeFormat.QR_CODE,500 , 500);
                //已生成二维码
                MatrixToImageWriter.writeToPath(matrix,"JPG",path);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            CodeFileEnity vo = new CodeFileEnity();
            vo.setPath(itemLen);
            vo.setCodeMd5(codeFileEnity.getCodeMd5());
            vo.setUrl(codeFileEnity.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, vo);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 二维码数据优先插入数据库并返回插入数据
     * @return
     */
    public CodeFileEnity itemFileCodeOpen(CodeFileEnity codeFileEnity,String pid){
        codeFileEnity.setCodeType("0");
        codeFileEnity.setCreateId(pid);
        String ids = rsaKey.uuid(null);
        codeFileEnity.setIds(ids);
        codeFileEnity.setCreateDate(codeFileEnity.getNowDate(null));
        codeFileService.insertCodeFile(codeFileEnity);
        codeFileEnity.setIds(ids);
        return codeFileEnity;
    }

}