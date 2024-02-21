package com.example.fileConfig.enity.pixivEnity;

public class PixivCrawler {

    private Boolean error;
    private String message;
    private PixivBody body;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PixivBody getBody() {
        return body;
    }

    public void setBody(PixivBody body) {
        this.body = body;
    }
}
