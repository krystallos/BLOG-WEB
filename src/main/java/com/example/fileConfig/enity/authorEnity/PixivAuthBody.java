package com.example.fileConfig.enity.authorEnity;

import com.example.fileConfig.enity.pixivEnity.PixivTagFs;

public class PixivAuthBody {

    private String name;
    private String userId;
    private PixivTagFs tags;
    private String image;
    private PixivAuthRegion region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PixivTagFs getTags() {
        return tags;
    }

    public void setTags(PixivTagFs tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PixivAuthRegion getRegion() {
        return region;
    }

    public void setRegion(PixivAuthRegion region) {
        this.region = region;
    }
}
