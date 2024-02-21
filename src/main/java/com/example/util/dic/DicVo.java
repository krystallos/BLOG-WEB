package com.example.util.dic;

import lombok.Builder;
import lombok.Data;

/**
 * 字典类
 * @author Enokimushroom
 * @Date 2023-02-04
 */
@Builder
public class DicVo {

    private String dicName;
    private String dicValue;

    private String value;
    private String name;

    private String key;
    private String label;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
