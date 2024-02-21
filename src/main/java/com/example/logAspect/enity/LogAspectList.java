package com.example.logAspect.enity;

import com.alibaba.fastjson.JSON;
import com.example.util.LogResultBody;
import com.example.util.enityUtil.userEnity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogAspectList extends userEnity{

    private String apiName;
    private String urlIze;
    private String httpMethod;
    private String classMethod;
    private String ip;
    private String result;
    private String title;
    private String type;
    private String createDate;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlIze() {
        return urlIze;
    }

    public void setUrlIze(String urlIze) {
        this.urlIze = urlIze;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
