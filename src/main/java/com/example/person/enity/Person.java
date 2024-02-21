package com.example.person.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class Person extends userEnity {

    private String userId;
    private String psnState;
    private String psnLikeId;
    private String psnLikeName;
    private String psnName;

}
