package com.tasi.speed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tasi.speed.model.Appointment;
import com.tasi.speed.model.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO 约课
 * @Author myk
 * @Date 2020/2/12 23:34
 * @Version 1.0
 **/
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {

}
