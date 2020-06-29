package com.tasi.speed.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName FileProperties
 * @Description TODO 文件上传配置
 * @Author myk
 * @Date 2020/2/28 0:32
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
