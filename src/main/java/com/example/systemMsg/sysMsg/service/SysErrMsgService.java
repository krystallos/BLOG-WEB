package com.example.systemMsg.sysMsg.service;

import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysMsg.mapper.SysErrMsgMapper;
import com.example.systemMsg.sysMsg.mapper.SysMsgMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysErrMsgService {

    @Resource
    private SysErrMsgMapper sysErrMsgMapper;

    /**
     * 查询统计数据(分页)
     * @param sysErrMsg
     * @return
     */
    public List<SysErrMsg> selectSysErrMsgTab(SysErrMsg sysErrMsg){
        return sysErrMsgMapper.selectSysErrMsgTab(sysErrMsg);
    }

    /**
     * 获取唯一值
     * @param sysErrMsg
     */
    public SysErrMsg get(SysErrMsg sysErrMsg){
        return sysErrMsgMapper.get(sysErrMsg);
    }

    /**
     * 新增BUG
     * @param sysErrMsg
     * @return
     */
     public int insetSysErrMsg(SysErrMsg sysErrMsg){
         sysErrMsg.setIds(rsaKey.uuid(null));
         sysErrMsg.getNowDate("");
         sysErrMsg.setDelFlag("0");
         sysErrMsg.setIsOk("未回复");
        return sysErrMsgMapper.insetSysErrMsg(sysErrMsg);
     }

    /**
     * 撤回BUG
     * @param sysErrMsg
     * @return
     */
    public int updateSyeErrMsg(SysErrMsg sysErrMsg){
         return sysErrMsgMapper.updateSyeErrMsg(sysErrMsg);
    }

    /**
     * 更新为解决
     * @param ids
     * @return
     */
    public int updateOkSysErrMsg(String ids){
        return sysErrMsgMapper.updateOkSysErrMsg(ids);
    }

}
