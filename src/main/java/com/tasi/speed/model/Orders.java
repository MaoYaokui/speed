package com.tasi.speed.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Order
 * @Description TODO 订单表
 * @Author myk
 * @Date 2020/5/20 上午12:03
 * @Version 1.0
 **/
@Data
public class Orders {
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
     * 商品规格Id
     */
    private int itemId;
    /**
     * 购买用户Id
     */
    private int userId;
    /**
     * 订单编号，以系统时间来生成，确保唯一
     */
    private String ordersNumber;
    /**
     * 购买数量
     */
    private int count;
    /**
     * 订单总价
     */
    private double totalPrice;
    /**
     * 订单支付价格
     */
    private double payPrice;
    /**
     * 订单状态 默认:0 待付款:0 报名成功:1 进行中:2 已完成:3 交易取消-1
     */
    private int status;
    /**
     * 总核销次数
     */
    private int totalNumber;
    /**
     * 可核销次数
     */
    private int cancelNumber;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品缩略图
     */
    private String thumbImg;
    /**
     * 商品规格名称
     */
    private String itemName;
    /**
     * 商品销售价
     */
    private double salePrice;
    /**
     * 订单备注
     */
    private String remark;
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
