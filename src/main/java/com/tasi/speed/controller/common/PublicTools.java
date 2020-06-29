package com.tasi.speed.controller.common;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PublicTools
 * @Description TODO 公共类
 * @Author myk
 * @Date 2020/2/13 13:23
 * @Version 1.0
 **/
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}
)
public class PublicTools {
    public Map<String, Object> queryMap = new HashMap<>();
}
