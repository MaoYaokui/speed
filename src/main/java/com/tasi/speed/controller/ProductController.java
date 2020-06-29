package com.tasi.speed.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tasi.speed.common.ValidatorUtils;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.product.ProductForm;
import com.tasi.speed.dto.product.ProductImageForm;
import com.tasi.speed.dto.product.ProductItemForm;
import com.tasi.speed.model.*;
import com.tasi.speed.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ProductController
 * @Description TODO 商品
 * @Author myk
 * @Date 2020/5/12 上午10:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/product")
public class ProductController extends PublicTools {
    @Autowired
    private ProductServer productServer;
    @Autowired
    private ProductCateServer productCateServer;
    @Autowired
    private ProductImageServer productImageServer;
    @Autowired
    private ProductItemServer productItemServer;

    @Value("${web.path}")
    private String webPath;

    /**
     * 商品分类信息
     *
     * @param params
     * @return
     */
    @GetMapping("/cate/list")
    public R cateList(@RequestParam Map<String, Object> params) {
        PageUtils page = productCateServer.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 新增商品分类
     *
     * @param productCate
     * @return
     */
    @PostMapping("/cate/create")
    public R cateCreate(@RequestBody ProductCate productCate) {
        ValidatorUtils.validateEntity(productCate, ProductCate.class);
        productCateServer.insert(productCate);
        return R.ok().put("productCate", productCate);
    }

    /**
     * 修改
     *
     * @param productCate
     * @return
     */
    @PostMapping("/cate/update")
    public R cateUpdate(@RequestBody ProductCate productCate) {
        ValidatorUtils.validateEntity(productCate, ProductCate.class);
        productCateServer.update(productCate);
        return R.ok();
    }

    @PostMapping("/cate/top")
    public R top(int id, int type) {
        ProductCate productCate = productCateServer.getById(id);
        if (type == 0) {
            // 置顶
            productCate.setSortNum(1);
        }
        if (type == 1) {
            // 取消置顶
            productCate.setSortNum(0);
        }
        productCate.setUpdateAt(new Date());
        productCateServer.updateById(productCate);
        return R.ok();
    }

    /**
     * 商品信息
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = productServer.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 新增商品
     *
     * @param productForm
     * @return
     */
    @PostMapping("/create")
    public R create(@RequestBody ProductForm productForm) {
        // 录入商品表
        Product product = new Product();
        productServer.saveMessage(product, productForm);
        product.setCreateCode(productForm.getCreateCode());
        product.setCreateAt(new Date());
        productServer.save(product);

        // 修改商品缩略图
        for (ProductImageForm image : productForm.getImages()) {
            ProductImage productImage = productImageServer.getById(image.getId());
            productImage.setProductId(product.getId());
            productImage.setUpdateCode(productForm.getCreateCode());
            productImage.setUpdateAt(new Date());
            productImageServer.updateById(productImage);
        }

        // 新增商品规格
        for (ProductItemForm productItemForm : productForm.getItems()) {
            ProductItem productItem = new ProductItem();
            productItemServer.saveMessage(productItem, product, productItemForm);
            productItem.setCreateCode(productForm.getCreateCode());
            productItem.setCreateAt(new Date());
            productItemServer.save(productItem);
        }
        return R.ok().put("product", product);
    }

    /**
     * 修改商品
     *
     * @param productForm
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductForm productForm) {
        // 修改商品信息
        Product product = productServer.getById(productForm.getId());
        productServer.saveMessage(product, productForm);
        product.setUpdateCode(productForm.getUpdateCode());
        product.setUpdateAt(new Date());
        productServer.updateById(product);

        // 修改商品缩略图
        List<ProductImage> productImageList = productImageServer.list(
                new QueryWrapper<ProductImage>()
                        .eq("product_id", productForm.getId())
        );
        System.out.println("已保存数据: " + productImageList);

        // 获取需修改数据
        productForm.getImages().forEach(imageForm -> {
            if (productImageList.stream().noneMatch(image -> imageForm.getId() == image.getId())) {
                ProductImage productImage = productImageServer.getById(imageForm.getId());
                productImage.setProductId(product.getId());
                productImage.setUpdateCode(productForm.getCreateCode());
                productImage.setUpdateAt(new Date());
                productImageServer.updateById(productImage);
            }
        });

        // 获取应删除数据 取数据库数据与新数据交集
        productImageList.forEach(image -> {
            if (productForm.getImages().stream().noneMatch(imageForm -> image.getId() == imageForm.getId())) {
                productImageServer.removeById(image.getId());
            }
        });

        // 修改商品规格
        List<ProductItem> productItemList = productItemServer.list(
                new QueryWrapper<ProductItem>()
                        .eq("product_id", productForm.getId())
        );
        System.out.println("已保存数据: " + productItemList);

        // 获取需修改/新增数据
        productForm.getItems().forEach(itemForm -> {
            // 新增
            if (itemForm.getId() == 0) {
                ProductItem productItem = new ProductItem();
                productItemServer.saveMessage(productItem, product, itemForm);
                productItem.setCreateCode(productForm.getCreateCode());
                productItem.setCreateAt(new Date());
                productItemServer.save(productItem);
            }
            // 修改
            else if (productItemList.stream().anyMatch(item -> itemForm.getId() == item.getId())) {
                ProductItem productItem = productItemServer.getById(itemForm.getId());
                productItemServer.saveMessage(productItem, product, itemForm);
                productItem.setUpdateCode(productForm.getUpdateCode());
                productItem.setUpdateAt(new Date());
                productItemServer.updateById(productItem);
            }
        });

        // 获取需删除数据
        productItemList.forEach(item -> {
            if (productForm.getItems().stream().noneMatch(itemForm -> item.getId() == itemForm.getId())) {
                productItemServer.removeById(item.getId());
            }
        });
        return R.ok().put("product", product);
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public R delete(int id) {
        System.out.println("id：" + id);
        Product product = productServer.getById(id);
        product.setIsShow("1");
        product.setUpdateAt(new Date());
        productServer.updateById(product);
        return R.ok();
    }

    @PostMapping("/top")
    public R delete(int id, int type) {
        Product product = productServer.getById(id);
        if (type == 0) {
            // 置顶
            product.setSortNum(1);
        }
        if (type == 1) {
            // 取消置顶
            product.setSortNum(0);
        }
        product.setUpdateAt(new Date());
        productServer.updateById(product);
        return R.ok();
    }

    /**
     * 删除商品缩略图
     *
     * @param id
     * @return
     */
    @PostMapping("/image/delete")
    public R imageDelete(int id) {
        productImageServer.removeById(id);
        return R.ok();
    }

    /**
     * 获取商品信息
     *
     * @param productId 商品Id
     * @return
     */
    @GetMapping("/get")
    public R get(int productId) {
        ProductForm productForm = new ProductForm();
        // 获取商品基本信息
        Product product = productServer.getById(productId);
        productForm.setId(product.getId());
        productForm.setCateId(product.getCateId());
        productForm.setPromoImage(product.getPromoImage());
        productForm.setName(product.getName());
        productForm.setIsShow(product.getIsShow());
        productForm.setRecommended(product.getRecommended());
        productForm.setCarousel(product.getCarousel());
        productForm.setTutorIntroduced(product.getTutorIntroduced());
        productForm.setInstruction(product.getInstruction());
        // 获取商品展示图
        List<ProductImageForm> images = new ArrayList<>();
        productImageServer.list(
                new QueryWrapper<ProductImage>()
                        .eq("product_id", productId)
        ).forEach(productImage -> {
            ProductImageForm productImageForm = new ProductImageForm();
            productImageForm.setId(productImage.getId());
            productImageForm.setUrl(webPath + productImage.getOriginalImg());
            images.add(productImageForm);
        });
        productForm.setImages(images);
        // 获取商品营期
        List<ProductItemForm> items = new ArrayList<>();
        productItemServer.list(
                new QueryWrapper<ProductItem>()
                        .eq("product_id", productId)
        ).forEach(productItem -> {
            ProductItemForm productItemForm = new ProductItemForm();
            productItemForm.setId(productItem.getId());
            productItemForm.setName(productItem.getName());
            productItemForm.setSalePrice(productItem.getSalePrice());
            productItemForm.setStock(productItem.getStock());
            items.add(productItemForm);
        });
        productForm.setItems(items);
        return R.ok().put("product", productForm);
    }
}
