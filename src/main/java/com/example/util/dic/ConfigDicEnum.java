package com.example.util.dic;

/**
 * 此配置需要与字典表SYS_DIC_CONFIG同步
 */
public enum ConfigDicEnum {

    accessFile("config:accessFile"),
    accessUploadFile("config:accessUploadFile"),
    fictionImg("config:fictionImg"),
    fictionFile("config:fictionFile"),
    assessImgFile("config:assessImgFile"),
    assessBlosImg("config:assessBlosImg"),
    dramaImage("config:dramaImage"),
    assessUrlBlos("config:assessUrlBlos"),
    assFileCode("config:assFileCode"),
    tempFile("config:tempFile"),
    asciiHttps("config:asciiHttps"),
    saucenaoHttps("config:saucenaoHttps"),
    saucenaoKey("config:saucenaoKey"),
    aliyunDNS("config:aliyunDNS"),
    aliyunDomain("config:aliyunDomain"),
    aliyunAccessKeyId("config:aliyunAccessKeyId"),
    aliyunAccessKeySecret("config:aliyunAccessKeySecret");

    public String message;

    private ConfigDicEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
