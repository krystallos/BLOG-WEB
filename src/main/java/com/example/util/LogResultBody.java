package com.example.util;

public class LogResultBody {

    private Integer code;
    private String message;
    private Integer total;

    public LogResultBody(Integer code, String message, Integer total){
        this.code = code;
        this.message = message;
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
