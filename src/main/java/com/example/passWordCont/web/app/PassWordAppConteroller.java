package com.example.passWordCont.web.app;

import com.example.passWordCont.enity.PassWord;
import com.example.passWordCont.service.PassWordService;
import com.example.passWordCont.web.PassWordConteroller;
import com.example.util.*;
import com.example.util.config.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 密码本
 * @author Enoki
 */
@RestController
public class PassWordAppConteroller {

    private static final Logger log = Logger.getLogger(PassWordAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private PassWordService passWordService;
    @Resource
    private PassWordUtil passWordUtil;

    /**
     * 密码本首页列表
     *
     * @param request
     * @param response
     * @param passWord 密码本实体
     */
    @PostMapping("api/passWordMineApp.act")
    public ResultBody passWordMineApp(HttpServletRequest request, HttpServletResponse response,@RequestBody PassWord passWord) {
        try {
            requestUTF.uTFonE(request, response);
            passWord.pubImplPage(passWord.getNowTab(), passWord.getHasTab());
            List<PassWord> listTab = passWordService.passWordMineTab(passWord);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab);
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
        }
    }

    /**
     * 密码校对。查看密码
     * @param request
     * @param response
     * @param passWord
     * @return
     */
    @PostMapping("api/remarkPassWordApp.act")
    public ResultBody remarkPassWordApp(HttpServletRequest request, HttpServletResponse response,@RequestBody PassWord passWord){
        try {
            requestUTF.uTFonE(request,response);
            Map<String, String> map = passWordUtil.tokenPassWord(passWord.getPassToken(), passWord.getPsnId());
            passWord.setPsnId(passWord.getPsnId());
            String passWordS = passWordService.get(passWord).getPassWord();
            if (map.get(passWord.getPsnId()).equals(passWord.getPassWord())) {
                return new ResultBody(ApiResultEnum.SUCCESS, "您在该网站的密码是：" + passWordS);
            } else {
                return new ResultBody(ApiResultEnum.SUCCESS, "密码错误！");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, "服务器连接超时");
        }
    }

}