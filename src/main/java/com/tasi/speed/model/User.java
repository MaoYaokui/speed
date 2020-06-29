package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO 用户表
 * @Author myk
 * @Date 2020/2/12 20:55
 * @Version 1.0
 **/
@Data
public class User {
    /**
     * 用户id 主键
     */
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 用户类型 管理员:0 会员:1 散客:2
     */
    private int type;
    /**
     * 微信用户唯一ID
     */
    private String openid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 地区
     */
    private String region;
    /**
     * 性别 默认:0 未知:0 男:1 女:2
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 注册IP
     */
    private String regIp;
    /**
     * 用户二维码
     */
    private String qrCode;
    /**
     * 付款二维码
     */
    private String payCode;
    /**
     * 用户状态 默认:0 正常:1 黑名单-1
     */
    private String status;
    /**
     * 关注状态 未关注:0 已关注:1
     */
    private String follow;
    /**
     * 最近一次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logInTime;
    /**
     * 添加用户
     */
    private String createCode;
    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    /**
     * 更新用户
     */
    private String updateCode;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
}
