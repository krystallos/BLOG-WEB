package com.example.util;

public enum LogEnum {

    SELECT(1001, "查询"),
    DETIAL(1002, "详情"),
    INSERT(1003, "新增"),
    DELETE(1004, "删除"),
    EDIT(1005, "修改"),
    UPLOAD(1006, "上传"),
    DEFAULT(999, "无动作");

    public Integer code;
    public String message;

    private LogEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
