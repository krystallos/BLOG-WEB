package com.example.codeFile.enity;

import com.example.util.enityUtil.userEnity;

/**
 * 二维码实体
 * @author Enoki
 */
public class CodeFileEnity extends userEnity {

    private String codeMind;
    private String codeType;
    private String codeMd5;
    private String path;
    private String itemPath;
    private String passWord;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodeMind() {
        return codeMind;
    }

    public void setCodeMind(String codeMind) {
        this.codeMind = codeMind;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeMd5() {
        return codeMd5;
    }

    public void setCodeMd5(String codeMd5) {
        this.codeMd5 = codeMd5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getItemPath() {
        return itemPath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
