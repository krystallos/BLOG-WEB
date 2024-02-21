package com.example.user.service;

import com.example.user.enity.User;
import com.example.user.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询用户
     * @param user
     * @return
     */
    public List<User> selectUserList(User user){
        PageHelper.offsetPage(user.getStartTab(), user.getHasTab());
        List<User> userList = userMapper.selectUserList();
        return userList;
    }

    /**
     * 查询指定ID的用户
     * @param uuid
     * @return
     */
    public User getUser(String uuid){
        User userList = userMapper.getUser(uuid);
        return userList;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public int updateUser(User user){
        user.setUpdateDate(user.getNowDate(null));//获取最新时间
        return userMapper.updateUser(user);
    }

}
