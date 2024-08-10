package com.example.job.systemJob.entry;

public class SystemConfigEnerty {

    private String id;
    private String systemName;
    private String cpuName;
    private String ip;
    private String startTime;
    private long runtime;
    private String cpuCode;
    private String systemNum;
    private Long dataHistorical;
    private Long gatewayHistorical;
    private Long isSave;
    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public Long getIsSave() {
        return isSave;
    }

    public void setIsSave(Long isSave) {
        this.isSave = isSave;
    }

    public Long getDataHistorical() {
        return dataHistorical;
    }

    public void setDataHistorical(Long dataHistorical) {
        this.dataHistorical = dataHistorical;
    }

    public Long getGatewayHistorical() {
        return gatewayHistorical;
    }

    public void setGatewayHistorical(Long gatewayHistorical) {
        this.gatewayHistorical = gatewayHistorical;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }
}
