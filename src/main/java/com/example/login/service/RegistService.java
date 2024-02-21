package com.example.login.service;

import com.example.login.enity.GetBackPassWord;
import com.example.login.enity.Login;
import com.example.login.mapper.RegistMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegistService {

    @Resource
    private RegistMapper registMapper;

    /**
     * 注册用户
     * @param login 登入类
     * @return 返回主键
     */
    public String createNewUser(Login login){
        login.setIds(rsaKey.uuid(null));
        login.setCreateDate(null);
        login.setUpdateDate(null);
        registMapper.createNewUser(login);
        return login.getIds();
    }

    /**
     * 根据邮箱/电话查询用户
     * @param login
     * @return
     */
    public List<Login> selectProUser(Login login){
        return registMapper.selectProUser(login);
    }

    /**
     * 找回密码
     * @param getBackPassWord
     * @return
     */
    public int changePassWord(GetBackPassWord getBackPassWord){
        return registMapper.changePassWord(getBackPassWord);
    }
}
