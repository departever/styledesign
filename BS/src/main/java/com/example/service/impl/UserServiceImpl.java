package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @ClassName: UserServiceImpl
 * @description: TODO
 * @author: xxxx
 * @create: 2021-10-19 09:39
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer checkLogin(String number, String password) {
        User user = this.getUserByNumber(number);
        if(ObjectUtils.isEmpty(user)){
            return -1;
        }else {
            if(password.equals(user.getPassword())){
                return 1;
            }else{
                return 0;
            }
        }
    }

    @Override
    public User getUserByNumber(String number) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("number",number);
        User user = this.userMapper.selectOne(wrapper);
        return user;
    }
}
