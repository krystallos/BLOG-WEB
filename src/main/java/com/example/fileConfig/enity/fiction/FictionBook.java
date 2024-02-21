package com.example.fileConfig.enity.fiction;

import com.example.util.enityUtil.userEnity;

public class FictionBook extends userEnity {

    private String fictionId;
    private String fictionOriginName;
    private String fictionBookName;
    private String bookSort;

    public String getFictionId() {
        return fictionId;
    }

    public void setFictionId(String fictionId) {
        this.fictionId = fictionId;
    }

    public String getFictionOriginName() {
        return fictionOriginName;
    }

    public void setFictionOriginName(String fictionOriginName) {
        this.fictionOriginName = fictionOriginName;
    }

    public String getFictionBookName() {
        return fictionBookName;
    }

    public void setFictionBookName(String fictionBookName) {
        this.fictionBookName = fictionBookName;
    }

    public String getBookSort() {
        return bookSort;
    }

    public void setBookSort(String bookSort) {
        this.bookSort = bookSort;
    }
}
