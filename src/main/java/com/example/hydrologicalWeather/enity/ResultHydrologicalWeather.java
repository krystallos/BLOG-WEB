package com.example.hydrologicalWeather.enity;

public class ResultHydrologicalWeather {

    private Integer code;
    private HydrologicalWeather data;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HydrologicalWeather getData() {
        return data;
    }

    public void setData(HydrologicalWeather data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
