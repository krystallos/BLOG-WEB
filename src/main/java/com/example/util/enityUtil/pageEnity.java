package com.example.util.enityUtil;

import lombok.Data;

/**
 * 分页实体类，存储所有的分页信息，除此之外还需要额外继承查询页信息
 * @author Enoki
 * @version 1.0
 */
@Data
public class pageEnity {

    private int nowTab; //当前页
    private int allTab; //总页数
    private int hasTab; //分页数

    private int objSize; //数据长度

    private int startTab; //开始标签
    private int stopTab; //结束标签

    /**
     * 数据注入，封装分页方法,当传入nowTab的时候就启动分页机制，如果不传则不启动
     * @param nowTabs
     * @param hasTabs
     */
    public void pubImplPage(int nowTabs, int hasTabs){
        nowTab = nowTabs;
        hasTab = hasTabs;
        pubAllTab();
        pubStartStopTab();
    }

    /**
     * 求总页数
     * objSize 数据长度
     * hasTab 分页单页数量
     * allTab 总多少页
     */
    public void pubAllTab(){
        if(objSize % hasTab != 0){
            allTab = objSize / hasTab + 1;
        }else{
            allTab = objSize / hasTab;
        }
    }

    /**
     * 求起始页/结束页
     * nowTab 当前页角标
     * hasTab 分页单页数量
     * stopTab 结束页角标
     */
    public void pubStartStopTab(){
        if(nowTab != 0 && hasTab != 0){
            startTab = nowTab * hasTab - hasTab;
            stopTab = nowTab * hasTab  - 1;
        }
    }

    /**
     * 求总页数(带返回参)
     * objSize 数据长度
     * hasTab 分页单页数量
     * allTab 总多少页
     */
    public static int pubAllTabReturn(int objSize, int hasTab){
        if(objSize % hasTab != 0){
            return objSize / hasTab + 1;
        }
        return objSize / hasTab;
    }

}
