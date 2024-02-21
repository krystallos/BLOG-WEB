package com.example.fileConfig.enity.fiction;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FictionForMainVo {

    private List<FictionTag> tagName;
    private Map<Integer , List<FictionFile>> tagBookFiction;

}
