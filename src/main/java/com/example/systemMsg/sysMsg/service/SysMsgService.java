package com.example.systemMsg.sysMsg.service;

import com.example.menu.enity.menu;
import com.example.menu.mapper.MenuMapper;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.mapper.SysMsgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMsgService {

    @Resource
    private SysMsgMapper sysMsgMapper;

    /**
     * 查询统计数据
     * @param  查询实体类
     * @return
     */
    public SysMsg selectSysMsg(String uuid){
        return sysMsgMapper.selectSysMsg(uuid);
    }

}
