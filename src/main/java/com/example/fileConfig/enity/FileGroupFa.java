package com.example.fileConfig.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

import java.util.List;

@Data
public class FileGroupFa extends userEnity {

    private String filePathName;
    private String isHas;
    private List<FileGroupChil> fileGroupChilList;

}
