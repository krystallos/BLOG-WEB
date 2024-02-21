package com.example.api.hisBlosMain;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.service.MineBlosService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.systemMsg.sysVersions.enity.SysVersions;
import com.example.systemMsg.sysVersions.service.SysVersionsService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.DicListVo;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人博客查看页
 * @author Enoki
 */
@RestController
public class HisBlosMainConteroller {

    private static final Logger log = Logger.getLogger(HisBlosMainConteroller.class);

    @Resource
    private SysVersionsService sysVersionsService;
    @Resource
    private MineBlosService mineBlosService;
    @Resource
    private PersonService personService;

    /*个人博客页面*/
    @Log(title = "个人博客主页（公开）", type = LogEnum.SELECT)
    @PostMapping("open/hisBlosMain.act")
    public ResultBody hisBlosMain(@RequestBody Person person){
        try{
            SysVersions sysVersionsList = sysVersionsService.selectOneAndOneSysVersions(0);
            Map<String, Object> hasMap = new HashMap<>();
            if(person.getIds()==null){
                return new ResultBody(ApiResultEnum.NOT_FIND_THREE, "地址非法，请输入正确地址");
            }
            person = personService.get(person.getIds());
            MineBlos mineBlos = new MineBlos();
            mineBlos.setPsnId(person.getIds());
            //List<DicListVo> item = mineBlosService.mineBlosGroupDate(person.getIds());
            List<MineBlos> tag = mineBlosService.selectRightBlos(mineBlos);
            //hasMap.put("groupTime", item);
            hasMap.put("groupTag", tag);
            hasMap.put("person",person);
            hasMap.put("sysVersionsList",sysVersionsList);
            return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*查询博客文章*/
    @Log(title = "个人博客文章列表（公开）", type = LogEnum.SELECT)
    @PostMapping("open/hisBlosMineType.act")
    public ResultBody hisBlosMineType(@RequestBody MineBlos mineBlos){
        try {
            mineBlos.pubImplPage(mineBlos.getNowTab(),4);
            List<MineBlos> item = mineBlosService.mineBlosApiDivLook(mineBlos);
            return new ResultBody(ApiResultEnum.SUCCESS, item, (int) new PageInfo<>(item).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /*获取博客时间轴*/
    @Log(title = "获取博客时间轴（公开）", type = LogEnum.SELECT)
    @PostMapping("open/getTimeGroupBy.act")
    public ResultBody getTimeGroupBy(@RequestBody MineBlos mineBlos){
        try {
            if(mineBlos.getPsnId()==null){
                return new ResultBody(ApiResultEnum.NOT_FIND_THREE, "地址非法，请输入正确地址");
            }
            List<DicListVo> item = mineBlosService.mineBlosGroupDate(mineBlos.getPsnId());
            return new ResultBody(ApiResultEnum.SUCCESS, item);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
