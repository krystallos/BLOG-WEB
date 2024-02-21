package com.example.job.systemJob.mapper;

import com.example.job.systemJob.entry.SystemEntry;
import com.example.passWordCont.enity.PassWord;
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
	 * 插入系统信息
	 * @param systemEntry
	 */
	int insertSystemJob(@Param("systemEntry") SystemEntry systemEntry);

	/**
	 * 查询系统记录列表
	 * @param systemEntry
	 * @return
	 */
	List<SystemEntry> selectFindList(@Param("systemEntry") SystemEntry systemEntry);

	/**
	 * 查询记录分组，近1天
	 * @return
	 */
	List<SystemEntry> selectGroupList(@Param("beginTime")String beginTime, @Param("endTime")String endTime );
}
