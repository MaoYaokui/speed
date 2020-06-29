package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.mapper.AppointmentMapper;
import com.tasi.speed.mapper.OrdersMapper;
import com.tasi.speed.model.Appointment;
import com.tasi.speed.model.Orders;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName AppointmentServer
 * @Description TODO 约课
 * @Author maoyaokui
 * @Date 2020/5/31 6:39 下午
 * @Version 1.0
 **/
@Service
public class AppointmentServer extends ServiceImpl<AppointmentMapper, Appointment> {

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Appointment> page = this.page(
                new Query<Appointment>().getPage(params),
                new QueryWrapper<Appointment>()
        );
        return new PageUtils(page);
    }
}
