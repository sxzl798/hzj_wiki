package com.hzj.wiki.service;

import com.hzj.wiki.domain.User;
import com.hzj.wiki.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User list(){
        return userMapper.selectByPrimaryKey(1);
    }
}
