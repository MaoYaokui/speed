package com.tasi.speed.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.dto.product.ProductItemForm;
import com.tasi.speed.mapper.ProductImageMapper;
import com.tasi.speed.mapper.ProductItemMapper;
import com.tasi.speed.model.Product;
import com.tasi.speed.model.ProductImage;
import com.tasi.speed.model.ProductItem;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProductItemServer
 * @Description TODO 商品规格
 * @Author myk
 * @Date 2020/5/15 下午9:18
 * @Version 1.0
 **/
@Service
public class ProductItemServer extends ServiceImpl<ProductItemMapper, ProductItem> {

    /**
     * 录入商品规格信息
     *
     * @param productItem
     * @param product
     * @param itemForm
     */
    public void saveMessage(ProductItem productItem, Product product, ProductItemForm itemForm) {
        productItem.setProductId(product.getId());
        productItem.setName(itemForm.getName());
        productItem.setSalePrice(itemForm.getSalePrice());
        productItem.setStock(itemForm.getStock());
    }
}
