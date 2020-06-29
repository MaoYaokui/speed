package com.tasi.speed.dto;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName OrderApi
 * @Description TODO 订单实体类
 * @Author myk
 * @Date 2020/5/17 下午7:00
 * @Version 1.0
 **/
@Data
public class OrderApi {
    /**
     * 订单Id
     */
    private int id;
    /**
     * 商品Id
     */
    private int productId;
    /**
     * 规格Id
     */
    private int itemId;
    /**
     * 购买用户Id
     */
    private int userId;
    /**
     * 购买数量
     */
    private int count;
    /**
     * 订单支付价格
     */
    private double  payPrice;
    /**
     * 订单备注
     */
    private String remark;

    /**
     * 商品展示图
     */
    private String image;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 商品规格名称
     */
    private String itemName;
    /**
     * 商品规格销售价
     */
    private double salePrice;
}
