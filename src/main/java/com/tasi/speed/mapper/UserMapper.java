package com.tasi.speed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tasi.speed.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO 用户管理
 * @Author myk
 * @Date 2020/2/12 23:34
 * @Version 1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
