package com.example.systemMsg.sysMsg.mapper;

import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysErrMsgMapper {

	/**
	 * 查询统计
	 * @param sysErrMsgMsg
	 */
	List<SysErrMsg> selectSysErrMsgTab(@Param("sysErrMsg")SysErrMsg sysErrMsgMsg);

	/**
	 * 获取唯一实体
	 * @param sysErrMsg
	 * @return
	 */
	SysErrMsg get(@Param("sysErrMsg")SysErrMsg sysErrMsg);

	/**
	 * 新增BUG
	 * @param sysErrMsg
	 * @return
	 */
	int insetSysErrMsg(@Param("sysErrMsg")SysErrMsg sysErrMsg);

	/**
	 * 撤回BUG
	 * @param sysErrMsg
	 * @return
	 */
	int updateSyeErrMsg(@Param("sysErrMsg")SysErrMsg sysErrMsg);

	/**
	 * 更新为已解决
	 * @param ids
	 * @return
	 */
	int updateOkSysErrMsg(@Param("ids")String ids);
}
