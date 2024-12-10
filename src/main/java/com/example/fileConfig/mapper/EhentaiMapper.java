package com.example.fileConfig.mapper;

import com.example.fileConfig.enity.ehentai.GetEhentaiVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EhentaiMapper {

	/**
	 * 获取唯一实例
	 * @param getEhentaiVo
	 * @return
	 */
	GetEhentaiVo get(@Param("getEhentaiVo")GetEhentaiVo getEhentaiVo);

	/**
	 * 查询文件
	 */
	List<GetEhentaiVo> selectEhentaiName();

	/**
	 * 获取分页文件
	 * @param getEhentaiVo
	 * @return
	 */
	List<GetEhentaiVo> selectEhentaiFile(@Param("getEhentaiVo")GetEhentaiVo getEhentaiVo);

	/**
	 * 新增文件
	 * @param getEhentaiVo
	 * @return
	 */
	int insertEhentai(@Param("getEhentaiVo")GetEhentaiVo getEhentaiVo);

	/**
	 * 更新文件
	 * @param getEhentaiVo
	 * @return
	 */
	int updateEhentai(@Param("getEhentaiVo")GetEhentaiVo getEhentaiVo);

}
