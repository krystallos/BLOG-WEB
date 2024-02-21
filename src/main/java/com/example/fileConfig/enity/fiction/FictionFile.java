package com.example.fileConfig.enity.fiction;

import com.example.util.enityUtil.userEnity;
import com.example.util.dic.DicVo;

import java.util.List;

public class FictionFile extends userEnity {

    private String originName;
    private String englishName;
    private String chineseName;
    private String pressName;
    private String authorName;
    private String illustrationAuth;
    private String illustrationUrl;
    private String libilName;
    private String chainBegin;
    private String chainEnd;
    private String tagId;
    private String tagName;

    private String storyMsg;

    private String fileType;
    private List<DicVo> allTag;
    private List<DicVo> tagVo;

    private String chineseNameFt;
    private String isEnd;

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getChineseNameFt() {
        return chineseNameFt;
    }

    public void setChineseNameFt(String chineseNameFt) {
        this.chineseNameFt = chineseNameFt;
    }

    public List<DicVo> getAllTag() {
        return allTag;
    }

    public void setAllTag(List<DicVo> allTag) {
        this.allTag = allTag;
    }

    public List<DicVo> getTagVo() {
        return tagVo;
    }

    public void setTagVo(List<DicVo> tagVo) {
        this.tagVo = tagVo;
    }

    public String getChainEnd() {
        return chainEnd;
    }

    public void setChainEnd(String chainEnd) {
        this.chainEnd = chainEnd;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getStoryMsg() {
        return storyMsg;
    }

    public void setStoryMsg(String storyMsg) {
        this.storyMsg = storyMsg;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getPressName() {
        return pressName;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIllustrationAuth() {
        return illustrationAuth;
    }

    public void setIllustrationAuth(String illustrationAuth) {
        this.illustrationAuth = illustrationAuth;
    }

    public String getIllustrationUrl() {
        return illustrationUrl;
    }

    public void setIllustrationUrl(String illustrationUrl) {
        this.illustrationUrl = illustrationUrl;
    }

    public String getLibilName() {
        return libilName;
    }

    public void setLibilName(String libilName) {
        this.libilName = libilName;
    }

    public String getChainBegin() {
        return chainBegin;
    }

    public void setChainBegin(String chainBegin) {
        this.chainBegin = chainBegin;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
