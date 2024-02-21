package com.example.systemMsg.sysVersions.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

/**
 * 系统更新
 * @author Enoki
 */
@Data
public class SysVersions extends userEnity {

    private String verName;
    private String verType;
    private String verCreate;
    private String verVersion;

    private String sysType;
    private String appUrl;
    private String isMustUpdate;

}
