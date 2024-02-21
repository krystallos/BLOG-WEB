package com.example.fileConfig.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

/**
 * 文件JSON类
 */
@Data
public class FileUtil extends userEnity {

    private String psnId;
    private String filePath;//子文件夹
    private String isHas;//文件路径
    private String isValid;//是否参与更新

    private String fileName;//文件名称，非数据库字段
    private String authorName;//作者
    private String index;//页面ID
    private String pathName;//保存路径

}
