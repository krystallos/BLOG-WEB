package com.example.mineBlos.myDrama.mapper;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myDrama.enity.DramaList;
import com.example.util.dic.DicListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaMapper {

	/**
	 * 查看番剧列表
	 * @param dramaList
	 */
	List<DramaList> dramaList(@Param("dramaList")DramaList dramaList);

	/**
	 * 获取详细番剧
	 * @param dramaList
	 * @return
	 */
	DramaList getDetialdrama(@Param("dramaList") DramaList dramaList);

	/**
	 * 删除番剧
	 * @param dramaList
	 * @return
	 */
	int delDrama(@Param("dramaList") DramaList dramaList);

	/**
	 * 更新番剧
	 * @param dramaList
	 * @return
	 */
	int editDrama(@Param("dramaList") DramaList dramaList);

	/**
	 * 新增番剧
	 * @param dramaList
	 * @return
	 */
	int insertDrama(@Param("dramaList") DramaList dramaList);

}
