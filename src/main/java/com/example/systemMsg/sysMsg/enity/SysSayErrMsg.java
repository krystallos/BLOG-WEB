package com.example.systemMsg.sysMsg.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class SysSayErrMsg extends userEnity {

    private String errId;
    private String sayPsnId;
    private String sayPsnName;
    private String sayCenter;

}
