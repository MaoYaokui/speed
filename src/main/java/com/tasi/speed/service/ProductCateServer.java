package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.mapper.ProductCateMapper;
import com.tasi.speed.mapper.UserMapper;
import com.tasi.speed.model.ProductCate;
import com.tasi.speed.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName ProductCateServer
 * @Description TODO 商品分类
 * @Author myk
 * @Date 2020/5/12 上午10:18
 * @Version 1.0
 **/
@Service
public class ProductCateServer extends ServiceImpl<ProductCateMapper, ProductCate> {

    /**
     * 商品分类信息列表
     *
     * @param params
     * @return
     */
    public PageUtils queryPage(Map<String, Object> params) {
        Object name = params.get("name");
        IPage<ProductCate> page = this.page(
                new Query<ProductCate>().getPage(params),
                new QueryWrapper<ProductCate>()
                        .ne("id", 1)
                        .like(!CommonFunction.isnull(name), "name", name)
                        .orderByDesc("sort_num")
        );
        return new PageUtils(page);
    }

    /**
     * 插入
     *
     * @param productCate
     */
    public void insert(ProductCate productCate) {
        productCate.setParentId(1);
        productCate.setCreateAt(new Date());
        this.save(productCate);
    }

    /**
     * 修改
     *
     * @param productCate
     */
    public void update(ProductCate productCate) {
        productCate.setUpdateAt(new Date());
        this.updateById(productCate);
    }
}
