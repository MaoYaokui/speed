package com.tasi.speed.dto.productApi;

import lombok.Data;

/**
 * @ClassName ProductCateApi
 * @Description TODO 获取所有商品分类 API
 * @Author myk
 * @Date 2020/5/16 下午8:55
 * @Version 1.0
 **/
@Data
public class ProductCateApi {
    /**
     * 分类Id
     */
    private int id;
    /**
     * 父Id
     */
    private int pid;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类图片
     */
    private String picture;
}
