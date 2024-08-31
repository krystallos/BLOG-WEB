package com.example.mineBlos.myDrama.web;

import com.example.mineBlos.myDrama.enity.DramaList;
import com.example.mineBlos.myDrama.service.DramaService;
import com.example.person.enity.Person;
import com.example.util.ApiResultEnum;
import com.example.util.LogEnum;
import com.example.util.ResultBody;
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
 * 番剧跟踪
 * @author Enoki
 */
@RestController
public class DramaConfigConteroller {

    private static final Logger log = Logger.getLogger(DramaConfigConteroller.class);

    @Resource
    private DramaService dramaService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取番剧跟踪列表
     */
    @Log(title = "获取番剧跟踪列表", type = LogEnum.SELECT)
    @PostMapping("api/dramaListTab.act")
    public ResultBody dramaListTab(@RequestBody DramaList dramaList){
        try{
            dramaList.pubImplPage(dramaList.getNowTab(),dramaList.getHasTab());
            List<DramaList> item = dramaService.dramaList(dramaList);
            return new ResultBody(ApiResultEnum.SUCCESS, item, (int) new PageInfo<>(item).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取番剧跟踪列表
     */
    @Log(title = "获取番剧跟踪列表", type = LogEnum.DETIAL)
    @PostMapping("api/getDetialdrama.act")
    public ResultBody getDetialdrama(@RequestBody DramaList dramaList){
        try{
            return new ResultBody(ApiResultEnum.SUCCESS, dramaService.getDetialdrama(dramaList));
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取番剧跟踪列表
     */
    @Log(title = "删除番剧", type = LogEnum.DELETE)
    @PostMapping("api/delDrama.act")
    public ResultBody delDrama(@RequestBody DramaList dramaList){
        try{
            dramaService.delDrama(dramaList);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 更新番剧
     */
    @Log(title = "更新番剧", type = LogEnum.EDIT)
    @PostMapping("api/editDrama.act")
    public ResultBody editDrama(@RequestBody DramaList dramaList){
        try{
            dramaService.editDrama(dramaList);
            return new ResultBody(ApiResultEnum.SUCCESS, "修改完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 新增番剧
     */
    @Log(title = "新增番剧", type = LogEnum.EDIT)
    @PostMapping("api/insertDrama.act")
    public ResultBody insertDrama(HttpSession session, @RequestBody DramaList dramaList){
        try{
            Person person = (Person) redisUtils.get(session.getId());
            dramaList.setCreateId(person.getIds());
            dramaList.setCreateDate(dramaList.getNowDate(""));
            dramaService.insertDrama(dramaList);
            return new ResultBody(ApiResultEnum.SUCCESS, "新增完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
