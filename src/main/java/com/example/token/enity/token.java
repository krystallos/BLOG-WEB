package com.example.token.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class token extends userEnity {

    private String tokenId;
    private String psnId;
    private String areaId;

}
