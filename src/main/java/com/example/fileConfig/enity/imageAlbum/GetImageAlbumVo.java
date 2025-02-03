package com.example.fileConfig.enity.imageAlbum;

import com.example.util.enityUtil.userEnity;

public class GetImageAlbumVo extends userEnity {

    private String imageName;
    private String imagePath;
    private String imageTemp;
    private String sessionYear;
    private String sessionMonth;
    private String sessionPiovince;
    private String sessionRegion;
    private String isLike;
    private String isLikeName;
    private String fileType;

    public String getSessionMonth() {
        return sessionMonth;
    }

    public void setSessionMonth(String sessionMonth) {
        this.sessionMonth = sessionMonth;
    }

    public String getIsLikeName() {
        return isLikeName;
    }

    public void setIsLikeName(String isLikeName) {
        this.isLikeName = isLikeName;
    }

    public String getImageTemp() {
        return imageTemp;
    }

    public void setImageTemp(String imageTemp) {
        this.imageTemp = imageTemp;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSessionYear() {
        return sessionYear;
    }

    public void setSessionYear(String sessionYear) {
        this.sessionYear = sessionYear;
    }

    public String getSessionPiovince() {
        return sessionPiovince;
    }

    public void setSessionPiovince(String sessionPiovince) {
        this.sessionPiovince = sessionPiovince;
    }

    public String getSessionRegion() {
        return sessionRegion;
    }

    public void setSessionRegion(String sessionRegion) {
        this.sessionRegion = sessionRegion;
    }
}
