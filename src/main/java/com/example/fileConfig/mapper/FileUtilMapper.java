package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.FileUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileUtilMapper {

	/**
	 * 获取唯一实例
	 * @param fileUtil
	 * @return
	 */
	FileUtil get(@Param("fileUtil") FileUtil fileUtil);

	/**
	 * 查询文件
	 * @param fileUtil
	 */
	List<FileUtil> selectFileUtilTab(@Param("fileUtil") FileUtil fileUtil);

	/**
     * 插入文件路径（未启用存储过程）
	 * @param fileUtil
     * @return
     */
	int insertFileUtil(@Param("fileUtil") FileUtil fileUtil);


	/**
	 * 更新文件配置
	 * @param fileUtil
	 * @return
	 */
	int updateLog(@Param("fileUtil") FileUtil fileUtil);

}
