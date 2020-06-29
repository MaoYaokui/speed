package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.mapper.OrdersMapper;
import com.tasi.speed.model.Orders;
import com.tasi.speed.model.ProductCate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName OrderServer
 * @Description TODO 订单
 * @Author myk
 * @Date 2020/5/20 上午1:36
 * @Version 1.0
 **/
@Service
public class OrdersServer extends ServiceImpl<OrdersMapper, Orders> {

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Orders> page = this.page(
                new Query<Orders>().getPage(params),
                new QueryWrapper<Orders>()

        );
        return new PageUtils(page);
    }
}
