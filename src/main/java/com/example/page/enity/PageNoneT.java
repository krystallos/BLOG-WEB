package com.example.page.enity;

import lombok.Data;

import java.util.List;

/**
 * 作为通用方法，可以支持多实体传入
 */
@Data
public class PageNoneT<T>{

    private String noneT;
    private List<T> noneListT;
    private Object noneObj;

}
