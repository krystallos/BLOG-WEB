package com.example.fileConfig.web;

import com.example.codeFile.enity.CodeFileEnity;
import com.example.fileConfig.service.FileLookService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *二维码查看页
 * @author Enoki
 */
@RestController
public class FileLookConteroller {

    private static final Logger log = Logger.getLogger(FileLookConteroller.class);

    @Resource
    private FileLookService fileLookService;

    /**
     * 文件配置路径查询
     */
    @Log(title = "获取二维码文件路径列表", type = LogEnum.SELECT)
    @PostMapping("api/fileCodeLook.act")
    public ResultBody fileCodeLook(@RequestBody CodeFileEnity codeFileEnity){
        try{
            codeFileEnity.pubImplPage(codeFileEnity.getNowTab(),codeFileEnity.getHasTab());
            List<CodeFileEnity> listSize = fileLookService.selectFileLookTab(codeFileEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, listSize, (int) new PageInfo<>(listSize).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

}
