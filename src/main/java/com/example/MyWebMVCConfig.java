package com.example;

import com.example.util.MyInterceptor;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 文件回馈
 * @author Enoki
 */
@Slf4j
@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {

    @Resource
    private RedisUtils redisUtils;

    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        /**
         * 将本机地址转换为服务器地址
         */
        registry.addResourceHandler("/img/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.accessFile.message));
        registry.addResourceHandler("/imgItem/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.assessImgFile.message));
        registry.addResourceHandler("/imgBlos/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.assessBlosImg.message));
        registry.addResourceHandler("/assFileCode/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.assFileCode.message));
        registry.addResourceHandler("/tempFile/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.tempFile.message));
        registry.addResourceHandler("/fictionImg/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.fictionImg.message));
        registry.addResourceHandler("/fictionFile/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.fictionFile.message));
        registry.addResourceHandler("/dramaImage/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.dramaImage.message));
        registry.addResourceHandler("/ehentai/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.accessEhentai.message));
        registry.addResourceHandler("/imageAlbum/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.imageAlbum.message));
        registry.addResourceHandler("/imageAlbumThumbanil/**").addResourceLocations("file:///" + redisUtils.getConfig(ConfigDicEnum.imageAlbumThumbanil.message));
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
            .excludePathPatterns("/dramaImage/**")
            .excludePathPatterns("/ehentai/**")
            .excludePathPatterns("/imageAlbum/**")
            .excludePathPatterns("/imageAlbumThumbanil/**")
            .excludePathPatterns("/fictionFile/**");
        log.info("系统拦截器初始化完成");

    }



}
