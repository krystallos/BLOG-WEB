package com.example.util;

import com.example.login.enity.Login;
import com.example.login.service.LoginService;
import com.example.person.enity.Person;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 密码校验，所有校验方法走这里
 */
@SpringBootApplication
public class PassWordUtil {

    private static final Logger log = Logger.getLogger(PassWordUtil.class);

    @Resource
    private LoginService loginService;

    public Map<String, String> tokenPassWord(String userId, String personId){

        try {
            Map<String, String> map = new HashMap<>();
            Login user = loginService.get(userId);
            String returnKey = rsaKey.setKeyBtye(user.getUserPassWord());
            map.put(personId,returnKey);
            return map;
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

}
