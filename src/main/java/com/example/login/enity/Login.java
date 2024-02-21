package com.example.login.enity;

import com.example.util.enityUtil.userEnity;

public class Login extends userEnity {

    private String logname;
    private String logpass;

    private String areaId;
    private String orgSessionId;
    private String delFlag;

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getLogpass() {
        return logpass;
    }

    public void setLogpass(String logpass) {
        this.logpass = logpass;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getOrgSessionId() {
        return orgSessionId;
    }

    public void setOrgSessionId(String orgSessionId) {
        this.orgSessionId = orgSessionId;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
