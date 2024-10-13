package com.example.api.hisBlosTab;

import com.example.activityLike.enity.ActivityLike;
import com.example.activityLike.service.ActivityLikeService;
import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.service.MineBlosService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客外链分享查看页
 * @author Enoki
 */
@RestController
public class HisBlosTabConteroller {

    private static final Logger log = Logger.getLogger(HisBlosTabConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MineBlosService mineBlosService;
    @Resource
    private ActivityLikeService activityLikeService;

    /*查询公开/非公开博客*/
    @Log(title = "获取博客文章详情（公开）", type = LogEnum.DETIAL)
    @PostMapping("open/hisBlos.act")
    public ResultBody hisBlos(@RequestBody MineBlos mineBlos){
        try{
            mineBlos = mineBlosService.selectMineBlosSemmIn(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, mineBlos);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*查询博客文章*/
    @Log(title = "未调用接口（公开）", type = LogEnum.DEFAULT)
    @PostMapping("open/hisBlosType.act")
    public ResultBody hisBlosType(@RequestBody MineBlos mineBlos){
        try {
            Map<String, List<MineBlos>> blos = new HashMap<>();
            List<MineBlos> mineBlosList = mineBlosService.hisBlosType(redisUtils.getConfig(ConfigDicEnum.assessUrlBlos.message) + "hisBlos.act?ids=",mineBlos.getIds());
            blos.put("left", mineBlosList);
            return new ResultBody(ApiResultEnum.SUCCESS, blos);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

    /*收藏喜欢的博客*/
    @Log(title = "收藏喜欢的博客", type = LogEnum.INSERT)
    @PostMapping("api/hisBlosLike.act")
    public ResultBody hisBlosLike(HttpSession session, MineBlos mineBlos){
        try {
            ActivityLike activityLike = new ActivityLike();
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID, "您未登入或日志过期，即将跳转登入页...");
            }
            activityLike.setMineActivityId(mineBlos.getIds());
            activityLike.setPsnId(person.getIds());
            List<ActivityLike> item = activityLikeService.selectPageNoLike(activityLike);
            if (item.size()>0) {
                return new ResultBody(ApiResultEnum.DUPLICATION_OF_DATA, "您已收藏过该文章了！");
            }
            activityLike.setCreateId(person.getIds());
            activityLike.setLikeMainId("");
            activityLikeService.insertLike(activityLike);

            return new ResultBody(ApiResultEnum.SUCCESS, "收藏成功！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }

    }

}
