package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.PageUtils;
import com.tasi.speed.common.query.Query;
import com.tasi.speed.dto.UserForm;
import com.tasi.speed.mapper.UserMapper;
import com.tasi.speed.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO 用户管理
 * @Author myk
 * @Date 2020/2/12 23:34
 * @Version 1.0
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    /**
     * 获取用户信息
     *
     * @param userForm
     * @return
     */
    public User login(UserForm userForm) {
        return this.getOne(
                new QueryWrapper<User>()
                        .eq("name", userForm.getUsername())
                        .eq("password", userForm.getPassword())
        );
    }

    /**
     * 获取用户列表
     *
     * @param params
     * @return
     */
    public PageUtils queryPage(Map<String, Object> params) {
        // 身份 管理员：0 非管理员：1
        Object identity = params.get("identity");
        Object type = params.get("type");
        Object name = params.get("name");
        Object nickName = params.get("nickName");
        IPage<User> page = this.page(
                new Query<User>().getPage(params),
                new QueryWrapper<User>()
                        .eq(!CommonFunction.isnull(identity) && "0".equals(identity.toString()), "type", 0)
                        .ne(!CommonFunction.isnull(identity) && "1".equals(identity.toString()), "type", 0)
                        .eq(!CommonFunction.isnull(type), "type", type)
                        .like(!CommonFunction.isnull(name), "name", name)
                        .like(!CommonFunction.isnull(nickName), "nick_name", nickName)
        );
        return new PageUtils(page);
    }

    /**
     * 添加用户
     *
     * @param user 用户
     */
    public void insert(User user) {
        user.setPassword("123456");
        user.setCreateAt(new Date());
        this.save(user);
    }

    /**
     * 查询用户是否存在
     *
     * @param name
     * @return
     */
    public boolean queryPresence(String name) {
        User user = this.getOne(
                new QueryWrapper<User>()
                        .eq("name", name)
        );
        return user != null;
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        user.setUpdateAt(new Date());
        this.updateById(user);
    }

    /**
     * 重置密码
     *
     * @param id
     */
    public void reset(String id, String name) {
        User user = this.getById(id);
        user.setPassword("123456");
        user.setUpdateCode(name);
        user.setUpdateAt(new Date());
        this.updateById(user);
    }

    /**
     * 修改密码
     *
     * @param user
     */
    public void updatePassword(User user) {
        String password = user.getPassword();
        user = this.getById(user.getId());
        user.setPassword(password);
        user.setUpdateAt(new Date());
        this.updateById(user);
    }
}
