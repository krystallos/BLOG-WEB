package com.example.postApi.mapper;

import com.example.postApi.enity.PostApiEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口查询实现页
 * @author Enoki
 */
@Mapper
public interface ApiPostListMapper {

	/**
	 * 查询接口列表
	 * @param postApiEnity
	 */
	List<PostApiEnity> apiPostListPage(@Param("postApiEnity") PostApiEnity postApiEnity);

	/**
	 * 查询接口列表总数
	 * @param postApiEnity
	 * @return
	 */
	int countApiPostListPage(@Param("postApiEnity") PostApiEnity postApiEnity);

	/**
	 * 新增接口记录
	 */
	int insertApiPost(@Param("postApiEnity") PostApiEnity postApiEnity);

	/**
	 * 删除接口记录
	 * @param postApiEnity
	 * @return
	 */
	int deleteApiPost(@Param("postApiEnity") PostApiEnity postApiEnity);

}
