package com.example.fileConfig.enity;

import com.example.util.enityUtil.userEnity;

public class FileSelectPixiv extends userEnity {

    private String ids;
    private String imgUrl;
    private String author;
    private String authorUrl;
    private String picUrl;
    private String pic;
    private String orderTime;

    private String localHost;
    private String likeImg;

    private String blosImgUrl;
    private String blosPic;
    private String blosPicUrl;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLikeImg() {
        return likeImg;
    }

    public void setLikeImg(String likeImg) {
        this.likeImg = likeImg;
    }

    public String getBlosImgUrl() {
        return blosImgUrl;
    }

    public void setBlosImgUrl(String blosImgUrl) {
        this.blosImgUrl = blosImgUrl;
    }

    public String getBlosPic() {
        return blosPic;
    }

    public void setBlosPic(String blosPic) {
        this.blosPic = blosPic;
    }

    public String getBlosPicUrl() {
        return blosPicUrl;
    }

    public void setBlosPicUrl(String blosPicUrl) {
        this.blosPicUrl = blosPicUrl;
    }
}
