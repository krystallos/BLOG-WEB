package com.example.job.systemJob.service;

import com.example.job.systemJob.entry.SystemEntry;
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
     * 插入日志
     * @param
     * @return
     */
    public int insertSystemJob(SystemEntry systemEntry){
        return systemJobMapper.insertSystemJob(systemEntry);
    }

    /**
     * 查询列表
     * @param systemEntry
     * @return
     */
    public List<SystemEntry> selectFindList(SystemEntry systemEntry){
        PageHelper.offsetPage(systemEntry.getStartTab(), systemEntry.getHasTab());
        List<SystemEntry> list = systemJobMapper.selectFindList(systemEntry);
        return list;
    }

    /**
     * 查询柱状图
     * @return
     */
    public List<SystemEntry> selectGroupList(String beginTime, String endTime){
        return systemJobMapper.selectGroupList(beginTime, endTime);
    }
}
