package com.example.systemMsg.sysVersions.mapper;

import com.example.systemMsg.sysVersions.enity.SysVersions;
import com.example.systemMsg.sysVersions.enity.SysVersionsApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysVersionsAppMapper {

	/**
	 * 查询统计
	 * @param sysVersions
	 */
	List<SysVersions> selectSysVersions(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 获取最新版本APP
	 */
	SysVersionsApp getNewUpdateAppVersion();

	/**
	 * 更新版本迭代
	 * @param sysVersions
	 * @return
	 */
	int insertSysVersions(@Param("sysVersions")SysVersions sysVersions);

	/**
	 * 查询ID的元素
	 * @param sysVersions
	 * @return
	 */
	SysVersions get(@Param("sysVersions")SysVersions sysVersions);

}
