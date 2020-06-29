package com.tasi.speed.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tasi.speed.common.ValidatorUtils;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.AppointmentForm;
import com.tasi.speed.model.Appointment;
import com.tasi.speed.model.Orders;
import com.tasi.speed.model.ProductCate;
import com.tasi.speed.service.AppointmentServer;
import com.tasi.speed.service.OrdersServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName Appointment
 * @Description TODO
 * @Author maoyaokui
 * @Date 2020/6/1 12:48 上午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/appointment")
public class AppointmentController extends PublicTools {
    @Autowired
    private AppointmentServer appointmentServer;

    /**
     * 订单列表
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R ordersList(@RequestParam Map<String, Object> params) {
        PageUtils page = appointmentServer.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 所有订单列表
     *
     * @param type
     * @return
     */
    @GetMapping("/listAll")
    public R ordersListAll() {
        return R.ok().put("list", appointmentServer.list(
                new QueryWrapper<Appointment>()
                        .in("state", 0)
                        .apply("create_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
        ));
    }

    /**
     * 通过
     *
     * @param appointmentForm
     * @return
     */
    @PostMapping("/through")
    public R through(@RequestBody AppointmentForm appointmentForm) {
        ValidatorUtils.validateEntity(appointmentForm, AppointmentForm.class);
        Appointment appointment = appointmentServer.getById(appointmentForm.getId());
        appointment.setTotalTimes(appointmentForm.getTimes());
        appointment.setAvailableTimes(appointmentForm.getTimes());
        appointment.setState(1);
        appointment.setUpdateAt(new Date());
        appointment.setUpdateCode(appointmentForm.getUpdateCode());
        appointmentServer.updateById(appointment);
        return R.ok();
    }

    /**
     * 驳回
     *
     * @param appointmentForm
     * @return
     */
    @PostMapping("/overrule")
    public R overrule(@RequestBody AppointmentForm appointmentForm) {
        ValidatorUtils.validateEntity(appointmentForm, AppointmentForm.class);
        Appointment appointment = appointmentServer.getById(appointmentForm.getId());
        appointment.setState(-1);
        appointmentServer.updateById(appointment);
        return R.ok();
    }

    /**
     * 核销
     *
     * @param appointmentForm
     * @return
     */
    @PostMapping("/writeOff")
    public R writeOff(@RequestBody AppointmentForm appointmentForm) {
        ValidatorUtils.validateEntity(appointmentForm, AppointmentForm.class);
        Appointment appointment = appointmentServer.getById(appointmentForm.getId());
        appointment.setAvailableTimes(appointment.getAvailableTimes() - 1);
        if (appointment.getAvailableTimes() == 0) {
            appointment.setState(2);
        }
        appointmentServer.updateById(appointment);
        return R.ok();
    }


}
