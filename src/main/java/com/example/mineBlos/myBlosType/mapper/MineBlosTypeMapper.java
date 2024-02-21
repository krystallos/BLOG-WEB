package com.example.mineBlos.myBlosType.mapper;

import com.example.mineBlos.myBlosTab.enity.MineBlos;
import com.example.mineBlos.myBlosType.enity.MineBlosType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MineBlosTypeMapper {

	/**
	 * 查询博客类型
	 * @param mineBlosType
	 */
	List<MineBlosType> mineBlosType(@Param("mineBlosType") MineBlosType mineBlosType);

	/**
	 * 查询单个博客
	 * @param uuid
	 */
	MineBlosType mineBlosTypeGet(@Param("uuid") String uuid);

	/**
	 * 新增博客类型数据
	 * @param mineBlosType
	 * @return
	 */
	int insertMineBlosType(@Param("mineBlosType") MineBlosType mineBlosType);

	/**
	 * 更新博客类型数据
	 * @param mineBlosType
	 * @return
	 */
	int updateMineBlosType(@Param("mineBlosType") MineBlosType mineBlosType);

}
