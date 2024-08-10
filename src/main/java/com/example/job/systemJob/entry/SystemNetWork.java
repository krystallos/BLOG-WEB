package com.example.job.systemJob.entry;

import com.example.util.enityUtil.userEnity;

public class SystemNetWork extends userEnity {

    private String systemId;

    /**
     * 上行速度
     */
    private String txPercent ;
    /**
     * 下行速度
     */
    private String rxPercent ;



    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTxPercent() {
        return txPercent;
    }

    public void setTxPercent(String txPercent) {
        this.txPercent = txPercent;
    }

    public String getRxPercent() {
        return rxPercent;
    }

    public void setRxPercent(String rxPercent) {
        this.rxPercent = rxPercent;
    }
}
