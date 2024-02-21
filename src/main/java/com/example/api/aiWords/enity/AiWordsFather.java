package com.example.api.aiWords.enity;

import java.util.List;

public class AiWordsFather{

    private String ids;
    private String dicFatherName;
    private String chiName;
    private String dicType;
    private String tabType;
    private List<AiWordsChild> aiWordsChildList;

    private Integer tabSort;
    private Integer chiSort;

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public String getDicFatherName() {
        return dicFatherName;
    }

    public void setDicFatherName(String dicFatherName) {
        this.dicFatherName = dicFatherName;
    }

    public String getChiName() {
        return chiName;
    }

    public void setChiName(String chiName) {
        this.chiName = chiName;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<AiWordsChild> getAiWordsChildList() {
        return aiWordsChildList;
    }

    public void setAiWordsChildList(List<AiWordsChild> aiWordsChildList) {
        this.aiWordsChildList = aiWordsChildList;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public Integer getTabSort() {
        return tabSort;
    }

    public void setTabSort(Integer tabSort) {
        this.tabSort = tabSort;
    }

    public Integer getChiSort() {
        return chiSort;
    }

    public void setChiSort(Integer chiSort) {
        this.chiSort = chiSort;
    }
}
