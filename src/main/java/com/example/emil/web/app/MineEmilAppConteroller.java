package com.example.emil.web.app;

import com.example.emil.enity.MineEmil;
import com.example.emil.serivce.MineEmilService;
import com.example.login.service.LoginService;
import com.example.nachrichten.web.app.NachrichtenAppConteroller;
import com.example.util.*;
import com.example.util.config.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件控制页
 * @author Enoki
 */
@RestController
public class MineEmilAppConteroller {

    private static final Logger log = Logger.getLogger(MineEmilAppConteroller.class);

    @Resource
    private MineEmilService mineEmilService;
    @Resource
    private requestUTF requestUTF;

    /*分页查询*/
    @PostMapping("api/mineEmilAppTab.act")
    public ResultBody mineEmilAppTab(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestBody MineEmil mineEmil){
        try {
            requestUTF.uTFonE(request,response);
            mineEmil.pubImplPage(mineEmil.getNowTab(),mineEmil.getHasTab());
            int total = mineEmilService.selectPageNoEmilInt(mineEmil);
            List<MineEmil> tab = total == 0 ? new ArrayList<>(): mineEmilService.selectPageEmil(mineEmil);
            return new ResultBody(ApiResultEnum.SUCCESS,tab,total);
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

    /*指定查询*/
    @PostMapping("api/getMineEmilApp.act")
    public ResultBody getMineEmilApp(HttpServletRequest request, HttpServletResponse response,@RequestBody MineEmil mineEmil){
        try {
            requestUTF.uTFonE(request, response);
            mineEmil.setIsAll("1");
            MineEmil itemMine = mineEmilService.getMineEmil(mineEmil);
            return new ResultBody(ApiResultEnum.SUCCESS,itemMine);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

}