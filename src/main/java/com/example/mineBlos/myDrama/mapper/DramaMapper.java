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

}
