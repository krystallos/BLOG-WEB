package com.example.login.web.app;

import com.example.login.enity.Login;
import com.example.login.service.LoginService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
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
import java.util.List;

/**
 * 登入页
 */
@RestController
public class LoginAppConteroller {

    private static final Logger log = Logger.getLogger(LoginAppConteroller.class);

    @Resource
    private requestUTF requestUTF;
    @Resource
    private LoginService loginService;
    @Resource
    private PersonService personService;
    @Resource
    private RedisUtils redisUtils;

    //登入判断
    @PostMapping("api/loginApp.act")
    public ResultBody LoginApp(HttpServletRequest request, HttpServletResponse response, @RequestBody Login login){
        try {
            requestUTF.uTFonE(request, response);
            if(StringBlack.isBlackString(login.getLogname())){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID ,"邮箱/电话非法表达式");
            }
            if (StringBlack.isBlackString(login.getLogpass())) {
                return new ResultBody(ApiResultEnum.TOKEN_INVALID ,"密码非法表达式");
            }
            List<Login> userList = loginService.LoginUser(login.getLogname());
            if(userList.size()==0){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID ,"无此用户，请确定是否输入账户错误");
            }else if(userList.size()==1){
                String returnKey = rsaKey.setKeyBtye(userList.get(0).getUserPassWord());
                if(returnKey.equals(login.getLogpass())){
                    List<Person> personList = personService.selectListPerson(userList.get(0).getIds());
                    if(personList != null && personList.size()>0){
                        return new ResultBody(ApiResultEnum.SUCCESS , personList);
                    }else{
                        return new ResultBody(ApiResultEnum.FAIL , userList);
                    }
                }else{
                    return new ResultBody(ApiResultEnum.TOKEN_INVALID ,"密码错误，请确定您的密码是否正确");
                }
            }else{
                return new ResultBody(ApiResultEnum.TOKEN_INVALID ,"账户冲突");
            }
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR,null);
    }

    //主页面退出
    @PostMapping("api/gouOutApp.act")
    public void bye(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            requestUTF.uTFonE(request, response);
            session = request.getSession();
            redisUtils.remove(session.getId());
            session.invalidate();
        }catch (Exception e){
            log.error(e);
        }
    }

}
