package com.example.systemMsg.sysVersions.service;

import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.mapper.SysMsgMapper;
import com.example.systemMsg.sysVersions.enity.SysVersions;
import com.example.systemMsg.sysVersions.enity.SysVersionsApp;
import com.example.systemMsg.sysVersions.mapper.SysVersionsAppMapper;
import com.example.systemMsg.sysVersions.mapper.SysVersionsMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysVersionsService {

    @Resource
    private SysVersionsMapper sysVersionsMapper;
    @Resource
    private SysVersionsAppMapper sysVersionsAppMapper;

    /**
     * 查询更新数据
     * @param
     * @return
     */
    public List<SysVersions> selectSysVersions(SysVersions sysVersions){
        return sysVersionsMapper.selectSysVersions(sysVersions);
    }

    /**
     * 查询首条更新数据
     * @param isHasDay
     * @return
     */
    public SysVersions selectOneAndOneSysVersions(int isHasDay){
        return sysVersionsMapper.selectOneAndOneSysVersions(isHasDay);
    }

    /**
     * 查看用户是否需要提示通知
     * @param sysVersions
     * @return
     */
    public int selectSysVersionsWebIss(SysVersions sysVersions){
        return sysVersionsMapper.selectSysVersionsWebIss(sysVersions);
    }

    /**
     * 用户确认通知消息
     * @param sysVersions
     * @return
     */
    public int insertSysVersionsWebIss(SysVersions sysVersions){
        return sysVersionsMapper.insertSysVersionsWebIss(sysVersions);
    }

    /**
     * 同步日志
     * @param
     * @return
     */
    public int insertSysVersions(SysVersions sysVersions){
        sysVersions.getNowDate("");
        sysVersions.setDelFlag("0");
        sysVersions.setIds(rsaKey.uuid(""));
        if(sysVersions.getSysType().equals("1")){
            return sysVersionsMapper.insertSysVersionsWeb(sysVersions);
        }else{
            return sysVersionsMapper.insertSysVersionsApp(sysVersions);
        }
    }

    /**
     * 获取唯一实例
     * @param sysVersions
     * @return
     */
    public SysVersions get(SysVersions sysVersions){
        return sysVersionsMapper.get(sysVersions);
    }

    /**
     * 获取最新版本APP
     * @return
     */
    public SysVersionsApp getNewUpdateAppVersion(){
        return sysVersionsAppMapper.getNewUpdateAppVersion();
    }


}
