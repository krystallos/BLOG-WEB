package com.example.menu.mapper;

import com.example.menu.enity.menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {

	/**
	 * 获取菜单
	 * @param menu
	 */
	List<menu> selectTypeMenu(@Param("menu")menu menu);
	List<menu> selectTypePageMenu(@Param("menu")menu menu);

	/**
	 * 查询顶级菜单
	 * @return
	 */
	List<menu> selectNoneUrlMenu();

	/**
	 * 查询全部菜单
	 * @return
	 */
	List<menu> selectAllMenu();

	/**
	 * 根据新的权限配置系统获取菜单_包含App
	 * @param personId
	 * @return
	 */
	List<menu> selectTypeMenuForRole(@Param("personId")String personId);
	List<menu> selectTypeMenuForRoleApp(@Param("personId")String personId);
	List<menu> selectTypeMenuForRoleSmileApp(@Param("personId")String personId,@Param("ids")String ids);

	/**
	 * 插入菜单
	 * @param menu
	 * @return
	 */
	int insertMenuAdd(@Param("menu")menu menu);

	/**
	 * 查询ID或SORT最大的值
	 * @return
	 */
	String maxMenuSortOrId();

}
