package com.example.fileConfig.enity.sauce;

import java.util.List;

public class SauceNaoSendMsg {

    private List<String> ext_urls;

    /*------------pixiv----------*/

    private String title;

    private String pixiv_id;

    private String member_name;

    private String member_id;

    /*------------danbooru----------*/

    private String danbooru_id;

    /*--------------yandere-----------*/

    private String yandere_id;

    /*--------------通用-------------*/

    private String creator;

    private String material;

    public String getDanbooru_id() {
        return danbooru_id;
    }

    public void setDanbooru_id(String danbooru_id) {
        this.danbooru_id = danbooru_id;
    }

    public String getYandere_id() {
        return yandere_id;
    }

    public void setYandere_id(String yandere_id) {
        this.yandere_id = yandere_id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<String> getExt_urls() {
        return ext_urls;
    }

    public void setExt_urls(List<String> ext_urls) {
        this.ext_urls = ext_urls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPixiv_id() {
        return pixiv_id;
    }

    public void setPixiv_id(String pixiv_id) {
        this.pixiv_id = pixiv_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
