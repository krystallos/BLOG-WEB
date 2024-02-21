package com.example.login.service;

import com.example.login.enity.Login;
import com.example.login.mapper.LoginMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginService {

    @Resource
    private LoginMapper loginMapper;

    public List<Login> LoginUser(String uname){
        return loginMapper.selectuser(uname);
    }

    public Login get(String id){
        return loginMapper.get(id);
    }

    public Login selectuserOrEmil(String emil){
        return loginMapper.selectuserOrEmil(emil);
    }
}
