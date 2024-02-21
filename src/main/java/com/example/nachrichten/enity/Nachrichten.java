package com.example.nachrichten.enity;

import com.example.util.enityUtil.userEnity;
import lombok.Data;

@Data
public class Nachrichten extends userEnity {

    private String imageUrl;
    private String message;
    private String gotoUrl;
    private String sysType;

}
