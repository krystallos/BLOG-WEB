package com.example.job.systemJob.service;

import com.example.job.systemJob.entry.DirSystemEntry;
import com.example.job.systemJob.entry.SystemConfigEnerty;
import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.entry.SystemNetWork;
import com.example.job.systemJob.mapper.SystemJobMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemJobService {

    @Resource
    private SystemJobMapper systemJobMapper;

    /**
     * 保存内存信息日志
     * @param systemEntry
     * @return
     */
    public int insertSystem(SystemEntry systemEntry){
        return systemJobMapper.insertSystem(systemEntry);
    }

    /**
     * 查询磁盘信息是否重复
     * @param sysFile
     * @return
     */
    public String selectDoubleDicCode(DirSystemEntry sysFile){
        return systemJobMapper.selectDoubleDicCode(sysFile);
    }

    /**
     * 插入磁盘信息
     * @param sysFile
     * @return
     */
    public int insertSystemDic(DirSystemEntry sysFile){
        return systemJobMapper.insertSystemDic(sysFile);
    }

    /**
     * 更新磁盘信息
     * @param sysFile
     * @return
     */
    public int updateSystemDic(DirSystemEntry sysFile){
        return systemJobMapper.updateSystemDic(sysFile);
    }

    /**
     * 查询CPU唯一信息
     * @param cpuCode
     * @return
     */
    public String selectDoubleCpuCode(String cpuCode){
        return systemJobMapper.selectDoubleCpuCode(cpuCode);
    }

    /**
     * 插入日志
     * @param
     * @return
     */
    public int insertSystemConfig(SystemConfigEnerty enerty){
        return systemJobMapper.insertSystemConfig(enerty);
    }

    /**
     * 更新日志
     * @param enerty
     * @return
     */
    public int updateSystemConfig(SystemConfigEnerty enerty){
        return systemJobMapper.updateSystemConfig(enerty);
    }

    /**
     * 插入网络信息
     * @param netWork
     * @return
     */
    public int insertSystemNetWork(SystemNetWork netWork){
        return systemJobMapper.insertSystemNetWork(netWork);
    }

    /**
     * 获取系统信息
     * @param systemConfigEnerty
     * @return
     */
    public SystemConfigEnerty getSystemConfig(SystemConfigEnerty systemConfigEnerty){
        return systemJobMapper.getSystemConfig(systemConfigEnerty);
    }

    /**
     * 获取系统内存信息
     * @param systemEntry
     * @return
     */
    public SystemEntry selectSystem(SystemEntry systemEntry){
        return systemJobMapper.selectSystem(systemEntry);
    }

    /**
     * 获取系统磁盘信息
     * @param dirSystemEntry
     * @return
     */
    public List<DirSystemEntry> selectDic(DirSystemEntry dirSystemEntry){
        PageHelper.offsetPage(dirSystemEntry.getStartTab(), dirSystemEntry.getHasTab());
        List<DirSystemEntry> list = systemJobMapper.selectDic(dirSystemEntry);
        return list;
    }

    /**
     * 查询柱状图
     * @return
     */
    public List<SystemEntry> selectGroupList(String beginTime, String endTime, String ip){
        return systemJobMapper.selectGroupList(beginTime, endTime, ip);
    }

    /**
     * 查询柱状图
     * @return
     */
    public List<SystemNetWork> selectGroupNetWork(String beginTime, String endTime, String ip){
        return systemJobMapper.selectGroupNetWork(beginTime, endTime, ip);
    }

}
