package com.example.mineBlos.myDrama.enity;

import com.example.util.enityUtil.userEnity;

public class DramaList extends userEnity {

    private String dramaName;
    private String dramaPath;
    private String dramaImage;
    private String productionYear;
    private String unPlay;
    private String rss;

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    public String getUnPlay() {
        return unPlay;
    }

    public void setUnPlay(String unPlay) {
        this.unPlay = unPlay;
    }

    public String getDramaName() {
        return dramaName;
    }

    public void setDramaName(String dramaName) {
        this.dramaName = dramaName;
    }

    public String getDramaPath() {
        return dramaPath;
    }

    public void setDramaPath(String dramaPath) {
        this.dramaPath = dramaPath;
    }

    public String getDramaImage() {
        return dramaImage;
    }

    public void setDramaImage(String dramaImage) {
        this.dramaImage = dramaImage;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }
}
