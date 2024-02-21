package com.example.fileConfig.enity.pixivEnity;

import java.util.List;

public class PixivTagFs {

    private Long authorId;
    private Boolean isLocked;
    private List<PixivTags> tags;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public List<PixivTags> getTags() {
        return tags;
    }

    public void setTags(List<PixivTags> tags) {
        this.tags = tags;
    }
}
