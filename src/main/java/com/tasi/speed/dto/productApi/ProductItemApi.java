package com.tasi.speed.dto.productApi;

import lombok.Data;

/**
 * @ClassName ProductItemApi
 * @Description TODO 商品展类型
 * @Author myk
 * @Date 2020/5/17 下午4:21
 * @Version 1.0
 **/
@Data
public class ProductItemApi {
    /**
     * 规格Id
     */
    private int id;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 销售价
     */
    private double salePrice;
    /**
     * 库存
     */
    private int stock;
}
