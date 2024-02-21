package com.example.api.aiWords.enity;

import com.example.util.enityUtil.userEnity;

public class AiWordsPage extends userEnity {

    private String ids;
    private String dicCheName;
    private String chiName;
    private String fatherId;
    private String fatherCheName;
    private String fatherName;
    private String dicType;
    private String tabType;
    private String tabSort;
    private String chiSort;

    private int addType;//添加逻辑

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDicCheName() {
        return dicCheName;
    }

    public void setDicCheName(String dicCheName) {
        this.dicCheName = dicCheName;
    }

    public String getChiName() {
        return chiName;
    }

    public void setChiName(String chiName) {
        this.chiName = chiName;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getFatherCheName() {
        return fatherCheName;
    }

    public void setFatherCheName(String fatherCheName) {
        this.fatherCheName = fatherCheName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public String getTabSort() {
        return tabSort;
    }

    public void setTabSort(String tabSort) {
        this.tabSort = tabSort;
    }

    public String getChiSort() {
        return chiSort;
    }

    public void setChiSort(String chiSort) {
        this.chiSort = chiSort;
    }

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
    }
}
