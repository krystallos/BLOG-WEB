package com.example.systemMsg.sysMsg.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class SysErrMsg extends userEnity {

    private String errTitle;
    private String errCenter;
    private String errPsnId;
    private String errPsnName;
    private String adminBack;
    private String adminCenter;
    private String isOk;
    private String isType;

    private String[] errId;

}
