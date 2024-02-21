package com.example.menu.mapper;

import com.example.menu.enity.RoleEnity;
import com.example.menu.enity.RoleMenuEnity;
import com.example.menu.enity.RolePersonEnity;
import com.example.menu.enity.menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

	/**
	 * 角色详情
	 * @param roleEnity
	 * @return
	 */
	RoleEnity getRole(@Param("roleEnity") RoleEnity roleEnity);

	/**
	 * 获取角色
	 * @param roleEnity
	 */
	List<RoleEnity> selectTypeRole(@Param("roleEnity")RoleEnity roleEnity);

	/**
	 * 新增角色
	 * @param roleEnity
	 * @return
	 */
	int insertRole(@Param("roleEnity") RoleEnity roleEnity);

	/**
	 * 删除角色
	 * @param idList
	 * @return
	 */
	int delRole(@Param("idList")String[] idList);

	/**
	 *更新角色
	 * @param roleEnity
	 * @return
	 */
	int updateRole(@Param("roleEnity")RoleEnity roleEnity);

	/**
	 * 查询角色对应菜单
	 * @param roleMenuEnity
	 * @return
	 */
	List<RoleMenuEnity> selectRoleMenu(@Param("roleMenuEnity") RoleMenuEnity roleMenuEnity);

	/**
	 * 只获取角色拥有的菜单
	 * @param roleMenuEnity
	 * @return
	 */
	List<String> selectTransferMenu(@Param("roleMenuEnity") RoleMenuEnity roleMenuEnity);

	/**
	 * 只获取角色拥有的用户
	 * @param roleMenuEnity
	 * @return
	 */
	List<String> selectRolePersonNoPag(@Param("roleMenuEnity") RoleMenuEnity roleMenuEnity);

	/**
	 * 删除菜单
	 * @param idList
	 * @param roleId
	 * @return
	 */
	int delMenu(@Param("idList")String[] idList, @Param("roleId")String roleId);

	/**
	 * 删除用户
	 * @param idList
	 * @param roleId
	 * @return
	 */
	int delPerson(@Param("idList")String[] idList, @Param("roleId")String roleId);

	/**
	 * 查询用户
	 * @param personId
	 * @return
	 */
	String selectPersonForRole(@Param("personId")String personId);

	/**
	 * 移除该用户在其他权限下的角色信息
	 * @param personId
	 * @return
	 */
	int delAllRoleForPerson(@Param("personId")String personId);

	/**
	 * 新增菜单
	 * @param roleMenuEnity
	 * @return
	 */
	int insertMenu(@Param("roleMenuEnity")RoleMenuEnity roleMenuEnity);

	/**
	 * 新增用户
	 * @param rolePersonEnity
	 * @return
	 */
	int insertPerson(@Param("rolePersonEnity") RolePersonEnity rolePersonEnity);

	/**
	 * 查询该用户的权限
	 * @param personId
	 * @param page
	 * @return
	 */
	int hisPersonForMain(@Param("personId")String personId, @Param("page") String page);

	/**
	 * 获取默认角色
	 * @return
	 */
	String isDefRole();
}
