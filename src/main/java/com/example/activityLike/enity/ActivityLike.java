package com.example.activityLike.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

/**
 * 收藏实体
 * @author Enoki
 */
@Data
public class ActivityLike extends userEnity {

    private String mineActivityId;
    private String psnId;
    private String likeMainId;


}
