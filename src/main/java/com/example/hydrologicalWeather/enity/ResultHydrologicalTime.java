package com.example.hydrologicalWeather.enity;

public class ResultHydrologicalTime {

    private Integer code;
    private HydrologicalTime data;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HydrologicalTime getData() {
        return data;
    }

    public void setData(HydrologicalTime data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
