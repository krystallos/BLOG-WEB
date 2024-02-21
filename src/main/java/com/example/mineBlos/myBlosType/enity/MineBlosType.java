package com.example.mineBlos.myBlosType.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

/**
 * 博客类型分类
 * @author Enoki
 */
@Data
public class MineBlosType extends userEnity {

    private String ids;
    private String psnId;
    private String blosTypeName;
    private String blosTypeImg;
    private String isValid;

}
