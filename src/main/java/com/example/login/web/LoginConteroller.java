package com.example.login.web;

import com.example.login.enity.Login;
import com.example.login.service.LoginService;
import com.example.menu.enity.menu;
import com.example.menu.service.MenuService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.token.web.TokenSession;
import com.example.user.enity.User;
import com.example.user.service.UserService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登入页
 */
@RestController
public class LoginConteroller {
    private static final Logger log = Logger.getLogger(LoginConteroller.class);

    @Resource
    private LoginService loginService;
    @Resource
    private UserService userService;
    @Resource
    private PersonService personService;
    @Resource
    private TokenSession tokenSession;
    @Resource
    private MenuService menuService;
    @Resource
    private RedisUtils redisUtils;

    //登入判断
    @Log(title = "登入系统", type = LogEnum.SELECT)
    @PostMapping("open/login.act")
    public ResultBody Login(HttpSession session, @RequestBody Login login){
        try {
            if(StringBlack.isBlackString(login.getLogname())){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA,"邮箱/电话非法表达式");
            }
            if (StringBlack.isBlackString(login.getLogpass())) {
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA,"密码非法表达式");
            }
            List<Login> userList = loginService.LoginUser(login.getLogname());
            if(userList.size()==0){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA,"无此用户，请确定是否输入账户错误");
            }else if(userList.size()==1){
                String returnKey = rsaKey.setKeyBtye(userList.get(0).getUserPassWord());
                if(returnKey.equals(login.getLogpass())){
                    List<Person> personList = personService.selectListPerson(userList.get(0).getIds());
                    Person personBin = new Person();
                    if(personList != null && personList.size()>0){
                        for(int a=0;a<personList.size();a++){
                            personBin = personList.get(0);
                        }
                        String token = tokenSession.AseToken(personBin.getIds());
                        if(redisUtils.get(session.getId()) == null){
                            redisUtils.set(session.getId(),personBin);
                        }
                        redisUtils.set(personBin.getIds(), token);
                        personBin.setAssessToken(token);
                        User user = new User();
                        user.setAreaId(login.getAreaId());
                        user.setOrgSessionId(login.getOrgSessionId());
                        user.setIds(userList.get(0).getIds());
                        userService.updateUser(user);
                        return new ResultBody(ApiResultEnum.LOGIN,"登入成功" ,token);
                    }else{
                        return new ResultBody(ApiResultEnum.NOT_FIND_DATA,"无社区账号");
                    }
                }else{
                    return new ResultBody(ApiResultEnum.DATA_ERROR,"密码错误，请确定您的密码是否正确");
                }
            }else{
                return new ResultBody(ApiResultEnum.DATA_ERROR,"数据非法，请联系管理员对账号进行鉴权处理");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR,e.getMessage());
        }
    }

    //登入判断
    @Log(title = "获取用户信息", type = LogEnum.DETIAL)
    @PostMapping("api/getInfo.act")
    public ResultBody UserInfo(HttpSession session){
        Person person = (Person)redisUtils.get(session.getId());
        List<menu> menuType = menuService.selectTypeMenuForRole(person.getIds());

        //存储结果
        List<menu> father = new ArrayList<menu>();
        //遍历MENU lists : list
        for (int b = 0; b < menuType.size(); b++) {
            //获取父级
            if (menuType.get(b).getPramId() == null) {
                //子集结果
                List<menu> chidList = new ArrayList<menu>();
                for (int a = 0; a < menuType.size(); a++) {
                    if (menuType.get(b).getIds().equals(menuType.get(a).getPramId())) {
                        chidList.add(menuType.get(a));
                    }
                }
                menuType.get(b).setListMenu(chidList);
                father.add(menuType.get(b));
            }
        }
        Map<String, Object> hasMap = new HashMap<>();
        hasMap.put("person", person);
        hasMap.put("menu", father);
        return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
    }

    //主页面退出
    @Log(title = "退出系统", type = LogEnum.EDIT)
    @PostMapping("open/gouOut.act")
    public ResultBody GoOut(HttpSession session) {
        try {
            Person person = (Person)redisUtils.get(session.getId());
            redisUtils.remove(session.getId());
            redisUtils.remove(person.getIds());
            session.invalidate();
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.SUCCESS, "注销成功");
    }

}
