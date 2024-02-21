package com.example.fileConfig.enity.fiction;

import com.example.util.enityUtil.userEnity;

public class FictionSelect extends userEnity {

    private String chineseName;
    private String isEnd; //0/null全部 1已完结 2未完结

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }
}
