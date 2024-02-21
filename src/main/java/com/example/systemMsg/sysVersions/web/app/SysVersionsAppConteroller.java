package com.example.systemMsg.sysVersions.web.app;

import com.example.systemMsg.sysVersions.enity.SysVersions;
import com.example.systemMsg.sysVersions.enity.SysVersionsApp;
import com.example.systemMsg.sysVersions.service.SysVersionsService;
import com.example.util.*;
import com.example.util.config.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 系统版本迭代通知页
 * @author Enoki
 */
@RestController
public class SysVersionsAppConteroller {

    private final Logger logger = LoggerFactory.getLogger(SysVersionsAppConteroller.class);

    @Resource
    private SysVersionsService sysVersionsService;
    @Resource
    private requestUTF requestUTF;

    /**
     * 系统更新通知
     * @param request
     * @param response
     */
    @PostMapping("api/sysVersionsAppTab.act")
    public ResultBody sysVersionsAppTab(HttpServletRequest request, HttpServletResponse response){
        try {
            requestUTF.uTFonE(request,response);
            SysVersions tab = sysVersionsService.selectOneAndOneSysVersions(0);
            return new ResultBody(ApiResultEnum.SUCCESS,tab);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR,null);
    }

    @PostMapping("api/sysUpdateVersionsApp.act")
    public ResultBody sysUpdateVersionsApp(HttpServletRequest request, HttpServletResponse response){
        try {
            requestUTF.uTFonE(request,response);
            SysVersionsApp tab = sysVersionsService.getNewUpdateAppVersion();
            return new ResultBody(ApiResultEnum.SUCCESS,tab);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR,null);
    }

}
