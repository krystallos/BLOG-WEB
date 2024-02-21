package com.example.menu.web.app;

import com.example.menu.enity.menu;
import com.example.menu.service.MenuService;
import com.example.nachrichten.web.app.NachrichtenAppConteroller;
import com.example.person.enity.Person;
import com.example.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 菜单-角色控制页
 * @author Enoki
 */
@RestController
public class MenuAppConteroller {

    private static final Logger log = Logger.getLogger(MenuAppConteroller.class);

    @Resource
    private MenuService menuService;
    @Resource
    private requestUTF requestUTF;

    /**
     * 获取用户菜单
     * @param request
     * @param response
     */
    @PostMapping("api/menuGetApp.act")
    public ResultBody menuGetApp(HttpServletRequest request, HttpServletResponse response,@RequestBody Person person){
        try{
            requestUTF.uTFonE(request,response);
            List<menu> menuType = new ArrayList<>();
            if(person==null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID,"登入超时");
            }else {
                menuType = menuService.selectTypeMenuForRoleApp(person.getIds());
            }
            return new ResultBody(ApiResultEnum.SUCCESS,menuType);
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

    /**
     * 获取用户下级菜单
     * @param request
     * @param response
     */
    @PostMapping("api/menuGetSmileApp.act")
    public ResultBody menuGetSmileApp(HttpServletRequest request, HttpServletResponse response,@RequestBody menu menu){
        try{
            requestUTF.uTFonE(request,response);
            List<menu> menuType = new ArrayList<>();
            if(menu == null){
                return new ResultBody(ApiResultEnum.TOKEN_INVALID,"登入超时");
            }else {
                menuType = menuService.selectTypeMenuForRoleSmileApp(menu.getIds(), menu.getPramId());
            }
            return new ResultBody(ApiResultEnum.SUCCESS,menuType);
        }catch (Exception e){
            log.error(e);
        }
        return new ResultBody(ApiResultEnum.ERR, null);
    }

}
