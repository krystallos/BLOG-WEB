package com.example.likeMenu.mapper;

import com.example.likeMenu.enity.LikeMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMenuMapper {

	/**
	 * 获取社区
	 * @param likeMenu
	 */
	List<LikeMenu> selectLikeMenu(@Param("likeMenu") LikeMenu likeMenu);

	/**
	 * 更新社区信息
	 * @param likeMenu
	 * @return
	 */
	int updateLikeMenuTab(@Param("likeMenu") LikeMenu likeMenu);

	/**
	 * 删除/启用社区信息(逻辑)
	 * @param likeMenu
	 * @return
	 */
	int delLikeMenuTab(@Param("likeMenu") LikeMenu likeMenu);

	/**
	 * 获取社区信息
	 * @param ids
	 * @return
	 */
	LikeMenu getLikeMenu(@Param("ids") String ids);

	/**
	 * 新增社区信息
	 * @param likeMenu
	 * @return
	 */
	int insertLikeMenuTab(@Param("likeMenu") LikeMenu likeMenu);

	/**
	 * 查询默认社区
	 * @return
	 */
	LikeMenu selectDefLikeMenu();
}
