package com.example.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号验证封装
 * @author Enoki
 */
@SpringBootApplication
public class phoneIsMobile {

    /**
     * 手机号验证
     * @param  str 手机
     * @return 验证通过返回true
     */
    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     * @param  str 电话
     * @return 验证通过返回true
     */
    public boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 电话号码中间4位隐藏
     * @param str 电话
     * @return 带星星后的电话
     */
    public String startPhone(String str){
        if(str!=null) {
            str = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return str;
    }

}
