package com.example.systemMsg.sysMsg.mapper;

import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysSayErrMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysSayErrMsgMapper {

	/**
	 * 查询统计
	 * @param sysSayErrMsg
	 */
	List<SysSayErrMsg> selectSysSayErrMsg(@Param("sysSayErrMsg")SysSayErrMsg sysSayErrMsg);

	/**
	 * 新增评论
	 * @param sysSayErrMsg
	 * @return
	 */
	int insertSysSayErrMsg(@Param("sysSayErrMsg")SysSayErrMsg sysSayErrMsg);
}
