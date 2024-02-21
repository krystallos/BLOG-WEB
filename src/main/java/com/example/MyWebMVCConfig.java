package com.example;

import com.example.util.MyInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件回馈
 * @author Enoki
 */
@Slf4j
@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {

    @Value("${accessFile}")
    private String loca;
    @Value("${assessImgFile}")
    private String assessImgFile;
    @Value("${assessBlosImg}")
    private String assessBlosImg;
    @Value("${assFileCode}")
    private String assFileCode;
    @Value("${tempFile}")
    private String tempFile;
    @Value("${fictionImg}")
    private String fictionImg;
    @Value("${fictionFile}")
    private String fictionFile;

    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /**
         * 将本机地址转换为服务器地址
         */
        registry.addResourceHandler("/img/**").addResourceLocations("file:///" + loca);
        registry.addResourceHandler("/imgItem/**").addResourceLocations("file:///" + assessImgFile);
        registry.addResourceHandler("/imgBlos/**").addResourceLocations("file:///" + assessBlosImg);
        registry.addResourceHandler("/assFileCode/**").addResourceLocations("file:///" + assFileCode);
        registry.addResourceHandler("/tempFile/**").addResourceLocations("file:///" + tempFile);
        registry.addResourceHandler("/fictionImg/**").addResourceLocations("file:///" + fictionImg);
        registry.addResourceHandler("/fictionFile/**").addResourceLocations("file:///" + fictionFile);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptor(getMyInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/open/**")
            .excludePathPatterns("/img/**")
            .excludePathPatterns("/imgItem/**")
            .excludePathPatterns("/imgBlos/**")
            .excludePathPatterns("/assFileCode/**")
            .excludePathPatterns("/tempFile/**")
            .excludePathPatterns("/fictionImg/**")
            .excludePathPatterns("/fictionFile/**");
        log.info("系统拦截器初始化完成");

    }



}
