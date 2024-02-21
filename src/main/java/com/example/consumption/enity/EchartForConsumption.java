package com.example.consumption.enity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class EchartForConsumption {

    private String[] xAxisData;
    private String[] data1;
    private String[] data2;

    private String beginTime = new SimpleDateFormat("yyyy").format(new Date());
    //1是月份，2是年份
    private String timeType = "1";

    private String createId;
    private String apiType;

    private Integer month;
    private Integer day;
    private String y;

    //资金排序
    private int moneyOrder;
    private String orderType;

}
