package com.example.fileConfig.enity.pixivEnity;

import java.util.List;

public class PixivBody {

    private Long illustId;
    private PixivTagFs tags;

    public Long getIllustId() {
        return illustId;
    }

    public void setIllustId(Long illustId) {
        this.illustId = illustId;
    }

    public PixivTagFs getTags() {
        return tags;
    }

    public void setTags(PixivTagFs tags) {
        this.tags = tags;
    }
}
