package com.example.mineBlos.myBlosTab.web;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.service.MineBlosService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 个人博客
 * @author Enoki
 */
@RestController
public class MineBlosConteroller {

    private static final Logger log = Logger.getLogger(MineBlosConteroller.class);

    @Resource
    private MineBlosService mineBlosService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 个人博客首页列表
     * @param session
     * @param mineBlos type类型1是最近消息，2是文章，3是资源更新，4是收藏
     */
    @Log(title = "获取个人博客数据信息集", type = LogEnum.SELECT)
    @PostMapping("api/mineBlosTab.act")
    public ResultBody mineBlosTab(HttpSession session, @RequestBody MineBlos mineBlos){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            mineBlos.setPsnId(person.getIds());
            mineBlos.pubImplPage(mineBlos.getNowTab(),mineBlos.getHasTab());
            List<MineBlos> listTab = mineBlosService.mineBlosSysLook(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 个人博客数据列表
     * @param session
     * @param mineBlos
     */
    @Log(title = "获取个人博客列表", type = LogEnum.SELECT)
    @PostMapping("api/mineBlosArticle.act")
    public ResultBody mineBlosArticle(HttpSession session, @RequestBody MineBlos mineBlos){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            mineBlos.setPsnId(person.getIds());
            mineBlos.pubImplPage(mineBlos.getNowTab(),mineBlos.getHasTab());
            List<MineBlos> listTab = mineBlosService.mineBlosArticle(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除博客
     * @param mineBlos
     */
    @Log(title = "删除博客", type = LogEnum.DELETE)
    @PostMapping("api/delMineBlosArticle.act")
    public ResultBody delMineBlosArticle(@RequestBody MineBlos mineBlos){
        try{
            mineBlosService.delMineBlosArticle(mineBlos.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, "删除完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
