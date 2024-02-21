package com.example.api.randomImg.web;

import com.example.api.randomImg.enity.FileScore;
import com.example.api.randomImg.enity.GetFileRandom;
import com.example.fileConfig.service.FileConfigService;
import com.example.api.randomImg.enity.FileRandom;
import com.example.util.*;
import com.example.util.annotion.Log;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 文件控制页
 * @author Enoki
 */
@RestController
public class FileRandomConteroller {

    private static final Logger log = Logger.getLogger(FileRandomConteroller.class);

    @Resource
    private FileConfigService fileConfigService;

    /*随机图片*/
    @Log(title = "随机图片（公开）", type = LogEnum.SELECT)
    @PostMapping("open/fileRandom.act")
    public ResultBody fileConfigTab(@RequestBody FileRandom fileRandom){
        try{
            GetFileRandom getFileRandom = new GetFileRandom();
            getFileRandom.setIsHas(fileRandom.getType());
            getFileRandom.setTagName(fileRandom.getTagName());
            getFileRandom.setCreateDate((new Date().getTime() - 2000000000) + "");
            getFileRandom.setPathName(fileRandom.getPathName());
            getFileRandom.setAuthorId(fileRandom.getAuthorId());
            List<FileRandom> item = fileConfigService.selectRandomImg(getFileRandom);
            for(FileRandom filR: item){
                List<String> tagList = new ArrayList<>();
                if(filR.getTagName() != null && filR.getTagName().split(",").length > 0){
                    tagList.addAll(Arrays.asList(filR.getTagName().split(",")));
                }
                filR.setTagNameList(tagList);
                Map<String, Object> map = fileConfigService.selectRandomScore(filR.getIds());
                filR.setAvgScore(map != null ? Integer.parseInt(map.get("AVGSCORE").toString()) : 0);
                filR.setCountImg(map != null ? Integer.parseInt(map.get("COUNTID").toString()) : 0);
            }
            fileRandom.setPathName(getFileRandom.getPathName());

            return new ResultBody(ApiResultEnum.SUCCESS, item);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*评分*/
    @Log(title = "图片评分（公开）", type = LogEnum.INSERT)
    @PostMapping("open/scoreImage.act")
    public ResultBody scoreImage(HttpSession session, @RequestBody FileScore fileScore){
        try{
            fileScore.setIds(rsaKey.uuid(""));
            fileScore.setCreateDate(new Date().getTime() + "");
            fileScore.setSessionId(session.getId());
            fileConfigService.insertFileScore(fileScore);
            return new ResultBody(ApiResultEnum.SUCCESS, "感谢您的评价");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*添加tag*/
    @Log(title = "给图片添加标签（公开）", type = LogEnum.INSERT)
    @PostMapping("open/saveImageTag.act")
    public ResultBody saveImageTag(@RequestBody FileRandom fileRandom){
        try{
            //fileConfigService.updateImageTag(fileRandom);
            return new ResultBody(ApiResultEnum.SUCCESS, "感谢您的添加");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
