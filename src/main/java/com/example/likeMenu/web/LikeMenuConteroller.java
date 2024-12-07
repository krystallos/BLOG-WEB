package com.example.likeMenu.web;

import com.example.likeMenu.enity.LikeMenu;
import com.example.likeMenu.service.LikeMenuService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
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
 * 社区控制页
 * @author Enoki
 */
@RestController
public class LikeMenuConteroller {

    private static final Logger log = Logger.getLogger(LikeMenuConteroller.class);

    @Resource
    private LikeMenuService likeMenuService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private PersonService personService;

    /*修改*/
    @Log(title = "更新加入的社区", type = LogEnum.EDIT)
    @PostMapping("api/editLikeMenu.act")
    public ResultBody editLikeMenu(@RequestBody LikeMenu likeMenu){
        try{
            likeMenuService.updateLikeMenuTab(likeMenu);
            return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*新增*/
    @Log(title = "添加加入的社区", type = LogEnum.INSERT)
    @PostMapping("api/addLikeMenu.act")
    public ResultBody addLikeMenu(HttpSession session,@RequestBody LikeMenu likeMenu){
        try{
            likeMenu.setState("1");
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            likeMenu.setCreateId(person.getIds());
            likeMenuService.insertLikeMenuTab(likeMenu);
            return new ResultBody(ApiResultEnum.SUCCESS, "添加成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*删除*/
    @Log(title = "删除加入的社区", type = LogEnum.DELETE)
    @PostMapping("api/delLikeMenu.act")
    public ResultBody delLikeMenu(@RequestBody LikeMenu likeMenu){
        try {
            likeMenuService.delLikeMenuTab(likeMenu.getIds(),"删除");
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*获取单个社区*/
    @Log(title = "获取唯一社区记录", type = LogEnum.DETIAL)
    @PostMapping("api/getLikeMenu.act")
    public ResultBody getLikeMenu(@RequestBody LikeMenu likeMenu){
        try {
            likeMenu = likeMenuService.getLikeMenu(likeMenu.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, likeMenu);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*社区分页*/
    @Log(title = "获取社区列表", type = LogEnum.SELECT)
    @PostMapping("api/likeMenuTabPage.act")
    public ResultBody likeMenuTabPage(@RequestBody LikeMenu likeMenu){
        try{
            likeMenu.pubImplPage(likeMenu.getNowTab(),likeMenu.getHasTab());
            List<LikeMenu> tab = likeMenuService.selectLikeMenuTab(likeMenu);
            return new ResultBody(ApiResultEnum.SUCCESS, tab, (int) new PageInfo<>(tab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*加入社区*/
    @Log(title = "加入社区", type = LogEnum.INSERT)
    @PostMapping("api/innerJoinLike.act")
    public ResultBody innerJoinLike(HttpSession session,@RequestBody Person person){
        try {
            Person redPeople = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            person.setIds(redPeople.getIds());
            personService.updatePerson(person);
            return new ResultBody(ApiResultEnum.SUCCESS, "加入新的社区成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }


}
