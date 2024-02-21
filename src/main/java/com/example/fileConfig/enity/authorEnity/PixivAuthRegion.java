package com.example.fileConfig.enity.authorEnity;

public class PixivAuthRegion {

    private String name;
    private String regionName = "未知地区";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}
