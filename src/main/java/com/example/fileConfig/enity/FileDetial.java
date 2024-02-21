package com.example.fileConfig.enity;


import com.example.util.enityUtil.userEnity;
import lombok.Data;

import java.util.List;

/**
 * 文件实体类
 */
@Data
public class FileDetial {

    private String pathName;
    private String psnName;
    private String fileName;
    private String fileType;
    private String isHas;
    private String isSync;
    private String pathId;
    private List<String> tagNameList;
    private String authorName;
    private String pixivId;
    private String headImage;
    private String region;
    private String createDate;

}
