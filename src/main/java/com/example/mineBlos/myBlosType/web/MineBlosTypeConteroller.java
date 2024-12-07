package com.example.mineBlos.myBlosType.web;

import com.example.mineBlos.myBlosType.enity.MineBlosType;
import com.example.mineBlos.myBlosType.service.MineBlosTypeService;
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
 * 个人博客类型处理
 * @author Enoki
 */
@RestController
public class MineBlosTypeConteroller {

    private static final Logger log = Logger.getLogger(MineBlosTypeConteroller.class);

    @Resource
    private MineBlosTypeService mineBlosTypeService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 个人博客类型列表
     * @param session
     * @param mineBlosType 博客类型
     */
    @Log(title = "个人博客类型列表", type = LogEnum.SELECT)
    @PostMapping("api/mineBlosTypeTab.act")
    public ResultBody mineBlosTypeTab(HttpSession session,@RequestBody MineBlosType mineBlosType){
        try{
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            mineBlosType.setPsnId(person.getIds());
            mineBlosType.pubImplPage(mineBlosType.getNowTab(),mineBlosType.getHasTab());
            List<MineBlosType> listTab = mineBlosTypeService.mineBlosTypeTab(mineBlosType);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取个人博客标签详情
     * @param mineBlosType 实体
     */
    @Log(title = "获取个人博客标签详情", type = LogEnum.DETIAL)
    @PostMapping(value = "api/getMineBlosType.act")
    public ResultBody getMineBlosType(@RequestBody MineBlosType mineBlosType){
        try {
            mineBlosType = mineBlosTypeService.mineBlosTypeGet(mineBlosType.getIds());
            return new ResultBody(ApiResultEnum.SUCCESS, mineBlosType);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, mineBlosType);
        }
    }

    /**
     * 新增/修改博客类型
     * @param session
     * @param mineBlosType
     */
    @Log(title = "新增、修改个人博客标签", type = LogEnum.INSERT)
    @PostMapping("api/insertBlosType.act")
    public ResultBody insertBlosType(HttpSession session,@RequestBody MineBlosType mineBlosType){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            mineBlosType.getNowDate(null);
            mineBlosType.setPsnId(person.getIds());
            mineBlosType.setBlosTypeImg("NULL");
            mineBlosType.getUuidCreateUpdate(person.getIds());
            mineBlosTypeService.insertMineBlosType(mineBlosType);
            return new ResultBody(ApiResultEnum.SUCCESS, "新增成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除博客类型
     * @param mineBlosType
     */
    @Log(title = "删除个人博客标签", type = LogEnum.DELETE)
    @PostMapping("api/deleteBlosType.act")
    public ResultBody deleteBlosType(@RequestBody MineBlosType mineBlosType){
        try {
            if(mineBlosType.getIds()==null || mineBlosType.getIds().equals("")){
                return new ResultBody(ApiResultEnum.DATA_ERROR, "数据为空，删除失败");
            }else {
                mineBlosType.setDelFlag("1");
                mineBlosType.getNowDate(null);
                mineBlosTypeService.insertMineBlosType(mineBlosType);
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }


}
