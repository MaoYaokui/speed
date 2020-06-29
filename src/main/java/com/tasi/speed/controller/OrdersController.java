package com.tasi.speed.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.model.Appointment;
import com.tasi.speed.model.Orders;
import com.tasi.speed.service.AppointmentServer;
import com.tasi.speed.service.OrdersServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OrdersController
 * @Description TODO 订单表
 * @Author myk
 * @Date 2020/5/20 下午3:25
 * @Version 1.0
 **/

@RestController
@RequestMapping("/orders")
public class OrdersController extends PublicTools {
    @Autowired
    private OrdersServer ordersServer;
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
        PageUtils page = ordersServer.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取订单列表
     *
     * @param type
     * @return
     */
    @GetMapping("/listAll")
    public R ordersListAll(int type) {
        if (type == 0) {
            return R.ok().put("list", ordersServer.list(
                    new QueryWrapper<Orders>()
                            .in("status", 1)
                            .apply("create_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
            ));
        }
        if (type == 1) {
            return R.ok().put("list", ordersServer.list(
                    new QueryWrapper<Orders>()
                            .in("status", 2, 3)
                            .apply("update_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
            ));
        }
        return R.ok();
    }

    /**
     * 获取当日营业额
     *
     * @return
     */
    @GetMapping("/getTurnover")
    public R getTurnover() {
        // 今日成交订单
        int clinch = ordersServer.list(
                new QueryWrapper<Orders>()
                        .in("status", 1)
                        .apply("create_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
        ).size();
        // 今日履约订单
        int performance = ordersServer.list(
                new QueryWrapper<Orders>()
                        .in("status", 2, 3)
                        .apply("update_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
        ).size();
        // 今日约课订单
        int appointment = appointmentServer.list(
                new QueryWrapper<Appointment>()
                        .in("state", 0)
                        .apply("create_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
        ).size();
        // 今日日切流水
        double turnover = ordersServer.list(
                new QueryWrapper<Orders>()
                        .in("status", 1)
                        .apply("create_at <= DATE_ADD({0}, INTERVAL 1 DAY)", new Date())
        ).stream().mapToDouble(Orders::getPayPrice).sum();
        return R.ok().put("clinch", clinch).put("performance", performance).put("appointment", appointment).put("turnover", turnover);
    }

}
