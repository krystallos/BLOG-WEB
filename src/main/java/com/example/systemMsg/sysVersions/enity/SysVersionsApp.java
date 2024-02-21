package com.example.systemMsg.sysVersions.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class SysVersionsApp extends userEnity {

    private String versionsId;
    private String versionsCode;
    private String isMustUpdate;
    private String appUrl;
    private String versionsType;

}
