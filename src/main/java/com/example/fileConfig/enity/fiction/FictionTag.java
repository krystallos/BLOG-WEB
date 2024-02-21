package com.example.fileConfig.enity.fiction;

import com.example.util.enityUtil.userEnity;

public class FictionTag extends userEnity {

    private String tagName;
    private String tagRemark;

    private String tagType;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagRemark() {
        return tagRemark;
    }

    public void setTagRemark(String tagRemark) {
        this.tagRemark = tagRemark;
    }
}
