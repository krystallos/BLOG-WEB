package com.example.systemMsg.sysMsg.enity;

import lombok.Data;

/**
 * 系统通知/服务类
 * @author Enoki
 */
@Data
public class SysMsg {

    private String ids;//主键
    private String name;//名字
    private String isAdmin;//是否管理员
    private String isFile;//文件管理
    private String hasFile;//文件管理数量
    private String hasBook;//小说数量
    private String isEmail;//是否未读邮件
    private String hasEmail;//邮件数量
    private String isblos;//是否有博客
    private String hasBlos;//博客数量
    private String isService;//是否有社区
    private String hasService;//社区数量
    private String blosUrl;//博客地址

}
