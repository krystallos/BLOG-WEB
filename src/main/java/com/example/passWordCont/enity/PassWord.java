package com.example.passWordCont.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

/**
 * 密码本实体
 * @author Enoki
 */
@Data
public class PassWord extends userEnity {

    private String ids;
    private String passWord;//密码
    private String passToken;//校验
    private String https;//网站
    private String remark;//备注
    private String onState;//有效性
    private String psnId;//用户
    private String accessName;//账号

    private String froms;//类型

}
