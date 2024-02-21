package com.example.systemMsg.sysVersions.mapper;

import com.example.systemMsg.sysMsg.enity.SysMsg;
import com.example.systemMsg.sysVersions.enity.SysVersions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysVersionsMapper {

	/**
	 * 查询统计
	 * @param sysVersions
	 */
	List<SysVersions> selectSysVersions(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 查询首条数据
	 * @param isHas
	 */
	SysVersions selectOneAndOneSysVersions(@Param("isHas")int isHas);

	/**
	 * 用户确认通知消息
	 * @param sysVersions
	 * @return
	 */
	int insertSysVersionsWebIss(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 查看用户是否需要提示通知
	 * @param sysVersions
	 * @return
	 */
	int selectSysVersionsWebIss(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 更新版本迭代
	 * @param sysVersions
	 * @return
	 */
	int insertSysVersionsWeb(@Param("sysVersions")SysVersions sysVersions);
	int insertSysVersionsApp(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 查询ID的元素
	 * @param sysVersions
	 * @return
	 */
	SysVersions get(@Param("sysVersions")SysVersions sysVersions);

}
