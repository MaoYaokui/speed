package com.tasi.speed.dto.productApi;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ProductApi
 * @Description TODO 商品 API
 * @Author myk
 * @Date 2020/5/17 下午3:00
 * @Version 1.0
 **/
@Data
public class ProductApi {
    /**
     * 商品Id
     */
    private int id;
    /**
     * 商品类型Id
     */
    private int cateId;
    /**
     * 商品展示图
     */
    private String image;
    /**
     * 商品展示图列表
     */
    private List<ProductImageApi> imgList;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 商品价格
     */
    private double price;
    /**
     * 商品已售数量
     */
    private int sales;
    /**
     * 商品库存
     */
    private int stock;
    /**
     * 商品规格表
     */
    private List<ProductItemApi> specList;
    /**
     * 导师介绍
     */
    private String tutorIntroduced;
    /**
     * 商品详情
     */
    private String instruction;
}
