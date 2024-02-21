package com.example.likeMenu.enity;


import com.example.util.enityUtil.userEnity;

import java.util.List;

/**
 * 社区实体页
 */
public class LikeMenu extends userEnity {

    private String likeName;
    private String likePid;
    private String likeSort;
    private String likeRemaker;
    private String isDefault;

    private List<LikeMenu> data;

    public List<LikeMenu> getData() {
        return data;
    }

    public void setData(List<LikeMenu> data) {
        this.data = data;
    }

    public String getLikeName() {
        return likeName;
    }

    public void setLikeName(String likeName) {
        this.likeName = likeName;
    }

    public String getLikePid() {
        return likePid;
    }

    public void setLikePid(String likePid) {
        this.likePid = likePid;
    }

    public String getLikeSort() {
        return likeSort;
    }

    public void setLikeSort(String likeSort) {
        this.likeSort = likeSort;
    }

    public String getLikeRemaker() {
        return likeRemaker;
    }

    public void setLikeRemaker(String likeRemaker) {
        this.likeRemaker = likeRemaker;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
