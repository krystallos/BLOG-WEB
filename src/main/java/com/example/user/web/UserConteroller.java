package com.example.user.web;

import com.example.user.enity.User;
import com.example.user.service.UserService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 用户页
 * @author Enoki
 */
@RestController
public class UserConteroller {

    private static final Logger log = Logger.getLogger(UserConteroller.class);

    @Resource
    private UserService userService;

    /**
     * 查询用户
     */
    @Log(title = "获取用户列表", type = LogEnum.SELECT)
    @PostMapping("api/userSelect.act")
    public ResultBody userSelect(@RequestBody User user){
        try {
            user.pubImplPage(user.getNowTab(),user.getHasTab());
            List<User> userList = userService.selectUserList(user);
            return new ResultBody(ApiResultEnum.SUCCESS, userList, (int) new PageInfo<>(userList).getTotal());
        }catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取用户
     */
    @Log(title = "获取用户详情", type = LogEnum.DETIAL)
    @PostMapping("api/getUser.act")
    public ResultBody getUser(@RequestBody User user){
        try{
            user = userService.getUser(user.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, "获取成功", user);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 更新用户状态
     */
    @Log(title = "更新用户状态", type = LogEnum.EDIT)
    @PostMapping("api/userUpdateState.act")
    public ResultBody userUpdateState(@RequestBody User user){
        try{
            userService.updateUser(user);
            return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
    * 更新用户
    */
    @Log(title = "更新用户信息", type = LogEnum.EDIT)
    @PostMapping("api/userUpdate.act")
    public ResultBody userUpdate(@RequestBody User user){
        try{
            userService.updateUser(user);
            return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 还原用户密码
     */
    @Log(title = "还原用户密码", type = LogEnum.EDIT)
    @PostMapping("api/userPassBack.act")
    public ResultBody userPassBack(@RequestBody User user){
        try{
            String pass = "";
            if(user.getUserPassWord() == null){
                pass = user.getUserName() + System.currentTimeMillis();
            }else{
                pass = user.getUserPassWord();
            }
            user.setUserPassWord(rsaKey.getKeyBtye(pass));
            userService.updateUser(user);
            return new ResultBody(ApiResultEnum.SUCCESS, "还原成功，还原后的初始密码为: "+pass);
        }catch (IOException e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
