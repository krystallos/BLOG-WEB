package com.example.postApi.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class PostApiEnity extends userEnity {

    private String ids;
    private String postName;
    private String remark;

}
