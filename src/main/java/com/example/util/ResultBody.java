package com.example.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBody implements Serializable {

    private String textMsg;
    private Object data;
    private Integer code;
    private String reslutMsg;
    private Integer total;

    public ResultBody(ApiResultEnum apiResultEnum){
        this.textMsg = apiResultEnum.getMessage();
        this.code = apiResultEnum.getCode();
        this.reslutMsg = apiResultEnum.getMessage();
    }
    public ResultBody(ApiResultEnum apiResultEnum, Object data){
        this.code = apiResultEnum.getCode();
        this.reslutMsg = apiResultEnum.getMessage();
        this.data = data;
    }
    public ResultBody(ApiResultEnum apiResultEnum, String textMsg){
        this.textMsg = textMsg;
        this.code = apiResultEnum.getCode();
        this.reslutMsg = apiResultEnum.getMessage();
    }
    public ResultBody(ApiResultEnum apiResultEnum, String textMsg, Object data){
        this.textMsg = textMsg;
        this.data = data;
        this.code = apiResultEnum.getCode();
        this.reslutMsg = apiResultEnum.getMessage();
    }

    public ResultBody(ApiResultEnum apiResultEnum, Object data, Integer total){
        this.total = total;
        this.data = data;
        this.code = apiResultEnum.getCode();
        this.reslutMsg = apiResultEnum.getMessage();
    }

    public ResultBody(String textMsg, Object data){
        this.textMsg = textMsg;
        this.data = data;
    }

    public ResultBody(String textMsg){
        this.textMsg = textMsg;
    }
}
