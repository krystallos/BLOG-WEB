package com.example.util.dic;

import lombok.Builder;

/**
 * 字典类
 * @author Enokimushroom
 * @Date 2023-02-04
 */
@Builder
public class FictionDicVo {

    private String dicName;
    private String dicValue;

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

}
