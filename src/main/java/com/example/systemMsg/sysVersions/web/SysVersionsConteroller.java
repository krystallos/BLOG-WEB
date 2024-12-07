package com.example.systemMsg.sysVersions.web;

import com.example.person.enity.Person;
import com.example.systemMsg.sysVersions.enity.SysVersions;
import com.example.systemMsg.sysVersions.enity.SysVersionsApp;
import com.example.systemMsg.sysVersions.service.SysVersionsService;
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
 * 系统版本迭代通知页
 * @author Enoki
 */
@RestController
public class SysVersionsConteroller {

    private static final Logger log = Logger.getLogger(SysVersionsConteroller.class);

    @Resource
    private SysVersionsService sysVersionsService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 系统更新通知
     */
    @Log(title = "系统更新列表", type = LogEnum.SELECT)
    @PostMapping("api/sysVersionsWebTab.act")
    public ResultBody sysVersionsTab(@RequestBody SysVersions sysVersions){
        try {
            List<SysVersions> tab = sysVersionsService.selectSysVersions(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, tab);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 首条更新通知
     */
    @Log(title = "首条更新通知", type = LogEnum.SELECT)
    @PostMapping("api/selectOneAndOneSysVersions.act")
    public ResultBody selectOneAndOneSysVersions(HttpSession session ,@RequestBody SysVersions sysVersions){
        try {
            Person personId = (Person)redisUtils.get(session.getId());
            if(personId == null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID, "用户信息已过期");
            }
            SysVersions tab = sysVersionsService.selectOneAndOneSysVersions(0);
            if(tab == null || tab.getIds() == null){
                return new ResultBody(ApiResultEnum.SUCCESS, null);
            }
            sysVersions.setIds(tab.getIds());
            sysVersions.setCreateId(personId.getIds());
            int a = sysVersionsService.selectSysVersionsWebIss(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, a == 0? tab : null);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 用户确认更新通知
     */
    @Log(title = "用户确认更新通知", type = LogEnum.INSERT)
    @PostMapping("api/sysVersionsWebIss.act")
    public ResultBody sysVersionsWebIss(HttpSession session ,@RequestBody SysVersions sysVersions){
        try {
            Person personId = (Person)redisUtils.get(session.getId());
            if(personId == null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID, "用户信息已过期");
            }
            sysVersions.getNowDate("");
            sysVersions.setVerCreate(personId.getIds());
            sysVersions.setVerVersion(sysVersions.getIds());
            sysVersions.setIds(rsaKey.uuid(""));
            sysVersionsService.insertSysVersionsWebIss(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, "确认完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 使用SESSION获取首条通知
     */
    @Log(title = "首条更新通知", type = LogEnum.SELECT)
    @PostMapping("open/selectSessionSysVersions.act")
    public ResultBody selectSessionSysVersions(@RequestBody SysVersions sysVersions){
        try {
            SysVersions tab = sysVersionsService.selectOneAndOneSysVersions(0);
            if(tab == null || tab.getIds() == null){
                return new ResultBody(ApiResultEnum.SUCCESS, null);
            }
            sysVersions.setIds(tab.getIds());
            sysVersions.setCreateId(sysVersions.getSession());
            int a = sysVersionsService.selectSysVersionsWebIss(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, a == 0? tab : null);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * session确认更新通知
     */
    @Log(title = "session确认更新通知", type = LogEnum.INSERT)
    @PostMapping("open/sysVersionsSessionIss.act")
    public ResultBody sysVersionsSessionIss(@RequestBody SysVersions sysVersions){
        try {
            sysVersions.getNowDate("");
            sysVersions.setVerCreate(sysVersions.getSession());
            sysVersions.setVerVersion(sysVersions.getIds());
            sysVersions.setIds(rsaKey.uuid(""));
            sysVersionsService.insertSysVersionsWebIss(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, "确认完成");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 系统更新提交
     * @param sysVersions
     */
    @Log(title = "系统更新提交", type = LogEnum.INSERT)
    @PostMapping("api/upVersionText.act")
    public ResultBody upVersionText(HttpSession session,@RequestBody SysVersions sysVersions){
        try {
            Person person = (Person)redisUtils.get(session.getId());
            if(person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }
            sysVersions.getUuidCreateUpdate(person.getIds());
            sysVersionsService.insertSysVersions(sysVersions);
            return new ResultBody(ApiResultEnum.SUCCESS, "提交完成");
        }catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取APP最新的下载地址
     * @return
     */
    @Log(title = "APP下载地址", type = LogEnum.DETIAL)
    @PostMapping("open/sysAppUpdateVersions.act")
    public ResultBody sysAppUpdateVersions(){
        try {
            SysVersionsApp tab = sysVersionsService.getNewUpdateAppVersion();
            return new ResultBody(ApiResultEnum.SUCCESS, tab);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
