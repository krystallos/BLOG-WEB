package com.example.mineBlos.myBlosTab.web;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.enity.MineBlosTop;
import com.example.mineBlos.myBlosTab.service.MineBlosService;
import com.example.mineBlos.myBlosTab.service.mineBlosTopService;
import com.example.mineBlos.myBlosType.enity.MineBlosType;
import com.example.mineBlos.myBlosType.service.MineBlosTypeService;
import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 撰写博客页面
 * @author Enoki
 */
@RestController
public class MineBlosTopConteroller {

    private static final Logger log = Logger.getLogger(MineBlosTopConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MineBlosTypeService mineBlosTypeService;
    @Resource
    private mineBlosTopService mineBlosTopService;
    @Resource
    private MineBlosService mineBlosService;

    /**
     * 获取博客tag
     */
    @Log(title = "获取个人博客标签列表", type = LogEnum.SELECT)
    @PostMapping(value = "api/goNewMineBlosTop.act")
    public ResultBody goNewMineBlosTop(HttpSession session){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            MineBlosType mineBlosType = new MineBlosType();
            mineBlosType.setPsnId(person.getIds());
            List<MineBlosType> mineBlosTypeList = mineBlosTypeService.mineBlosTypeNoTab(mineBlosType);
            if(mineBlosTypeList == null || mineBlosTypeList.size()==0){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "请设置基础博客类型");
            }
            return new ResultBody(ApiResultEnum.SUCCESS, mineBlosTypeList);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取个人博客详情
     */
    @Log(title = "获取个人博客详情", type = LogEnum.DETIAL)
    @PostMapping(value = "api/getWriteMineBlos.act")
    public ResultBody getWriteMineBlos(HttpSession session,@RequestBody MineBlos mineBlos){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            mineBlos.setPsnId(person.getIds());
            mineBlos = mineBlosService.selectBlosDetial(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, mineBlos);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 写博客
     * @param session
     * @param mineBlosTop
     */
    @Log(title = "撰写博客", type = LogEnum.INSERT)
    @PostMapping("api/writeMineBlos.act")
    public ResultBody writeMineBlos(HttpSession session,@RequestBody MineBlosTop mineBlosTop){
        try {
            Person person = (Person) redisUtils.get(session.getId());
            String uuidTitle = rsaKey.uuid(null);
            mineBlosTop.getUuidCreateUpdate(person.getIds());//更新人
            mineBlosTop.getNowDate(null);//时间
            mineBlosTop.setDelFlag("0");
            mineBlosTop.setIds(uuidTitle);
            mineBlosTop.setActImg("null");
            mineBlosTop.setActLike(person.getPsnLikeId());
            int a = mineBlosTopService.writeMineBlos(mineBlosTop);
            /*插入博客主表结束*/
            if(a>0){
                MineBlos mineBlos = new MineBlos();
                mineBlos.setIds(rsaKey.uuid(null));
                mineBlos.setActId(uuidTitle);
                mineBlos.setActName(mineBlosTop.getActTitle());
                mineBlos.setContent(mineBlosTop.getContent());
                mineBlos.setLiteCont(mineBlosTop.getLiteCont());
                mineBlos.setClobTitle(mineBlosTop.getActTitle());
                mineBlos.setOnState(mineBlosTop.getState());
                mineBlos.setPsnId(person.getIds());
                mineBlos.setPsnName(person.getPsnName());
                mineBlos.setCentImg(mineBlosTop.getCentImg()==null||mineBlosTop.getCentImg().equals("")?"":"/imgBlos/"+mineBlosTop.getCentImg());
                mineBlos.getNowDate(null);
                mineBlos.getUuidCreateUpdate(person.getIds());
                int b= mineBlosService.insertMineBlos(mineBlos);
                if(b!=0){
                    return new ResultBody(ApiResultEnum.SUCCESS, "保存成功！");
                }
            }
            return new ResultBody(ApiResultEnum.FAIL, "插入失败！");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 修改博客
     * @param session
     * @param mineBlosTop
     */
    @Log(title = "修改博客", type = LogEnum.EDIT)
    @PostMapping("api/updateWriteMineBlos.act")
    public ResultBody updateWriteMineBlos(HttpSession session,@RequestBody MineBlosTop mineBlosTop){
        Person person = (Person) redisUtils.get(session.getId());
        if(person == null || person.getIds() == null){
            return new ResultBody(ApiResultEnum.TOKEN_INVALID, "缺失用户信息，请重新登入");
        }
        mineBlosTop.getNowDate(null);//时间
        int a = mineBlosTopService.updateMineBlos(mineBlosTop);
        if(a > 0){
            MineBlos mineBlos = new MineBlos();
            mineBlos.setActId(mineBlosTop.getIds());
            mineBlos.setContent(mineBlosTop.getContent());
            mineBlos.setLiteCont(mineBlosTop.getLiteCont());
            mineBlos.setClobTitle(mineBlosTop.getActTitle());
            mineBlos.getNowDate(null);
            mineBlosService.updateMineBlos(mineBlos);
        }else{
            return new ResultBody(ApiResultEnum.SUCCESS, "头文件修改失败");
        }
        return new ResultBody(ApiResultEnum.SUCCESS, "修改成功");
    }

}
