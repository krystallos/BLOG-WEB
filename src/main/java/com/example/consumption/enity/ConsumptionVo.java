package com.example.consumption.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class ConsumptionVo extends userEnity {

    //交易号
    private String transactionNum;
    //订单号
    private String order;
    //交易建立时间
    private String payTime;
    //交易月份
    private String payMonth;
    //交易来源地
    private String payFrom;
    //交易类型
    private String payType;
    //交易对方
    private String payPerson;
    //商品名称
    private String commodity;
    //金额（元）
    private String amount;
    //收/支
    private String incomeOrExpenditure;
    //交易状态
    private String progress;
    //服务费（元）
    private String service;
    //退款金额（元）
    private String refundAmount;
    //资金状态
    private String fundStatus;

    //资金排序
    private String moneyOrder;
    private String orderType;

}
