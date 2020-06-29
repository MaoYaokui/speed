package com.tasi.speed.dto;

import lombok.Data;

/**
 * @ClassName AppointmentForm
 * @Description TODO 约课
 * @Author maoyaokui
 * @Date 2020/6/1 1:38 上午
 * @Version 1.0
 **/
@Data
public class AppointmentForm {
    /**
     * id
     */
    private int id;
    /**
     * 数量
     */
    private int times;
    /**
     * 更新用户
     */
    private String updateCode;
}
