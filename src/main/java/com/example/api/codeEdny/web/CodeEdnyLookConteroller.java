package com.example.api.codeEdny.web;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.codeFile.service.CodeFileService;
import com.example.fileConfig.enity.FileUtil;
import com.example.fileConfig.service.FileConfigService;
import com.example.util.*;
import com.example.util.annotion.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.*;

/**
 * 二维码解码控制页
 * @author Enoki
 */
@RestController
public class CodeEdnyLookConteroller {

    @Resource
    private CodeFileService codeFileService;
    @Resource
    private FileConfigService fileConfigService;

    /*二维码查看列表*/
    @Log(title = "分享图片查询列表（公开）", type = LogEnum.SELECT)
    @PostMapping("open/codeFileLook.act")
    public ResultBody codeFileEdny(@RequestBody CodeFileEnity codeFileEnity){
        if(codeFileEnity == null || "".equals(codeFileEnity.getIds())){
            return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "链接不合法，请联系管理员获取正确链接");
        }
        codeFileEnity = codeFileService.getCodeFile(codeFileEnity);
        List<FileUtil> fileRandomList = fileConfigService.selectIdListImg(codeFileEnity.getCodeMind());
        return new ResultBody(ApiResultEnum.SUCCESS, fileRandomList);
    }

}
