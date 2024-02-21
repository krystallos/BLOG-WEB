package com.example.passWordCont.web;

import com.example.passWordCont.enity.PassWord;
import com.example.passWordCont.service.PassWordService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * 密码本
 * @author Enoki
 */
@RestController
public class PassWordConteroller {

    private static final Logger log = Logger.getLogger(PassWordConteroller.class);
    
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private PassWordService passWordService;
    @Resource
    private PassWordUtil passWordUtil;

    /**
     * 密码本首页列表
     * @param session
     * @param passWord 密码本实体
     */
    @Log(title = "密码本列表", type = LogEnum.SELECT)
    @PostMapping("api/passWordMine.act")
    public ResultBody passWordMine(HttpSession session, @RequestBody PassWord passWord) {
        try {
            Person person = (Person) redisUtils.get(session.getId());
            passWord.setPsnId(person.getIds());
            passWord.pubImplPage(passWord.getNowTab(), passWord.getHasTab());
            List<PassWord> listTab = passWordService.passWordMineTab(passWord);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 密码校对。查看密码
     * @param passWord
     * @return
     */
    @Log(title = "校对获取密码本信息", type = LogEnum.SELECT)
    @PostMapping("api/remarkPassWord.act")
    public ResultBody remarkPassWord(HttpSession session ,@RequestBody PassWord passWord){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            Map<String, String> map = passWordUtil.tokenPassWord(person.getUserId(), person.getIds());
            passWord.setPsnId(person.getIds());
            String passWordS = passWordService.get(passWord).getPassWord();
            if (map.get(person.getIds()).equals(passWord.getPassWord())) {
                return new ResultBody(ApiResultEnum.SUCCESS, "您在该网站的密码是：" + passWordS);
            } else {
                return new ResultBody(ApiResultEnum.SUCCESS, "密码错误！");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 密码详细信息
     * @param passWord
     * @return
     */
    @Log(title = "密码本详情", type = LogEnum.DETIAL)
    @PostMapping("api/passWordUtilInfo.act")
    public ResultBody passWordUtilInfo(HttpSession session, @RequestBody PassWord passWord){
        Person person = (Person) redisUtils.get(session.getId());
        passWord.setPsnId(person.getIds());
        Map<String, String> map = passWordUtil.tokenPassWord(person.getUserId(), person.getIds());
        if(!map.get(person.getIds()).equals(passWord.getPassWord())){
            return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "密码错误！");
        }
        passWord = passWordService.get(passWord);
        return new ResultBody(ApiResultEnum.SUCCESS, passWord);
    }

    /**
     * 密码修改.更新。删除操作
     * @param session
     * @param passWord
     */
    @Log(title = "密码本更新删除", type = LogEnum.EDIT)
    @PostMapping("api/updatePassWordUtil.act")
    public ResultBody updatePassWordUtil(HttpSession session,@RequestBody PassWord passWord){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            passWord.getUuidCreateUpdate(person.getIds());
            passWord.setPsnId(person.getIds());
            passWord.getNowDate("");
            passWordService.updatePassWord(passWord);
            return new ResultBody(ApiResultEnum.SUCCESS, "操作成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }


}