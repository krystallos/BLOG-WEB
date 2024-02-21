package com.example.menu.service;

import com.example.menu.enity.RoleEnity;
import com.example.menu.enity.RoleMenuEnity;
import com.example.menu.enity.RolePersonEnity;
import com.example.menu.enity.menu;
import com.example.menu.mapper.RoleMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 获取角色
     * @param roleEnity 菜单实体类
     * @return
     */
    public List<RoleEnity> selectTypeRole(RoleEnity roleEnity){
        PageHelper.offsetPage(roleEnity.getStartTab(), roleEnity.getHasTab());
        return roleMapper.selectTypeRole(roleEnity);
    }

    public RoleEnity getRole(RoleEnity roleEnity){
        return roleMapper.getRole(roleEnity);
    }

    /**
     * 新增角色
     * @param roleEnity
     * @return
     */
    public int insertRole(RoleEnity roleEnity){
        roleEnity.setIds(rsaKey.uuid(null));
        roleEnity.setDelFlag("0");
        roleEnity.setIsFlag("0");
        roleEnity.setIsDefule("1");
        roleEnity.setCreateDate(roleEnity.getNowDate(""));
        roleEnity.setCreateId("1");
        return roleMapper.insertRole(roleEnity);
    }

    /**
     * 删除角色
     * @param idList
     * @return
     */
    public int delRole(String[] idList){
        return roleMapper.delRole(idList);
    }

    /**
     * 更新角色
     * @param roleEnity
     * @return
     */
    public int updateRole(RoleEnity roleEnity){
        return roleMapper.updateRole(roleEnity);
    }

    /**
     * 获取角色指定的菜单
     * @param roleMenuEnity
     * @return
     */
    public List<RoleMenuEnity> selectRoleMenu(RoleMenuEnity roleMenuEnity){
        PageHelper.offsetPage(roleMenuEnity.getStartTab(), roleMenuEnity.getHasTab());
        return roleMapper.selectRoleMenu(roleMenuEnity);
    }

    /**
     * 获取角色指定的菜单ID
     * @param roleMenuEnity
     * @return
     */
    public List<String> selectRoleMenuNoPag(RoleMenuEnity roleMenuEnity){
        return roleMapper.selectTransferMenu(roleMenuEnity);
    }

    /**
     * 获取角色指定的用户ID
     * @param roleMenuEnity
     * @return
     */
    public List<String> selectRolePersonNoPag(RoleMenuEnity roleMenuEnity){
        return roleMapper.selectRolePersonNoPag(roleMenuEnity);
    }

    /**
     * 删除菜单-角色配置
     * @param idList
     * @param roleId
     * @return
     */
    public int delMenu(String[] idList, String roleId){
        return roleMapper.delMenu(idList,roleId);
    }

    /**
     * 删除用户-角色配置
     * @param idList
     * @param roleId
     * @return
     */
    public int delPerson(String[] idList, String roleId){
        return roleMapper.delPerson(idList,roleId);
    }

    /**
     * 查询用户是否出现在角色-菜单表
     * @param personId
     * @return
     */
    public String selectPersonForRole(String personId){
        return roleMapper.selectPersonForRole(personId);
    }

    /**
     * 移除该用户在其他权限下的角色信息
     * @param personId
     * @return
     */
    public int delAllRoleForPerson(String personId){
        return roleMapper.delAllRoleForPerson(personId);
    }

    /**
     * 新增菜单-角色配置
     * @param menuId
     * @param roleId
     * @return
     */
    public int insertMenu(String menuId, String roleId){
        RoleMenuEnity roleMenuEnity = new RoleMenuEnity();
        roleMenuEnity.setIds(rsaKey.uuid(null));
        roleMenuEnity.setRoleId(roleId);
        roleMenuEnity.setMenuId(menuId);
        roleMenuEnity.setCreateId("1");
        roleMenuEnity.setCreateDate(roleMenuEnity.getNowDate(""));
        return roleMapper.insertMenu(roleMenuEnity);
    }

    /**
     * 新增用户-角色配置
     * @param personId
     * @param roleId
     * @return
     */
    public int insertPerson(String personId, String roleId){
        RolePersonEnity rolePersonEnity = new RolePersonEnity();
        rolePersonEnity.setIds(rsaKey.uuid(null));
        rolePersonEnity.setRoleId(roleId);
        rolePersonEnity.setPersonId(personId);
        rolePersonEnity.setCreateId("1");
        rolePersonEnity.setCreateDate(rolePersonEnity.getNowDate(""));
        return roleMapper.insertPerson(rolePersonEnity);
    }

    /**
     * 查询这个用户ID是否拥有这个菜单
     */
    public int hisPersonForMain(String personId, String page){
        return roleMapper.hisPersonForMain(personId,page);
    }

    /**
     * 获取默认角色
     * @return
     */
    public String isDefRole(){
        return roleMapper.isDefRole();
    }
}
