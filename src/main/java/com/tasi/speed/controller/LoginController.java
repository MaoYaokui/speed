package com.tasi.speed.controller;

import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.UserForm;
import com.tasi.speed.model.User;
import com.tasi.speed.model.UserToken;
import com.tasi.speed.service.UserService;
import com.tasi.speed.service.UserTokenServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO 登录
 * @Author maoyaokui
 * @Date 2020/3/27 下午8:33
 * @Version 1.0
 **/
@RestController
public class LoginController extends PublicTools {
    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenServer userTokenServer;

    /**
     * 登录
     *
     * @param userForm 用户表单
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody UserForm userForm) {
        User user = userService.login(userForm);
        if (user == null) {
            return R.error("账号或密码不正确");
        } else {
            return userTokenServer.createToken(user.getId());
        }
    }

    /**
     * 用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    public R info(String token) {
        UserToken userToken = userTokenServer.getUser(token);
        if (CommonFunction.isnull(userToken)) {
            return R.error();
        }
        User user = userService.getById(userToken.getId());
        return R.ok().put("user", user);
    }

    /**
     * 用户退出登录
     *
     * @param token
     * @return
     */
    @RequestMapping("/logout")
    public R logout(String token) {
        userTokenServer.deleteUser(token);
        return R.ok();
    }
}
