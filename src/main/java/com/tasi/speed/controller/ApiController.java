package com.tasi.speed.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.WxUtils;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.AppointmentApi;
import com.tasi.speed.dto.OrderApi;
import com.tasi.speed.dto.productApi.*;
import com.tasi.speed.model.*;
import com.tasi.speed.service.*;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @ClassName ApiController
 * @Description TODO 小程序API
 * @Author myk
 * @Date 2020/5/7 下午4:29
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class ApiController extends PublicTools {
    // 测试小程序
    //private final static String AppId = "wx9e7a6e2f08e71a98";
    //private final static String SECRET = "c554018bac471a7d7faa6012d52f1afd";

    private String appid = "wxaf7955a00cb4d128";
    private String secret = "7d5ff050d9966d28d581966ffdd2b9af";
    private String mch_id = "1596293651";
    @Value("${wx.path}")
    private String notifyUrl;
    private String key = "0LWSHgX8M2x7JuVP1uajUcXjHRxresjk";
    private String placeUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    @Value("${wx.ip}")
    private String ipAddress;

    /**
     * 微信支付工具类
     */
    @Autowired
    private WxUtils wxUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductCateServer productCateServer;
    @Autowired
    private ProductServer productServer;
    @Autowired
    private ProductImageServer productImageServer;
    @Autowired
    private ProductItemServer productItemServer;
    @Autowired
    private OrdersServer ordersServer;
    @Autowired
    private AppointmentServer appointmentServer;

    /**
     * 获取首页轮播图
     *
     * @return
     */
    @GetMapping("/carousel/list")
    public R carouselList() {
        List<Product> productList = productServer.list(
                new QueryWrapper<Product>()
                        .eq("is_show", "0")
                        .last("limit 3")
        );
        return R.ok().put("carouselList", productList);
    }

    /**
     * 获取热销课程
     *
     * @return
     */
    @GetMapping("/goods/list")
    public R goodsList() {
        List<ProductApi> productApiList = new ArrayList<>();
        productServer.list(
                new QueryWrapper<Product>()
                        .eq("is_show", "0")
        ).forEach(product -> {
            ProductApi productApi = new ProductApi();
            productApi.setId(product.getId());
            productApi.setCateId(product.getCateId());
            // 获取商品展示图
            productApi.setImage(
                    productImageServer.getOne(
                            new QueryWrapper<ProductImage>()
                                    .like("product_id", product.getId())
                    ).getOriginalImg()
            );
            productApi.setTitle(product.getName());
            productApi.setPrice(
                    productItemServer.getOne(
                            new QueryWrapper<ProductItem>()
                                    .like("product_id", product.getId())
                    ).getSalePrice()
            );
            Random rand = new Random();
            productApi.setSales(rand.nextInt(1000));
            productApiList.add(productApi);
        });
        return R.ok().put("goodsList", productApiList);
    }


    /**
     * 用户注册
     *
     * @param code
     * @return
     * @throws IOException
     */
    @GetMapping("/user/login")
    public R userLogin(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
        //使用HttpClient发送请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //发送Get请求
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");
        //获得响应
        CloseableHttpResponse response = httpclient.execute(request);
        //拿到响应体
        HttpEntity httpEntity = response.getEntity();
        //使用工具转换
        // 转成string
        String result = EntityUtils.toString(httpEntity, "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        //拿到的所有内容
        System.out.println(jsonObject);
        String openid = jsonObject.get("openid").toString();
        //拿到的openid
        System.out.println(openid);
        // 查询用户是否注册
        User user = userService.getOne(
                new QueryWrapper<User>()
                        .eq("openid", openid)
        );
        if (user == null) {
            // 用户注册
            user = new User();
            user.setType(2);
            user.setOpenid(openid);
            user.setCreateCode("自行注册");
            user.setCreateAt(new Date());
            userService.save(user);
        }
        return R.ok().put("userId", user.getId());
    }


    /**
     * 用户更新信息
     *
     * @param params/
     * @return
     * @throws IOException
     */
    @GetMapping("/user/updateUserInfo")
    public R userUpdateUserInfo(@RequestParam Map<String, Object> params) {
        User user = userService.getById(Integer.parseInt(params.get("userId").toString()));
        user.setNickName(params.get("nickName").toString());
        user.setUserAvatar(params.get("avatarUrl").toString());
        String region = params.get("country").toString();
        if (params.get("province") != null) {
            region += ',' + params.get("province").toString();
        }
        if (params.get("city") != null) {
            region += ',' + params.get("city").toString();
        }
        user.setRegion(region);
        user.setSex(params.get("gender").toString());
        user.setLogInTime(new Date());
        user.setUpdateCode("自行修改");
        user.setUpdateAt(new Date());
        userService.update(user);
        return R.ok();
    }

    /**
     * 获取所有商品分类
     *
     * @return
     */
    @GetMapping("/product/cate/list")
    public R productCateList() {
        List<ProductCateApi> productCateApiList = new ArrayList<>();
        productCateServer.list(
                new QueryWrapper<ProductCate>()
                        .orderByDesc("sort_num")
        ).forEach(productCate -> {
            ProductCateApi productCateApi = new ProductCateApi();
            productCateApi.setId(productCate.getId());
            productCateApi.setPid(productCate.getParentId());
            productCateApi.setName(productCate.getName());
            productCateApi.setPicture(productCate.getIconImg());
            productCateApiList.add(productCateApi);
        });
        return R.ok().put("cateList", productCateApiList);
    }

    /**
     * 获取所有商品
     *
     * @param cateId
     * @return
     */
    @GetMapping("/product/list")
    public R productList(int cateId) {
        List<ProductApi> productApiList = new ArrayList<>();
        productServer.list(
                new QueryWrapper<Product>()
                        .like("cate_id", cateId)
                        .eq("is_show", "0")
                        .orderByDesc("sort_num")
        ).forEach(product -> {
            ProductApi productApi = new ProductApi();
            productApi.setId(product.getId());
            productApi.setCateId(cateId);
            // 获取商品展示图
            productApi.setImage(
                    productImageServer.getOne(
                            new QueryWrapper<ProductImage>()
                                    .like("product_id", product.getId())
                    ).getOriginalImg()
            );
            productApi.setTitle(product.getName());
            productApi.setPrice(
                    productItemServer.getOne(
                            new QueryWrapper<ProductItem>()
                                    .like("product_id", product.getId())
                    ).getSalePrice()
            );
            Random rand = new Random();
            productApi.setSales(rand.nextInt(1000));
            productApiList.add(productApi);
        });
        return R.ok().put("goodsList", productApiList);
    }

    /**
     * 获取商品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/product")
    public R product(int id) {
        ProductApi productApi = new ProductApi();
        // 获取对应商品
        Product product = productServer.getById(id);
        productApi.setId(product.getId());
        productApi.setCateId(product.getCateId());
        List<ProductImageApi> imgList = new ArrayList<>();
        productImageServer.list(
                new QueryWrapper<ProductImage>()
                        .like("product_id", product.getId())
        ).forEach(image -> {
            ProductImageApi productImageApi = new ProductImageApi();
            productImageApi.setSrc(image.getOriginalImg());
            imgList.add(productImageApi);
        });
        productApi.setImgList(imgList);
        productApi.setTitle(product.getName());
        productApi.setSales(0);
        productApi.setStock(0);
        List<ProductItemApi> specList = new ArrayList<>();
        productItemServer.list(
                new QueryWrapper<ProductItem>()
                        .like("product_id", product.getId())
        ).forEach(item -> {
            ProductItemApi productItemApi = new ProductItemApi();
            productItemApi.setId(item.getId());
            productItemApi.setName(item.getName());
            productItemApi.setSalePrice(item.getSalePrice());
            productItemApi.setStock(item.getStock());
            specList.add(productItemApi);
        });
        productApi.setSpecList(specList);
        productApi.setTutorIntroduced(product.getTutorIntroduced());
        productApi.setInstruction(product.getInstruction());
        return R.ok().put("product", productApi);
    }

    /**
     * 获取订单列表
     *
     * @param userId
     * @param state  全部：0 待付款：1
     * @return
     */
    @GetMapping("/orders/list")
    public R ordersList(int userId, int state) {
        List<Orders> ordersList = ordersServer.list(
                new QueryWrapper<Orders>()
                        .like("user_id", userId)
                        .eq(state == 1, "status", 0)
                        .orderByDesc("create_at")
        );
        return R.ok().put("ordersList", ordersList);
    }

    /**
     * 获取订单信息
     *
     * @param productId
     * @param itemId
     * @return
     */
    @GetMapping("/orders/info")
    public R ordersGet(int productId, int itemId) {
        OrderApi orderApi = new OrderApi();
        orderApi.setProductId(productId);
        orderApi.setItemId(itemId);
        orderApi.setImage(
                productImageServer.getOne(
                        new QueryWrapper<ProductImage>()
                                .like("product_id", productId)
                ).getOriginalImg()
        );
        Product product = productServer.getById(productId);
        orderApi.setTitle(product.getName());
        ProductItem productItem = productItemServer.getById(itemId);
        orderApi.setItemName(productItem.getName());
        orderApi.setSalePrice(productItem.getSalePrice());
        return R.ok().put("orders", orderApi);
    }

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    @SneakyThrows
    @GetMapping("/orders/get")
    public R ordersGet(int id) {
        Orders orders = ordersServer.getById(id);
        User user = userService.getById(orders.getUserId());
        OrderApi orderApi = new OrderApi();
        orderApi.setId(id);
        orderApi.setPayPrice(orders.getPayPrice());

        Map order = createOrder(String.valueOf(orders.getId()), BigDecimal.valueOf(orders.getPayPrice() * 100), orders.getProductName(), ipAddress, user.getOpenid());
        System.out.println("order：" + order);
        return R.ok().put("orders", orderApi).put("payOrder", order).put("appid", appid).put("key", key);
    }

    /**
     * 创建支付订单，对应文章中第一步，由合理的业务 service 调用，
     * 比如：购买商品业务中：
     * 用户选择商品提交服务器 ->
     * 服务器计算价格，生成业务订单、生成支付订单 ->
     * 用户支付 ->
     * 服务器确认支付结果，更新支付订单状态、更新业务订单状态
     * 此处，应该是在第二步中生成该支付订单
     *
     * @param orderId   公司业务订单号
     * @param price     价格
     * @param body      主题信息
     * @param ipAddress 客户端APP IP 地址
     * @param openid
     * @return
     * @throws IOException
     */
    public Map createOrder(String orderId, BigDecimal price, String body, String ipAddress, String openid) throws IOException {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        parameters.put("device_info", "WEB"); // 默认 "WEB"
        parameters.put("body", body);
        parameters.put("nonce_str", WxUtils.gen32RandomString()); // 32 位随机字符串
        parameters.put("notify_url", notifyUrl);
        parameters.put("out_trade_no", orderId);
        parameters.put("total_fee", price.intValue());
        //parameters.put("total_fee", 1); // 测试时，将支付金额设置为 1 分钱
        parameters.put("spbill_create_ip", ipAddress);
        parameters.put("trade_type", "JSAPI");
        parameters.put("openid", openid);
        parameters.put("sign", wxUtils.createSign(parameters, key)); // sign 必须在最后
        String result = wxUtils.executeHttpPost(placeUrl, parameters); // 执行 HTTP 请求，获取接收的字符串（一段 XML）
        return wxUtils.createSign2(result, key);
    }

    /**
     * 微信支付回调
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/orders/callBack")
    public R callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("---------------------------------------------------接收回调---------------------------------------------------");
        // 预先设定返回的 response 类型为 xml
        response.setHeader("Content-type", "application/xml");
        // 读取参数，解析Xml为map
        Map<String, String> map = wxUtils.transferXmlToMap(wxUtils.readRequest(request));
        // 转换为有序 map，判断签名是否正确
        boolean isSignSuccess = wxUtils.checkSign(new TreeMap<String, Object>(map), key);
        if (isSignSuccess) {
            // 签名校验成功，说明是微信服务器发出的数据
            String orderId = map.get("out_trade_no");
            Orders orders = ordersServer.getById(Integer.parseInt(orderId));
            if (orders.getStatus() == 0) {
                orders.setStatus(1);
                orders.setUpdateAt(new Date());
                ordersServer.updateById(orders);
            }
            return R.ok();
        } else {
            // 签名校验失败（可能不是微信服务器发出的数据）
            return R.error();
        }
    }


    /**
     * 创建订单
     *
     * @param orderForm
     * @return
     */
    @PostMapping("/orders/create")
    public R ordersCreate(@RequestBody OrderApi orderForm) {
        Orders order = new Orders();
        order.setProductId(orderForm.getProductId());
        order.setItemId(orderForm.getItemId());
        order.setUserId(orderForm.getUserId());
        order.setCount(orderForm.getCount());
        order.setPayPrice(orderForm.getPayPrice());
        order.setStatus(0);
        Product product = productServer.getById(orderForm.getProductId());
        order.setProductName(product.getName());
        order.setThumbImg(
                productImageServer.getOne(
                        new QueryWrapper<ProductImage>()
                                .like("product_id", orderForm.getProductId())
                ).getOriginalImg()
        );
        ProductItem productItem = productItemServer.getById(orderForm.getItemId());
        order.setItemName(productItem.getName());
        order.setSalePrice(productItem.getSalePrice());
        order.setRemark(orderForm.getRemark());
        order.setCreateCode("系统生成");
        order.setCreateAt(new Date());
        ordersServer.save(order);
        return R.ok().put("orderId", order.getId());
    }

    /**
     * 取消订单
     *
     * @param id
     * @return
     */
    @GetMapping("/orders/cancel")
    public R ordersCancel(int id) {
        Orders orders = ordersServer.getById(id);
        orders.setStatus(-1);
        orders.setUpdateAt(new Date());
        ordersServer.updateById(orders);
        return R.ok();
    }

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    @GetMapping("/orders/delete")
    public R ordersDelete(int id) {
        ordersServer.removeById(id);
        return R.ok();
    }

    /**
     * 生成约课订单
     *
     * @param appointmentApi
     * @return
     */
    @PostMapping("/appointment/create")
    public R appointmentSave(@RequestBody AppointmentApi appointmentApi) {
        Appointment appointment = new Appointment();
        appointment.setUserId(appointmentApi.getUserId());
        appointment.setProduct(appointmentApi.getProduct());
        appointment.setTime(appointmentApi.getTime());
        appointment.setPhone(appointmentApi.getPhone());
        appointment.setRemarks(appointmentApi.getRemarks());
        appointment.setState(0);
        appointment.setCreateAt(new Date());
        appointmentServer.save(appointment);
        return R.ok();
    }
}
