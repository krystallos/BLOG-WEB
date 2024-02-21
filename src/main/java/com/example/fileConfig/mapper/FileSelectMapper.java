package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.FilePixiv;
import com.example.fileConfig.enity.sauce.SauceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileSelectMapper {

	/**
	 * 获取唯一实例
	 * @param filePixiv
	 * @return
	 */
	FilePixiv get(@Param("filePixiv") FilePixiv filePixiv);

	/**
	 * 插入文件
	 * @param filePixiv
	 */
	int insert(@Param("filePixiv") FilePixiv filePixiv);

	/**
	 * 根据uid和pid查询图片
	 * @param sauceData
	 * @return
	 */
	FilePixiv findGetPixiv(@Param("sauceData") SauceData sauceData);

}
