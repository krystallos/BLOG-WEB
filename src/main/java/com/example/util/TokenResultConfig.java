package com.example.util;

import com.alibaba.fastjson.JSON;
import com.example.token.web.TokenSession;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenResultConfig {

    @Resource
    private TokenSession tokenSession;

    public int appException(HttpServletResponse response,String sessionId, String token){
        try{
            ResultBody result = tokenSession.tokenAssDemo(sessionId, token);
            //非成功和token刷新
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            if(result.getCode() != 200 && result.getCode() != 501){
                response.getWriter().print(JSON.toJSONString(result));
            }else {
                response.setHeader("assessToken", (String) result.getData());
            }
            return result.getCode();
        }catch (IOException e){
            log.error(e.getMessage());
            return 500;
        }
    }

}
