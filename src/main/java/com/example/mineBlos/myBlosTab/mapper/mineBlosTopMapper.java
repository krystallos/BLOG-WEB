package com.example.mineBlos.myBlosTab.mapper;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosTab.enity.MineBlosTop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客主标题实现页
 * @author Enoki
 */
@Mapper
public interface mineBlosTopMapper {

	/**
	 * 插入博客主标题
	 * @param mineBlosTop
	 */
	int writeMineBlos(@Param("mineBlosTop") MineBlosTop mineBlosTop);

	/**
	 * 更新博客主标题
	 * @param mineBlosTop
	 * @return
	 */
	int updateMineBlos(@Param("mineBlosTop") MineBlosTop mineBlosTop);

}
