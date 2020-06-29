package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Appointment
 * @Description TODO 约课表
 * @Author maoyaokui
 * @Date 2020/5/31 6:26 下午
 * @Version 1.0
 **/
@Data
public class Appointment {
    /**
     * Id 主键
     */
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 课程名称
     */
    private String product;
    /**
     * 上课时间
     */
    private String time;
    /**
     * 学员手机
     */
    private String phone;
    /**
     * 约课备注
     */
    private String remarks;
    /**
     * 约课状态 默认：0 申请：0 通过：1 已完成：2
     */
    private int state;
    /**
     * 总核销次数
     */
    private int totalTimes;
    /**
     * 剩余次数
     */
    private int availableTimes;
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
