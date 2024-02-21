package com.example.util.dic;

public enum ApiResultImgEnum {

    /**
     * 图像识别网站枚举
     */
    HMAGS(0,"h-mags"),
    PIXIV(5,"pixiv"),
    ANIME(7,"anime"),
    DANBOORU(9,"danbooru"),
    NIJIE(11,"nijie"),
    YANDERE(12,"yande.re");

    public int code;

    public String message;

    ApiResultImgEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
