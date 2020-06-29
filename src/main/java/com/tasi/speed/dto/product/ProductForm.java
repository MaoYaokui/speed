package com.tasi.speed.dto.product;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ProductForm
 * @Description TODO 商品
 * @Author myk
 * @Date 2020/5/15 下午5:52
 * @Version 1.0
 **/
@Data
public class ProductForm {
    /**
     * 商品Id
     */
    private int id;
    /**
     * 商品分类Id
     */
    private int cateId;
    /**
     * 商品展示图
     */
    private String promoImage;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 是否在售商品 默认:0 在售:0 未在售:1
     */
    private String isShow;
    /**
     * 是否推荐商品 默认不推荐 不推荐:0 推荐:1
     */
    private String recommended;
    /**
     * 首页轮播图 默认否 否:0 是:1
     */
    private String carousel;
    /**
     * 导师介绍
     */
    private String tutorIntroduced;
    /**
     * 商品详情
     */
    private String instruction;
    /**
     * 商品缩略图列表
     */
    private List<ProductImageForm> images;
    /**
     * 商品规格列表
     */
    private List<ProductItemForm> items;
    /**
     * 添加用户
     */
    private String createCode;
    /**
     * 更新用户
     */
    private String updateCode;
}
