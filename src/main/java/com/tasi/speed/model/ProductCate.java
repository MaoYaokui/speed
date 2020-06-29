package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName ProductCate
 * @Description TODO 商品分类表
 * @Author myk
 * @Date 2020/5/9 上午10:06
 * @Version 1.0
 **/
@Data
public class ProductCate {
    /**
     * Id 主键
     */
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类小图标
     */
    private String iconImg;
    /**
     * 分类上级 最上级目录 -1
     */
    private int parentId;
    /**
     * 是否显示分类 默认:0 默认:0 显示:0 删除:1
     */
    private int isShow;
    /**
     * 分类排序 默认：0 正常：0 置顶：1
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
