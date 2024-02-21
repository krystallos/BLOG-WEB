package com.example.job.systemJob.entry;

import com.example.util.enityUtil.userEnity;

public class SystemEntry extends userEnity {

    private Long timeMillis;
    private String systemNum;
    private String cpuCode;
    private String cpuSystemUse;
    private String cpuUserUse;
    private String cpuLeisure;
    private String cpuUtilization;
    private String allMemory;
    private String utilizationMemory;
    private String useMemory;
    private String createDate;

    public Long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(String systemNum) {
        this.systemNum = systemNum;
    }

    public String getCpuCode() {
        return cpuCode;
    }

    public void setCpuCode(String cpuCode) {
        this.cpuCode = cpuCode;
    }

    public String getCpuSystemUse() {
        return cpuSystemUse;
    }

    public void setCpuSystemUse(String cpuSystemUse) {
        this.cpuSystemUse = cpuSystemUse;
    }

    public String getCpuUserUse() {
        return cpuUserUse;
    }

    public void setCpuUserUse(String cpuUserUse) {
        this.cpuUserUse = cpuUserUse;
    }

    public String getCpuLeisure() {
        return cpuLeisure;
    }

    public void setCpuLeisure(String cpuLeisure) {
        this.cpuLeisure = cpuLeisure;
    }

    public String getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(String cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public String getAllMemory() {
        return allMemory;
    }

    public void setAllMemory(String allMemory) {
        this.allMemory = allMemory;
    }

    public String getUtilizationMemory() {
        return utilizationMemory;
    }

    public void setUtilizationMemory(String utilizationMemory) {
        this.utilizationMemory = utilizationMemory;
    }

    public String getUseMemory() {
        return useMemory;
    }

    public void setUseMemory(String useMemory) {
        this.useMemory = useMemory;
    }

    @Override
    public String getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
