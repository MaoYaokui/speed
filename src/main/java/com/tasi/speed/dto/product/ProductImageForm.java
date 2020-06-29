package com.tasi.speed.dto.product;

import lombok.Data;

/**
 * @ClassName ProductImageForm
 * @Description TODO 商品缩略图
 * @Author myk
 * @Date 2020/5/16 上午2:32
 * @Version 1.0
 **/
@Data
public class ProductImageForm {
    /**
     * 缩略图Id
     */
    private int id;

    /**
     * 缩略图路径
     */
    private String url;
}
