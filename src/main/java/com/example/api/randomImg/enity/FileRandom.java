package com.example.api.randomImg.enity;


import com.example.util.enityUtil.userEnity;
import lombok.Data;

import java.util.List;

/**
 * 随机图片实体类
 */
@Data
public class FileRandom extends userEnity {

    private int id;
    private String pathName;
    private String isOpen;
    private String fileName;

    private String authorId;
    private String authorName;

    private int type;
    private String msg;
    private String thumbnail;
    private int countImg;
    private int avgScore;
    private String tempPath;

    private String tagName;
    private List<String> tagNameList;

}
