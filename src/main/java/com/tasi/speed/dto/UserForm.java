package com.tasi.speed.dto;

import lombok.Data;

/**
 * @ClassName User
 * @Description TODO 用户信息
 * @Author myk
 * @Date 2020/2/25 0:46
 * @Version 1.0
 **/
@Data
public class UserForm {
    /**
     * 账号
     */
    String username;
    /**
     * 密码
     */
    String password;
}
