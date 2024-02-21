package com.example.menu.service;

import com.example.menu.enity.menu;
import com.example.menu.mapper.MenuMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取菜单
     * @param menu 菜单实体类
     * @return
     */
    public List<menu> selectTypeMenu(menu menu){
        return menuMapper.selectTypeMenu(menu);
    }

    /**
     * 获取菜单(分页)
     * @param menu 菜单实体类
     * @return
     */
    public List<menu> selectTypePageMenu(menu menu){
        PageHelper.offsetPage(menu.getStartTab(), menu.getHasTab());
        List<menu> menuList = menuMapper.selectTypePageMenu(menu);
        return menuList;
    }

    /**
     * 查询顶级菜单
     * @return
     */
    public List<menu> selectNoneUrlMenu(){
        return menuMapper.selectNoneUrlMenu();
    }

    public Map<String,String> selectAllMenu(){
        Map<String,String> temp = new HashMap<>();
        List<menu> list = menuMapper.selectAllMenu();
        for(menu item : list){
            temp.put(item.getMenuUrl(),item.getTabName());
        }
        return temp;
    }

    /**
     * 根据新的权限配置系统获取菜单
     * @param personId
     * @return
     */
    public List<menu> selectTypeMenuForRole(String personId){
        return menuMapper.selectTypeMenuForRole(personId);
    }

    /**
     * 根据新的权限配置系统获取菜单_APP
     * @param personId
     * @return
     */
    public List<menu> selectTypeMenuForRoleApp(String personId){
        return menuMapper.selectTypeMenuForRoleApp(personId);
    }
    public List<menu> selectTypeMenuForRoleSmileApp(String personId ,String ids){
        return menuMapper.selectTypeMenuForRoleSmileApp(personId, ids);
    }

    /**
     * 插入菜单
     * @param menu
     * @return
     */
    public int insertMenuAdd(menu menu){
        menu.setIds((maxMenuSortOrId()+1)+"");
        menu.setSort(menu.getIds());
        menu.getNowDate("");
        menu.setDelFlag("0");
        return menuMapper.insertMenuAdd(menu);
    }

    public int maxMenuSortOrId(){
        int max = Integer.parseInt(menuMapper.maxMenuSortOrId());
        return max;
    }

}
