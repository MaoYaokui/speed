package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.mapper.ProductCateMapper;
import com.tasi.speed.mapper.ProductImageMapper;
import com.tasi.speed.model.ProductCate;
import com.tasi.speed.model.ProductImage;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProductImageServer
 * @Description TODO 商品展示图
 * @Author myk
 * @Date 2020/5/14 下午10:41
 * @Version 1.0
 **/
@Service
public class ProductImageServer extends ServiceImpl<ProductImageMapper, ProductImage> {

    /**
     * 删除多余数据
     */
    public void deleteRedundantData() {
        this.remove(
                new QueryWrapper<ProductImage>()
                        .eq("product_id", null)
        );
    }
}
