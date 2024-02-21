package com.example.systemMsg.sysMsg.service;

import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysSayErrMsg;
import com.example.systemMsg.sysMsg.mapper.SysErrMsgMapper;
import com.example.systemMsg.sysMsg.mapper.SysSayErrMsgMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysSayErrMsgService {

    @Resource
    private SysSayErrMsgMapper sysSayErrMsgMapper;

    /**
     * 查评论数据
     * @param sysSayErrMsg
     * @return
     */
    public List<SysSayErrMsg> selectSysSayErrMsg(SysSayErrMsg sysSayErrMsg){
        return sysSayErrMsgMapper.selectSysSayErrMsg(sysSayErrMsg);
    }

    /**
     * 新增评论
     * @param sysSayErrMsg
     * @return
     */
    public int insertSysSayErrMsg(SysSayErrMsg sysSayErrMsg){
        sysSayErrMsg.setIds(rsaKey.uuid(null));
        sysSayErrMsg.getNowDate("");
        sysSayErrMsg.setDelFlag("0");
        return sysSayErrMsgMapper.insertSysSayErrMsg(sysSayErrMsg);
    }

}
