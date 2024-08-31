package com.example.person.web;

import com.example.login.enity.Login;
import com.example.menu.service.RoleService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 人员注册页
 * @author Enoki
 */
@RestController
public class PersonConteroller {

    private static final Logger log = Logger.getLogger(PersonConteroller.class);

    @Resource
    private PersonService personService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RoleService roleService;

    /**
     * post请求方法
     * @param request
     * @param response
     * @param person
     */
    @Log(title = "注册用户信息(未启用)", type = LogEnum.INSERT)
    @PostMapping("api/registPerson.act")
    public ResultBody registPerson(HttpServletRequest request, HttpServletResponse response, Person person){
        try {
            if (StringBlack.isBlackString(person.getPsnName())) {
                return new ResultBody(ApiResultEnum.DATA_ERROR,"用户名为空");
            }
            if (StringBlack.isBlackString(person.getUserId())) {
                return new ResultBody(ApiResultEnum.DATA_ERROR ,"用户ID为空");
            }
            person.setUserId(person.getIds());
            person.setPsnName(person.getPsnName());
            personService.createNewPerson(person);
            return new ResultBody(ApiResultEnum.SUCCESS,"建立用户成功");
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    public boolean registPersonNotPost(Person person){
        try {
            person.setUserId(person.getUserId());
            person.setPsnName(person.getPsnName());
            String pid = personService.createNewPerson(person);
            if(roleForNewPerson(pid)){
                return false;
            }
            return true;
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 注册人员权限下放
     */
    public boolean roleForNewPerson(String personId){
        String roleId = roleService.isDefRole();
        if(roleId == null){
            return true;
        }
        roleService.insertPerson(personId,roleId);
        return false;
    }

    /**
     * 实体方法，用于非请求类调用
     * @param personName
     * @param login
     * @return
     */
    public boolean registPersonMain(String personName, Login login) {
        Person person = new Person();

        if(StringBlack.isBlackString(personName)){
            return false;
        }
        person.setUserId(login.getIds());
        person.setPsnName(personName);
        personService.createNewPerson(person);
        return true;
    }

    /**
     * 查询用户实体方法
     */
    @Log(title = "获取用户实体信息", type = LogEnum.SELECT)
    @PostMapping("api/selectPersonEnity.act")
    public ResultBody selectPersonEnity(@RequestBody Person person) {
        try {
            List<Person> item = new ArrayList<>();
            if(person.getUserId() != null){
                item = personService.selectListPerson(person.getUserId());
                if(item.size() > 0){
                    person = item.get(0);
                    return new ResultBody(ApiResultEnum.SUCCESS, person);
                }else{
                    return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到社区人员");
                }
            }else{
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "用户UUID为空，查询失败");
            }
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 返回一个人员集合
     * @param session
     * @return
     */
    public Person getPersonListForRedis(HttpSession session){
        return (Person)redisUtils.get(session.getId());
    }

}
