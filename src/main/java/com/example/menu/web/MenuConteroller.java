package com.example.menu.web;

import com.example.menu.enity.*;
import com.example.menu.service.MenuService;

import com.example.menu.service.RoleService;
import com.example.person.enity.Person;
import com.example.person.service.PersonService;
import com.example.util.*;
import com.example.util.annotion.Log;
import com.example.util.config.RedisUtils;
import com.example.util.dic.DicVo;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 菜单-角色控制页
 * @author Enoki
 */
@RestController
public class MenuConteroller {

    private static final Logger log = Logger.getLogger(MenuConteroller.class);

    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private PersonService personService;

    /**
     * 角色列表
     * @param roleEnity
     */
    @Log(title = "获取角色列表", type = LogEnum.SELECT)
    @PostMapping("api/roleTypeList.act")
    public ResultBody roleTypeList(@RequestBody RoleEnity roleEnity) {
        try {
            roleEnity.pubImplPage(roleEnity.getNowTab(), roleEnity.getHasTab());
            List<RoleEnity> listTab = roleService.selectTypeRole(roleEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, listTab, (int) new PageInfo<>(listTab).getTotal());
        } catch (Exception e) {
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 添加角色
     * @param roleEnity
     */
    @Log(title = "添加角色", type = LogEnum.INSERT)
    @PostMapping("api/addRole.act")
    public ResultBody addRole(@RequestBody RoleEnity roleEnity){
        try{
            roleService.insertRole(roleEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, "建立成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 删除角色
     */
    @Log(title = "删除角色", type = LogEnum.DELETE)
    @PostMapping("api/delRole.act")
    public ResultBody delRole( @RequestBody RoleEnity roleEnity){
        try{
            String[] ids = new String[]{roleEnity.getIds()};
            roleService.delRole(ids);
            return new ResultBody(ApiResultEnum.SUCCESS, "删除成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 更新角色
     * @param roleEnity
     */
    @Log(title = "更新角色", type = LogEnum.EDIT)
    @PostMapping("api/updateRole.act")
    public ResultBody updateRole(@RequestBody RoleEnity roleEnity){
        try{
            roleService.updateRole(roleEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, "名字修改为《"+ roleEnity.getRoleName() +"》");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取角色详细信息
     */
    @Log(title = "获取角色详情", type = LogEnum.DETIAL)
    @PostMapping("api/roleInfo.act")
    public ResultBody roleInfo(@RequestBody RoleEnity roleEnity){
        try {
            roleEnity = roleService.getRole(roleEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, roleEnity);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 角色拥有的菜单
     * @param roleMenuEnity
     * @return
     */
    @Log(title = "获取角色拥有的菜单", type = LogEnum.SELECT)
    @PostMapping("api/roleMenuTab.act")
    public ResultBody roleMenuTab(@RequestBody RoleMenuEnity roleMenuEnity){
        try{
            roleMenuEnity.pubImplPage(roleMenuEnity.getNowTab(), roleMenuEnity.getHasTab());
            List<RoleMenuEnity> list = roleService.selectRoleMenu(roleMenuEnity);
            return new ResultBody(ApiResultEnum.SUCCESS, list, (int) new PageInfo<>(list).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 菜单穿梭框
     * @param roleMenuEnity
     */
    @Log(title = "角色菜单穿梭框信息", type = LogEnum.SELECT)
    @PostMapping("api/funcRoleMenu.act")
    public ResultBody funcRoleMenu(@RequestBody RoleMenuEnity roleMenuEnity){
        try{
            menu menu = new menu();
            //全部菜单
            List<menu> menuType = menuService.selectTypeMenu(menu);
            Map<String, String> mapAll = new HashMap<>();
            Map<String, String> mapHas = new HashMap<>();
            Map<String, String> mapHasList = new HashMap<>();
            Map<String, List<DicVo>> returnMap = new HashMap<>();
            for (int a=0;a<menuType.size();a++) {
                mapAll.put(menuType.get(a).getIds(), menuType.get(a).getTabName());
            }
            List<String> list = roleService.selectRoleMenuNoPag(roleMenuEnity);
            for (int a=0;a<list.size();a++) {
                mapHas.put(list.get(a), "menu");
            }
            for(Map.Entry entry : mapAll.entrySet()){
                if(mapHas.get(entry.getKey()) != null){
                    mapHasList.put((String)entry.getKey(), (String)entry.getValue());
                }
            }
            List<DicVo> mapAllListStream = mapAll.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                    .map(e -> DicVo.builder().key(e.getKey()).label(e.getValue()).build()).collect(Collectors.toList());
            List<DicVo> mapHasListStream = mapHasList.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                    .map(e -> DicVo.builder().key(e.getKey()).label(e.getValue()).build()).collect(Collectors.toList());
            returnMap.put("allMenu", mapAllListStream);
            returnMap.put("hasMenu", mapHasListStream);
            return new ResultBody(ApiResultEnum.SUCCESS, returnMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 用户穿梭框
     * @param roleMenuEnity
     */
    @Log(title = "用户穿梭框信息", type = LogEnum.SELECT)
    @PostMapping("api/funcRolePerson.act")
    public ResultBody funcRolePerson(@RequestBody RoleMenuEnity roleMenuEnity){
        try{
            Person person = new Person();
            //全部菜单
            List<Person> personType = personService.selectAllListPerson(person);
            Map<String, String> mapAll = new HashMap<>();
            Map<String, String> mapHas = new HashMap<>();
            Map<String, String> mapHasList = new HashMap<>();
            Map<String, List<DicVo>> returnMap = new HashMap<>();
            for (int a=0;a<personType.size();a++) {
                mapAll.put(personType.get(a).getIds(), personType.get(a).getPsnName());
            }
            List<String> list = roleService.selectRolePersonNoPag(roleMenuEnity);
            for (int a=0;a<list.size();a++) {
                mapHas.put(list.get(a), "menu");
            }
            for(Map.Entry entry : mapAll.entrySet()){
                if(mapHas.get(entry.getKey()) != null){
                    mapHasList.put((String)entry.getKey(), (String)entry.getValue());
                }
            }
            List<DicVo> mapAllListStream = mapAll.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                    .map(e -> DicVo.builder().key(e.getKey()).label(e.getValue()).build()).collect(Collectors.toList());
            List<DicVo> mapHasListStream = mapHasList.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                    .map(e -> DicVo.builder().key(e.getKey()).label(e.getValue()).build()).collect(Collectors.toList());
            returnMap.put("allMenu", mapAllListStream);
            returnMap.put("hasMenu", mapHasListStream);
            return new ResultBody(ApiResultEnum.SUCCESS, returnMap);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 操作穿梭菜单数据
     */
    @Log(title = "操作菜单穿梭框数据", type = LogEnum.EDIT)
    @PostMapping("api/updateResMenu.act")
    public ResultBody updateResMenu(@RequestBody AddRoleTypeEnity addRoleTypeEnity){
        try{
            String[] idList = addRoleTypeEnity.getIds();
            if(addRoleTypeEnity.getType().equals("1")) {
                roleService.delMenu(idList, addRoleTypeEnity.getRoleId());
            }else if(addRoleTypeEnity.getType().equals("0")){
                for(int a=0;a<idList.length;a++){
                    roleService.insertMenu(idList[a], addRoleTypeEnity.getRoleId());
                }
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "操作成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 操作穿梭用户数据
     */
    @Log(title = "操作用户穿梭框数据", type = LogEnum.EDIT)
    @PostMapping("api/updateResPerson.act")
    public ResultBody updateResPerson(@RequestBody AddRoleTypeEnity addRoleTypeEnity){
        try{
            String[] idList = addRoleTypeEnity.getIds();
            if(addRoleTypeEnity.getType().equals("1")) {
                roleService.delPerson(idList, addRoleTypeEnity.getRoleId());
            }else if(addRoleTypeEnity.getType().equals("0")){
                for(int a=0;a<idList.length;a++){
                    String psid = roleService.selectPersonForRole(idList[a]);
                    if(null != psid && psid.length()>0){
                        roleService.delAllRoleForPerson(psid);
                    }
                    roleService.insertPerson(idList[a],addRoleTypeEnity.getRoleId());
                }
            }
            return new ResultBody(ApiResultEnum.SUCCESS, "操作成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 获取用户菜单
     */
    @Log(title = "获取用户菜单", type = LogEnum.SELECT)
    @PostMapping("api/menuGet.act")
    public ResultBody menuGet(HttpSession session){
        try{
            Person personId = (Person)redisUtils.get(session.getId());
            List<menu> menuType = new ArrayList<>();
            if(personId==null){
                return new ResultBody(ApiResultEnum.NOT_FIND_DATA, "找不到该用户");
            }else {
                menuType = menuService.selectTypeMenuForRole(personId.getIds());
            }

            //存储结果
            List<menu> father = new ArrayList<menu>();
            //遍历MENU lists : list
            for (int b = 0; b < menuType.size(); b++) {
                //获取父级
                if (menuType.get(b).getPramId() == null) {
                    //子集结果
                    List<menu> chidList = new ArrayList<menu>();
                    for (int a = 0; a < menuType.size(); a++) {
                        if (menuType.get(b).getIds().equals(menuType.get(a).getPramId())) {
                            chidList.add(menuType.get(a));
                        }
                    }
                    menuType.get(b).setListMenu(chidList);
                    father.add(menuType.get(b));
                }
            }
            return new ResultBody(ApiResultEnum.SUCCESS, father);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 菜单列表
     * @param menu
     */
    @Log(title = "菜单列表", type = LogEnum.SELECT)
    @PostMapping("api/menuAddTab.act")
    public ResultBody menuAddTab(@RequestBody menu menu){
        try{
            menu.pubImplPage(menu.getNowTab(), menu.getHasTab());
            List<menu> list = menuService.selectTypePageMenu(menu);
            return new ResultBody(ApiResultEnum.SUCCESS, list, (int) new PageInfo<>(list).getTotal());
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 菜单新增
     */
    @Log(title = "新增菜单", type = LogEnum.INSERT)
    @PostMapping("api/addNewMenu.act")
    public ResultBody addNewMenu(HttpSession session, @RequestBody menu menu){
        try {
            Person personId = (Person)redisUtils.get(session.getId());
            menu.setCreateId(personId.getIds());
            menuService.insertMenuAdd(menu);
            return new ResultBody(ApiResultEnum.SUCCESS, "添加成功");
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    /**
     * 查询父级菜单
     */
    @Log(title = "获取父级菜单", type = LogEnum.SELECT)
    @PostMapping("api/getTopMenu.act")
    public ResultBody getTopMenu(){
        try {
            List<menu> menu = menuService.selectNoneUrlMenu();
            return new ResultBody(ApiResultEnum.SUCCESS, menu);
        }catch (Exception e){
            log.error(e);
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

}
