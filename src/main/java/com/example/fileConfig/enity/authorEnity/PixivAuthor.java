package com.example.fileConfig.enity.authorEnity;

public class PixivAuthor {

    private String ids;
    private Boolean error;
    private String message;
    private PixivAuthBody body;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

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

    public PixivAuthBody getBody() {
        return body;
    }

    public void setBody(PixivAuthBody body) {
        this.body = body;
    }
}
