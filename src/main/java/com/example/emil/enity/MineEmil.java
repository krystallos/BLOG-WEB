package com.example.emil.enity;

import com.example.util.enityUtil.userEnity;

/**
 * 邮件实体
 * @author Enoki
 */
public class MineEmil extends userEnity {

    private String fromPersonId;
    private String fromPersonEmil;
    private String fromPersonName;
    private String intoPersonId;
    private String intoPersonEmil;
    private String intoPersonName;
    private String title;
    private String content;
    private String isLike;
    private String isLook;//0为未读状态，1为已读状态，2为草稿箱

    /*工具*/
    private String isAll;//是否开启全局查询1open.0close
    private int type;//设置邮件状态值

    public String getFromPersonId() {
        return fromPersonId;
    }

    public void setFromPersonId(String fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public String getFromPersonName() {
        return fromPersonName;
    }

    public void setFromPersonName(String fromPersonName) {
        this.fromPersonName = fromPersonName;
    }

    public String getIntoPersonId() {
        return intoPersonId;
    }

    public void setIntoPersonId(String intoPersonId) {
        this.intoPersonId = intoPersonId;
    }

    public String getIntoPersonName() {
        return intoPersonName;
    }

    public void setIntoPersonName(String intoPersonName) {
        this.intoPersonName = intoPersonName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsLook() {
        return isLook;
    }

    public void setIsLook(String isLook) {
        this.isLook = isLook;
    }

    public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFromPersonEmil() {
        return fromPersonEmil;
    }

    public void setFromPersonEmil(String fromPersonEmil) {
        this.fromPersonEmil = fromPersonEmil;
    }

    public String getIntoPersonEmil() {
        return intoPersonEmil;
    }

    public void setIntoPersonEmil(String intoPersonEmil) {
        this.intoPersonEmil = intoPersonEmil;
    }
}
