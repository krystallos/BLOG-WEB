package com.example.systemMsg.sysConfig.service;

import com.example.systemMsg.sysConfig.enity.EditSystemConfigDicEnity;
import com.example.systemMsg.sysConfig.enity.SystemConfigDicEnity;
import com.example.systemMsg.sysConfig.mapper.SysConfigMapper;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.mapper.SysMsgMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysConfigService {

    @Resource
    private SysConfigMapper sysconfigMapper;

    /**
     * 获取项目的配置集合
     */
    public List<SystemConfigDicEnity> selectConfigDic(SystemConfigDicEnity enity){
        return sysconfigMapper.selectConfigDic(enity);
    }

    /**
     * 获取项目配置列表
     */
    public List<SystemConfigDicEnity> selectConfigDicFind(SystemConfigDicEnity enity){
        PageHelper.offsetPage(enity.getStartTab(), enity.getHasTab(),true);
        List<SystemConfigDicEnity> enityList = sysconfigMapper.selectConfigDicFind(enity);
        return enityList;
    }

    /**
     * 获取项目的配置
     */
    public SystemConfigDicEnity getConfigDic(SystemConfigDicEnity enity){
        return sysconfigMapper.getConfigDic(enity);
    }

    /**
     * 删除项目的配置
     */
    public int delSysConfig(SystemConfigDicEnity enity){
        return sysconfigMapper.delSysConfig(enity);
    }

    /**
     * 新增项目的配置
     */
    public int insertSysConfig(EditSystemConfigDicEnity enity){
        enity.getNowDate(null);
        enity.setIds(rsaKey.uuid(""));
        return sysconfigMapper.insertSysConfig(enity);
    }

    /**
     * 修改项目的配置
     */
    public int editSysConfig(EditSystemConfigDicEnity enity){
        return sysconfigMapper.editSysConfig(enity);
    }

}
