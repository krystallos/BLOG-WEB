package com.example.fileConfig.enity.fiction;

import lombok.Data;

import java.util.List;

@Data
public class FictionForDetilVo {

    private String detialId;

    private List<FictionBook> book;
    private FictionFile hasDetil;

}
