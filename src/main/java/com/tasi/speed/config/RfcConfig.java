package com.tasi.speed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RfcConfig
 * @Description TODO
 * @Author myk
 * @Date 2020/3/3 1:23
 * @Version 1.0
 **/

@Configuration
public class RfcConfig {
    @Bean
    public Integer setRfc() {
        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
        return 0;
    }
}
