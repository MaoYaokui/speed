package com.tasi.speed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tasi.speed.common.TokenGenerator;
import com.tasi.speed.common.query.R;
import com.tasi.speed.mapper.UserTokenMapper;
import com.tasi.speed.model.UserToken;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName UserTokenServer
 * @Description TODO 用户Token
 * @Author myk
 * @Date 2020/2/25 1:38
 * @Version 1.0
 **/
@Service
public class UserTokenServer extends ServiceImpl<UserTokenMapper, UserToken> {

    /**
     * 生成token
     *
     * @param id 用户ID
     * @return
     */
    public R createToken(int id) {
        //12小时后过期
        int expire = 3600 * 12;
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + expire * 1000);
        //判断是否生成过token
        UserToken tokenEntity = this.getById(id);
        if (tokenEntity == null) {
            tokenEntity = new UserToken();
            tokenEntity.setId(id);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            this.updateById(tokenEntity);
        }
        return R.ok().put("token", token).put("expire", expire);
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    public UserToken getUser(String token) {
        return this.getOne(
                new QueryWrapper<UserToken>()
                        .eq("token", token)
        );
    }

    /**
     * 删除token
     *
     * @param token
     */
    public void deleteUser(String token) {
        this.removeById(getUser(token).getId());
    }
}
