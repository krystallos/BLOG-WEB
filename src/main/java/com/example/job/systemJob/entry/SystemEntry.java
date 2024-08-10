package com.example.job.systemJob.entry;

import com.example.util.enityUtil.userEnity;

public class SystemEntry extends userEnity {

    private String systemId;
    private Integer cpuCode;
    private String cpuSystemUse;
    private String cpuUserUse;
    private String cpuLeisure;
    private String cpuUtilization;
    private String allMemory;
    private String utilizationMemory;
    private String useMemory;
    private String memoryUse;
    private String memoryUtilization;
    private String memoryMax;
    private String jvmP;
    private String memoryP;

    private String createDate;

    public String getJvmP() {
        return jvmP;
    }

    public void setJvmP(String jvmP) {
        this.jvmP = jvmP;
    }

    public String getMemoryP() {
        return memoryP;
    }

    public void setMemoryP(String memoryP) {
        this.memoryP = memoryP;
    }

    public String getMemoryUse() {
        return memoryUse;
    }

    public void setMemoryUse(String memoryUse) {
        this.memoryUse = memoryUse;
    }

    public String getMemoryUtilization() {
        return memoryUtilization;
    }

    public void setMemoryUtilization(String memoryUtilization) {
        this.memoryUtilization = memoryUtilization;
    }

    public String getMemoryMax() {
        return memoryMax;
    }

    public void setMemoryMax(String memoryMax) {
        this.memoryMax = memoryMax;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getCpuCode() {
        return cpuCode;
    }

    public void setCpuCode(Integer cpuCode) {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
