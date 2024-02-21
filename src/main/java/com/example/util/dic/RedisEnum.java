package com.example.util.dic;

public enum RedisEnum {

    GETBACK_PASSWORD("getBackPassWord:");

    public String message;

    private RedisEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
