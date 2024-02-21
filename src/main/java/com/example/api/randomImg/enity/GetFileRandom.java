package com.example.api.randomImg.enity;


import lombok.Data;

/**
 * 文件实体类
 */
@Data
public class GetFileRandom {

    private String pathName;
    private String fileName;
    private String fileType;
    private Integer isHas;
    private String pathId;
    private String authorId;
    private String tagName;
    private String createDate;

}
