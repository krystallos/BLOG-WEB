package com.example.mineBlos.myBlosTab.web.app;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.service.MineBlosService;
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
 * 个人博客
 * @author Enoki
 */
@RestController
public class MineBlosAppConteroller {

    private static final Logger log = Logger.getLogger(MineBlosAppConteroller.class);

    @Resource
    private MineBlosService mineBlosService;
    @Resource
    private requestUTF requestUTF;


    /**
     * 个人博客首页列表
     * @param request
     * @param response
     * @param mineBlos type类型1是最近消息，2是文章，3是资源更新，4是收藏
     */
    @PostMapping("api/mineBlosTabApp.act")
    public ResultBody mineBlosTabApp(HttpServletRequest request, HttpServletResponse response,@RequestBody MineBlos mineBlos){
        try{
            requestUTF.uTFonE(request,response);
            mineBlos.pubImplPage(mineBlos.getNowTab(),mineBlos.getHasTab());
            int total = mineBlosService.countMineBlosSysLook(mineBlos);
            List<MineBlos> listTab = total == 0? new ArrayList<>(): mineBlosService.mineBlosSysLook(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, total);
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

    /**
     * 开启一个新的博客页面
     */
    @PostMapping("api/mineBlosSemmsidApp.act")
    public ResultBody mineBlosSemmsidApp(HttpServletRequest request, HttpServletResponse response,@RequestBody MineBlos mineBlos){
        try {
            requestUTF.uTFonE(request,response);
            MineBlos mineBlosType = mineBlosService.selectMineBlosSemmIn(mineBlos);
            if(mineBlosType == null){
                log.error("无博客类型");
                return new ResultBody(ApiResultEnum.FAIL,"该文章已被删除");
            }
            return new ResultBody(ApiResultEnum.SUCCESS,mineBlosType);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR,null);
        }
    }

}
