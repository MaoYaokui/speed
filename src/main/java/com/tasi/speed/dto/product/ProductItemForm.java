package com.tasi.speed.dto.product;

import lombok.Data;

/**
 * @ClassName ProductItemForm
 * @Description TODO 商品营期
 * @Author myk
 * @Date 2020/5/15 下午7:45
 * @Version 1.0
 **/
@Data
public class ProductItemForm {
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
