package com.example.login.web;

import com.example.likeMenu.enity.LikeMenu;
import com.example.likeMenu.service.LikeMenuService;
import com.example.login.enity.GetBackPassWord;
import com.example.login.enity.RegiestUser;
import com.example.login.enity.Login;
import com.example.login.service.RegistService;
import com.example.person.enity.Person;
import com.example.person.web.PersonConteroller;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.RedisEnum;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 注册页
 */
@RestController
public class RegistConteroller {

    private static final Logger log = Logger.getLogger(RegistConteroller.class);
    @Resource
    private LikeMenuService likeMenuService;
    @Resource
    private RegistService registService;
    @Resource
    private PersonConteroller personConteroller;
    @Resource
    private RedisUtils redisUtils;

    /*注册*/
    @Log(title = "用户注册", type = LogEnum.INSERT)
    @PostMapping("open/registUser.act")
    public ResultBody registUser(@RequestBody RegiestUser regiestUser){
        Login login = new Login();
        try {
            login.setUserName(regiestUser.getUserName());
            login.setUserPassWord(rsaKey.getKeyBtye(regiestUser.getUserPassWord()));
            login.setPhone(regiestUser.getPhone(),false);
            login.setEmail(regiestUser.getEmail());

            List<Login> proUser = registService.selectProUser(login);
            if(proUser.size()>0){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "该电话/邮箱已被使用，请替换未使用过的电话/邮箱注册");
            }
            String createNewUserCount = registService.createNewUser(login);
            LikeMenu likeMenu = likeMenuService.selectDefLikeMenu();
            Person person = new Person();
            person.setPsnLikeId(likeMenu.getIds());
            person.setUserId(createNewUserCount);
            person.setPsnName(login.getUserName());
            if(!personConteroller.registPersonNotPost(person)){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "注册用户失败！");
            }
            return new ResultBody(ApiResultEnum.SUCCESS,"注册成功，正在跳转登入页...");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*向邮箱发送验证码*/
    @Log(title = "注册验证码发送", type = LogEnum.UPLOAD)
    @PostMapping("open/sendGoBackEmail.act")
    public ResultBody sendGoBackEmail(@RequestBody GetBackPassWord getBackPassWord){
        Login login = new Login();
        login.setEmail(getBackPassWord.getEmail());
        List<Login> user = registService.selectProUser(login);
        if(null == user || user.size() == 0){
            return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到该已注册的邮箱，请确认邮箱准确性");
        }
        getBackPassWord.setTime(new Date().getTime());
        try{
            GetBackPassWord vo = (GetBackPassWord)redisUtils.get(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail());
            if(null != vo && vo.getTime() + 60000 > getBackPassWord.getTime() ){
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "您在一分钟内已发送过一次验证码请求，请勿重复获取");
            }
            //todo 这里要做验证码发送和保存
            getBackPassWord.setCode("1234");
            redisUtils.set(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail(), getBackPassWord);
            return new ResultBody(ApiResultEnum.SUCCESS, "验证码发送成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*验证验证码并返回token*/
    @Log(title = "注册验证码校验", type = LogEnum.DETIAL)
    @PostMapping("open/goBackCodeVerify.act")
    public ResultBody goBackCodeVerify(@RequestBody GetBackPassWord getBackPassWord){
        try{
            GetBackPassWord vo = (GetBackPassWord)redisUtils.get(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail());
            if(null == vo){
                return new ResultBody(ApiResultEnum.THROWABLE_PARAM, "您的验证码已过期，请重新获取");
            }
            if(!getBackPassWord.getCode().equals(vo.getCode())){
                return new ResultBody(ApiResultEnum.THROWABLE_PARAM, "验证码输入错误");
            }
            Random rand = new Random();
            int max = 100000;
            int min = 10000;
            int returnLen = rand.nextInt((max - min) + 1) + min;
            vo.setToken(returnLen);
            redisUtils.set(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail(), vo);
            getBackPassWord.setToken(returnLen);
            return new ResultBody(ApiResultEnum.SUCCESS, "验证成功", getBackPassWord);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*找回密码*/
    @Log(title = "找回密码", type = LogEnum.EDIT)
    @PostMapping("open/changePassWord.act")
    public ResultBody changePassWord(@RequestBody GetBackPassWord getBackPassWord){
        GetBackPassWord vo = (GetBackPassWord)redisUtils.get(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail());
        if(null == vo){
            return new ResultBody(ApiResultEnum.THROWABLE_PARAM, "您的验证信息已过期，流程已失效");
        }
        if(!getBackPassWord.getToken().equals(vo.getToken())){
            return new ResultBody(ApiResultEnum.THROWABLE_PARAM, "验证失败，有可能是流程已过期或是非修改页操作");
        }
        try {
            Login login = new Login();
            login.setEmail(getBackPassWord.getEmail());
            List<Login> user = registService.selectProUser(login);
            if(null == user || user.size() == 0){
                return new ResultBody(ApiResultEnum.THROWABLE_PARAM, "验证失败，找不到该用户，请联系管理员修改密码");
            }
            getBackPassWord.setPassWord(rsaKey.getKeyBtye(getBackPassWord.getPassWord()));
            getBackPassWord.setUid(user.get(0).getIds());
            registService.changePassWord(getBackPassWord);
            redisUtils.remove(RedisEnum.GETBACK_PASSWORD + getBackPassWord.getEmail());
            return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

}
