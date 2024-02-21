package com.example.mineBlos.myBlosTab.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class MineBlos extends userEnity {

    private String ids;
    private String actId;
    private String actName;
    private String content;
    private String clobTitle;
    private String onState;
    private String psnId;
    private String psnName;
    private String actType;
    private String liteCont;
    private String centImg;

    //工具
    private String type;
    private String url;

}
