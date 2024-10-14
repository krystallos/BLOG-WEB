package com.example.systemMsg.sysConfig.mapper;

import com.example.systemMsg.sysConfig.enity.EditSystemConfigDicEnity;
import com.example.systemMsg.sysConfig.enity.SystemConfigDicEnity;
import com.example.systemMsg.sysMsg.enity.SysErrMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysConfigMapper {

	/**
	 * 获取项目的配置集合
	 */
	List<SystemConfigDicEnity> selectConfigDic(@Param("sysConfig")SystemConfigDicEnity enity);

	/**
	 * 根据名称获取配置
	 * @param dicKey
	 * @return
	 */
	SystemConfigDicEnity getSysConfig(@Param("dicKey")String dicKey, @Param("fileConfigType")String fileConfigType);

	/**
	 * 获取项目配置列表
	 * @param enity
	 * @return
	 */
	List<SystemConfigDicEnity> selectConfigDicFind(@Param("sysConfig")SystemConfigDicEnity enity);

	/**
	 * 获取系统详情配置
	 * @param enity
	 * @return
	 */
	SystemConfigDicEnity getConfigDic(@Param("sysConfig")SystemConfigDicEnity enity);

	/**
	 * 删除系统配置详情
	 * @param enity
	 * @return
	 */
	int delSysConfig(@Param("sysConfig")SystemConfigDicEnity enity);

	/**
	 * 新增系统配置详情
	 * @param enity
	 * @return
	 */
	int insertSysConfig(@Param("sysConfig") EditSystemConfigDicEnity enity);

	/**
	 * 修改系统配置详情
	 * @param enity
	 * @return
	 */
	int editSysConfig(@Param("sysConfig")EditSystemConfigDicEnity enity);

}
