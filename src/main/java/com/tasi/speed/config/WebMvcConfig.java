package com.tasi.speed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfigurer
 * @Description TODO
 * @Author myk
 * @Date 2020/2/29 1:58
 * @Version 1.0
 **/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String file;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        assert registry != null;
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //访问路径: http://*:*/file/*
        //实际路径: */file/
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + file);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        assert registry != null;
        /*设置允许跨域的路径*/
        registry.addMapping("/**")
                /*设置允许跨域请求的域名*/
                .allowedOrigins("*")
                /*是否允许证书 不再默认开启*/
                .allowCredentials(true)
                /*设置允许的方法*/
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                /*跨域允许时间*/
                .maxAge(3600);
    }
}
