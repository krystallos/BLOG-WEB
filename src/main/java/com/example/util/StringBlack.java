package com.example.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 判断类型是否为空
 * @author Enoki
 */
@SpringBootApplication
public class StringBlack {

    /**
     * 字符串判断是否非空
     * @param arc 字符串
     * @return true则为空数据
     */
    public static boolean isBlackString(String arc){
        if(arc == null){
            return true;
        }else if(arc.equals("null")){
            return true;
        }else if(arc.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 字符串判断是否非空
     * @param arc 字符串
     * @return false则为空数据
     */
    public static boolean isNotBlackString(String arc){
        if(arc == null){
            return false;
        }else if(arc.equals("null")){
            return false;
        }
        return true;
    }

    /**
     * 实体判断是否非空
     * @param obj 实体
     * @return true则为空数据
     */
    public static boolean isBlackObject(Object obj){
        if(obj == null){
            return true;
        }else if(obj instanceof List){
            if(((List)obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 实体判断是否非空
     * @param obj 实体
     * @return false则为空数据
     */
    public static boolean isNotBlackObject(Object obj){
        if(obj == null){
            return false;
        }else if(obj instanceof List){
            if(((List)obj).size() == 0) {
                return false;
            }
        }
        return true;
    }


}
