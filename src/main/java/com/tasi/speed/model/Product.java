package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Product
 * @Description TODO 商品表
 * @Author myk
 * @Date 2020/5/9 上午10:10
 * @Version 1.0
 **/
@Data
public class Product {
    /**
     * Id 主键
     */
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 主分类Id
     */
    private int cateId;
    /**
     * 子分类Id
     */
    private int cateSecondaryId;
    /**
     * 商品编号
     */
    private String number;
    /**
     * 商品宣传图
     */
    private String promoImage;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 导师介绍
     */
    private String tutorIntroduced;
    /**
     * 商品详情
     */
    private String instruction;
    /**
     * 商品排序 默认：0 正常：0 置顶：1
     */
    private int sortNum;
    /**
     * 是否在售商品 默认:0 在售:0 下架:1
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
     * 添加用户
     */
    private String createCode;
    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    /**
     * 更新用户
     */
    private String updateCode;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
}
