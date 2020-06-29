package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.dto.product.ProductForm;
import com.tasi.speed.mapper.ProductCateMapper;
import com.tasi.speed.mapper.ProductMapper;
import com.tasi.speed.model.Product;
import com.tasi.speed.model.ProductCate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName ProductServer
 * @Description TODO 产品
 * @Author myk
 * @Date 2020/5/13 下午7:59
 * @Version 1.0
 **/
@Service
public class ProductServer extends ServiceImpl<ProductMapper, Product> {

    /**
     * 商品信息列表
     *
     * @param params
     * @return
     */
    public PageUtils queryPage(Map<String, Object> params) {
        Object name = params.get("name");
        IPage<Product> page = this.page(
                new Query<Product>().getPage(params),
                new QueryWrapper<Product>()
                        .like(!CommonFunction.isnull(name), "name", name)
                        .eq("is_show", "0")
                        .orderByDesc("sort_num")
        );
        return new PageUtils(page);
    }

    /**
     * 录入商品信息
     *
     * @param product
     * @param productForm
     */
    public void saveMessage(Product product, ProductForm productForm) {
        product.setCateId(productForm.getCateId());
        product.setPromoImage(productForm.getPromoImage());
        product.setName(productForm.getName());
        product.setTutorIntroduced(productForm.getTutorIntroduced());
        product.setInstruction(productForm.getInstruction());
        product.setIsShow(productForm.getIsShow());
        product.setRecommended(productForm.getRecommended());
        product.setCarousel(productForm.getCarousel());
    }
}
