package com.example.consumption.enity;

import lombok.Data;

@Data
public class CountConsumptionVo {

    private String yearIn = "0.00";
    private String yearOut = "0.00";
    private String yearMax = "0.00";
    private String monthIn = "0.00";
    private String monthOut = "0.00";
    private String monthMax = "0.00";

    private String year;
    private String month;
    private String creatId;

}
