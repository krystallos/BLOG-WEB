package com.example.fileConfig.enity.sauce;

public class SauceData {

    private SauceHeader header;
    private SauceNaoSendMsg data;

    public SauceNaoSendMsg getData() {
        return data;
    }

    public SauceHeader getHeader() {
        return header;
    }

    public void setHeader(SauceHeader header) {
        this.header = header;
    }

    public void setData(SauceNaoSendMsg data) {
        this.data = data;
    }
}
