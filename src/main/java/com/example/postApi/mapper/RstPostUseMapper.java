package com.example.postApi.mapper;

import com.example.postApi.enity.PostApiEnity;
import com.example.postApi.enity.RstChildPostApi;
import com.example.postApi.enity.RstPostApiVo;
import com.example.postApi.enity.RstPostProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口查询实现页
 * @author Enoki
 */
@Mapper
public interface RstPostUseMapper {

	/**
	 * 查询接口列表
	 * @param rstPostProject
	 */
	List<RstPostProject> rstPostList(@Param("rstPostProject") RstPostProject rstPostProject);
	List<RstPostProject> rstPostChildList(@Param("ids") String ids);
	List<RstPostProject> rstOpenPostList(@Param("rstPostProject") RstPostProject rstPostProject);

	/**
	 * 分页查询
	 * @param rstPostProject
	 * @return
	 */
	int rstPostListCount(@Param("rstPostProject") RstPostProject rstPostProject);
	int rstOpenPostListCount(@Param("rstPostProject") RstPostProject rstPostProject);

	/**
	 * 根据项目名称查询是否重复
	 * @param rstPostProject
	 * @return
	 */
	int rstPostListDouble(@Param("rstPostProject") RstPostProject rstPostProject);

	/**
	 * 获取文档详情
	 * @param rstPostApiVo
	 * @return
	 */
	RstChildPostApi getRstPost(@Param("rstPostApiVo") RstPostApiVo rstPostApiVo);

	/**
	 * 修改文档详情
	 * @param rstPostProject
	 * @return
	 */
	int editRstPost(@Param("rstPostProject") RstPostProject rstPostProject);

	/**
	 * 获取文档内的接口列表
	 * @param rstPostApiVo
	 * @return
	 */
	List<RstPostApiVo> getRstPostList(@Param("rstPostApiVo") RstPostApiVo rstPostApiVo);

	/**
	 * 查询文章子菜单下拉框
	 * @return
	 */
	List<RstPostProject> getRstPostChiList();

	/**
	 * 保存API文档
	 * @return
	 */
	int insertRstPostApi(@Param("rstPostProject") RstPostProject rstPostProject);

	/**
	 * 删除文档接口
	 * @param ids
	 * @return
	 */
	int rstDelete(@Param("ids") String ids);

	/**
	 * 删除接口文档
	 * @param ids
	 * @return
	 */
	int rstPostDelete(@Param("ids") String ids);

	/**
	 * 删除接口
	 * @param ids
	 * @return
	 */
	int rstPostApiDelete(@Param("ids") String ids);

	/**
	 * 新增API接口
	 * @param rstPostApiVo
	 * @return
	 */
	int insertRstApiDetial(@Param("rstPostApiVo") RstPostApiVo rstPostApiVo);

	/**
	 * 修改API接口
	 * @param rstPostApiVo
	 * @return
	 */
	int saveRstApiDetial(@Param("rstPostApiVo") RstPostApiVo rstPostApiVo);

	/**
	 * 获取API详情
	 * @param rstPostApiVo
	 * @return
	 */
	RstPostApiVo getRstApiDetial(@Param("rstPostApiVo") RstPostApiVo rstPostApiVo);

}
