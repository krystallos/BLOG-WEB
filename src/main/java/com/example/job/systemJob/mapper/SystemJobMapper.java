package com.example.job.systemJob.mapper;

import com.example.job.systemJob.entry.DirSystemEntry;
import com.example.job.systemJob.entry.SystemConfigEnerty;
import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.entry.SystemNetWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统信息
 * @author Enoki
 */
@Mapper
public interface SystemJobMapper {

	/**
	 * 插入内存信息
	 * @param systemEntry
	 * @return
	 */
	int insertSystem(@Param("systemEntry") SystemEntry systemEntry);

	/**
	 * 查询磁盘信息是否重复
	 * @param sysFile
	 * @return
	 */
	String selectDoubleDicCode(@Param("sysFile") DirSystemEntry sysFile);

	/**
	 * 插入磁盘信息
	 * @param sysFile
	 * @return
	 */
	int insertSystemDic(@Param("sysFile") DirSystemEntry sysFile);

	/**
	 * 更新磁盘信息
	 * @param sysFile
	 * @return
	 */
	int updateSystemDic(@Param("sysFile") DirSystemEntry sysFile);

	/**
	 * 查询CPU唯一信息
	 * @param cpuCode
	 * @return
	 */
	String selectDoubleCpuCode(@Param("cpuCode") String cpuCode);

	/**
	 * 插入系统信息
	 * @param enerty
	 * @return
	 */
	int insertSystemConfig(@Param("enerty") SystemConfigEnerty enerty);

	/**
	 * 更新系统信息
	 * @param enerty
	 * @return
	 */
	int updateSystemConfig(@Param("enerty") SystemConfigEnerty enerty);

	/**
	 * 插入网络信息
	 * @param netWork
	 * @return
	 */
	int insertSystemNetWork(@Param("netWork") SystemNetWork netWork);

	/**
	 * 获取系统信息
	 * @param systemConfigEnerty
	 * @return
	 */
	SystemConfigEnerty getSystemConfig(@Param("systemConfigEnerty") SystemConfigEnerty systemConfigEnerty);

	/**
	 * 获取系统信息
	 * @param systemEntry
	 * @return
	 */
	SystemEntry selectSystem(@Param("systemEntry") SystemEntry systemEntry);

	/**
	 * 获取磁盘信息
	 * @param dirSystemEntry
	 * @return
	 */
	List<DirSystemEntry> selectDic(@Param("dirSystemEntry") DirSystemEntry dirSystemEntry);

	/**
	 * 查询记录分组，近1天
	 * @return
	 */
	List<SystemEntry> selectGroupList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("systemId")String systemId);

	/**
	 * 查询网络分组，近1天
	 * @param beginTime
	 * @param endTime
	 * @param systemId
	 * @return
	 */
	List<SystemNetWork> selectGroupNetWork(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("systemId")String systemId);
}
