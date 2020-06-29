package com.tasi.speed.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName AppointmentApi
 * @Description TODO
 * @Author maoyaokui
 * @Date 2020/5/31 11:25 下午
 * @Version 1.0
 **/
@Data
public class AppointmentApi {
    /**
     * 用户Id
     */
    int userId;
    /**
     * 课程名称
     */
    String product;
    /**
     * 上课时间
     */
    String time;
    /**
     * 手机号
     */
    String phone;
    /**
     * 备注
     */
    String remarks;
}
