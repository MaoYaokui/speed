package com.tasi.speed.controller;

import com.tasi.speed.common.ValidatorUtils;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.R;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.UserForm;
import com.tasi.speed.model.User;
import com.tasi.speed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO 用户
 * @Author maoyaokui
 * @Date 2020/3/28 下午11:50
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController extends PublicTools {
    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/get")
    public R get(int id) {
        User user = userService.getById(id);
        return R.ok().put("user", user);
    }

    /**
     * 新增
     *
     * @param user
     * @return
     */
    @PostMapping("/create")
    public R create(@RequestBody User user) {
        ValidatorUtils.validateEntity(user, User.class);
        boolean presence = userService.queryPresence(user.getName());
        if (presence) {
            return R.error("此用户已存在");
        }
        user.setCreateAt(new Date());
        userService.insert(user);
        return R.ok().put("user", user);
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody User user) {
        ValidatorUtils.validateEntity(user, User.class);
        userService.update(user);
        return R.ok();
    }

    /**
     * 重置密码
     *
     * @param id
     */
    @RequestMapping("/reset")
    public R reset(@RequestParam Map<String, String> params) {
        userService.reset(params.get("id"), params.get("name"));
        return R.ok();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody User user) {
        ValidatorUtils.validateEntity(user, User.class);
        userService.updatePassword(user);
        return R.ok();
    }
}
