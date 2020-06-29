package com.tasi.speed.model;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName userToken
 * @Description TODO 用户Token表
 * @Author myk
 * @Date 2020/2/25 1:17
 * @Version 1.0
 **/
@Data
public class UserToken {
    /**
     * 用户id 主键
     */
    int id;
    /**
     * token
     */
    String token;
    /**
     * 过期时间
     */
    Date expireTime;
    /**
     * 更新时间
     */
    Date updateTime;
}
