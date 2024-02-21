package com.example.passWordCont.service;

import com.example.passWordCont.enity.PassWord;
import com.example.passWordCont.mapper.PassWordMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassWordService {

    @Resource
    private PassWordMapper passWordMapper;

    /**
     * 密码本查询数据（分页）
     * @param
     * @return
     */
    public List<PassWord> passWordMineTab(PassWord passWord){
        PageHelper.offsetPage(passWord.getStartTab(), passWord.getHasTab());
        List<PassWord> item = passWordMapper.passWordMineSelect(passWord);
        return item;
    }

    /**
     * 获取唯一值
     * @param passWord
     * @return
     */
    public PassWord get(PassWord passWord){
        return passWordMapper.get(passWord);
    }


    /**
     * 更新/插入操作
     * @param passWord
     * @return
     */
    public int updatePassWord(PassWord passWord){
        int type = 0;
        if(passWord.getIds()==null || passWord.getIds().equals("") && passWord.getFroms()!=null && passWord.getFroms().equals("add")){
            passWord.setIds(rsaKey.uuid(null));
            passWord.getNowDate("");
            passWord.setPassToken("");
            type = passWordMapper.insertPassWord(passWord);
        }else if(passWord.getFroms()!=null && passWord.getFroms().equals("edit")){
            passWord.getNowDate("");
            type = passWordMapper.updatePassWord(passWord);
        }else if(passWord.getFroms()!=null && passWord.getFroms().equals("del")){
            passWord.setDelFlag("1");
            type = passWordMapper.updatePassWord(passWord);
        }
        return type;
    }

}
