package com.example.fileConfig.web.app;

import com.example.fileConfig.enity.FileGroupFa;
import com.example.fileConfig.enity.FileConfig;
import com.example.fileConfig.service.FileConfigService;
import com.example.nachrichten.web.app.NachrichtenAppConteroller;
import com.example.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件控制页
 * @author Enoki
 */
@RestController
public class FileConfigAppConteroller {

    private static final Logger log = Logger.getLogger(FileConfigAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private FileConfigService fileConfigService;

    /**
     * 查询文件
     */
    @PostMapping("api/fileSelectApp.act")
    public ResultBody fileSelectApp(HttpServletRequest request, HttpServletResponse response,@RequestBody FileConfig fileConfig){
        try {
            requestUTF.uTFonE(request,response);
            int total = fileConfigService.countSelectFileConfigGroup(fileConfig);
            List<FileGroupFa> fileConfigList = total == 0?new ArrayList<>(): fileConfigService.selectFileConfigGroup(fileConfig);
            return new ResultBody(ApiResultEnum.SUCCESS, fileConfigList, total);
        }catch (Exception e) {
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

    @PostMapping("api/filePathSelectApp.act")
    public ResultBody filePathSelectApp(HttpServletRequest request, HttpServletResponse response,@RequestBody FileConfig fileConfig){
        try {
            requestUTF.uTFonE(request,response);
            int total = fileConfigService.selectFileConfigInt(fileConfig);
            fileConfig.pubImplPage(fileConfig.getNowTab(),fileConfig.getHasTab());
            List<FileConfig> fileConfigList = total == 0?new ArrayList<>(): fileConfigService.selectFileConfigTab(fileConfig);
            return new ResultBody(ApiResultEnum.SUCCESS, fileConfigList, total);
        }catch (Exception e) {
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

}
