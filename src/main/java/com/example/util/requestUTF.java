package com.example.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 字符集编码工具
 * @author Enoki
 */
@SpringBootApplication
public class requestUTF {

    /**
     * 字符转换UTF8标准
     * @param request
     * @param response
     * @throws IOException
     */
    public void uTFonE(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * 字符转换GBK16标准
     * @param request
     * @param response
     * @throws IOException
     */
    public void gbKonE(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("GBK16");
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * 字符转换UTF-16标准
     * @param request
     * @param response
     * @throws IOException
     */
    public void utfonEOS(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-16");
        response.setContentType("text/html;charset=UTF-8");
    }

}
