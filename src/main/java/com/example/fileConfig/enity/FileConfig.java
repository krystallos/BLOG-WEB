package com.example.fileConfig.enity;


import com.example.util.enityUtil.userEnity;
import lombok.Data;

import java.util.List;

/**
 * 文件实体类
 */
@Data
public class FileConfig extends userEnity {

    private String pathName;
    private String psnId;
    private String psnName;
    private String fileName;
    private String fileType;
    private String isHas;
    private String thumbnail;
    private String pathId;
    private String tagName;
    private List<String> tagNameList;
    private String authorId;
    private String authorName;

    //相似度处理
    private String likeSize;
    private String likeFloat;

    private String lonPathNameType;//组合路径
    private String readyType;

}
