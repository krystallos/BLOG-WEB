package com.example.systemMsg.sysMsg.mapper;

import com.example.menu.enity.menu;
import com.example.systemMsg.sysMsg.enity.SysMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMsgMapper {

	/**
	 * 查询统计
	 * @param uuid
	 */
	SysMsg selectSysMsg(@Param("uuid")String uuid);

}
