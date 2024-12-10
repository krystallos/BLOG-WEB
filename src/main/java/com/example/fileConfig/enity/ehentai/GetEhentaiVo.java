package com.example.fileConfig.enity.ehentai;

import com.example.util.enityUtil.userEnity;

public class GetEhentaiVo extends userEnity {

    private String bookName;
    private String bookAuthor;
    private String bookSaveTime;
    private String bookImage;
    private String bookTimeStamp;

    public String getBookTimeStamp() {
        return bookTimeStamp;
    }

    public void setBookTimeStamp(String bookTimeStamp) {
        this.bookTimeStamp = bookTimeStamp;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookSaveTime() {
        return bookSaveTime;
    }

    public void setBookSaveTime(String bookSaveTime) {
        this.bookSaveTime = bookSaveTime;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
