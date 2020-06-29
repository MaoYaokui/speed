package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName ProductImage
 * @Description TODO 商品展示图表
 * @Author myk
 * @Date 2020/5/9 上午11:22
 * @Version 1.0
 **/
@Data
public class ProductImage {
    /**
     * Id 主键
     */
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 商品Id
     */
    private int productId;
    /**
     * 商品原图
     */
    private String originalImg;
    /**
     * 商品缩略图
     */
    private String thumbImg;
    /**
     * 顺序排序
     */
    private int sortNum;
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
