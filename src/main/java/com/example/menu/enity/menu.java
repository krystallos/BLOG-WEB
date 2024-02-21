package com.example.menu.enity;

import com.example.util.enityUtil.userEnity;

import java.util.List;

public class menu extends userEnity {

    private String tabName;
    private String sort;
    private String pramId;
    private String pramName;
    private String proType;
    private String menuUrl;
    private String menuName;
    private String icon;
    private String uniAppUrl;
    private String setApp;

    //独立的子菜单
    private List<menu> listMenu;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSetApp() {
        return setApp;
    }

    public void setSetApp(String setApp) {
        this.setApp = setApp;
    }

    public String getUniAppUrl() {
        return uniAppUrl;
    }

    public void setUniAppUrl(String uniAppUrl) {
        this.uniAppUrl = uniAppUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPramName() {
        return pramName;
    }

    public void setPramName(String pramName) {
        this.pramName = pramName;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPramId() {
        return pramId;
    }

    public void setPramId(String pramId) {
        this.pramId = pramId;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<menu> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<menu> listMenu) {
        this.listMenu = listMenu;
    }
}
