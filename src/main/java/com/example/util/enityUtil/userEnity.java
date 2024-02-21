package com.example.util.enityUtil;

import com.example.util.phoneIsMobile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 人员实体类，存储各种人员实体
 * @author Enoki
 * @version 1.0
 */
public class userEnity extends pageEnity{

    private String ids;
    private String userName;
    private String userPassWord;
    private String phone;
    private String email;
    private String createDate;
    private String createId;
    private String updateDate;
    private String state;
    private String delFlag;
    private String session;
    private String assessToken;

    public String getAssessToken() {
        return assessToken;
    }

    public void setAssessToken(String assessToken) {
        this.assessToken = assessToken;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getPhone() {
        return phone;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    /**
     * 电话
     * @param phone 电话
     * @param op 布尔，如何true那就加密电话，否则不加密
     */
    public void setPhone(String phone, Boolean op) {
        String phones = null;
        if(op){
            phoneIsMobile phoneIsMobile = new phoneIsMobile();
            phones = phoneIsMobile.startPhone(phone);
        }else{
            phones = phone;
        }
        this.phone = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = getNowDate(createDate);
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = getNowDate(updateDate);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    //----------------------------------------------
    /**
     * 用户实体类内功能型方法(更新时间/建立时间)
     * @author Enoki
     * @param str 数据(传空将会自动插值)
     */
    public String getNowDate(String str){
        String nowDate = null;
        if(str==null || str.equals("") || str.length()==0){
            nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            this.createDate = nowDate;
            this.updateDate = nowDate;
            return nowDate;
        }
        return str;
    }

    /**
     * 传入更新人和建立人
     * @param uuid
     */
    public void getUuidCreateUpdate(String uuid){
        if(uuid == null){
            createId = null;
        }else{
            createId = uuid;
        }
    }

}
