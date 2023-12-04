package com.hzj.wiki.service;

import com.hzj.wiki.domain.User;
import com.hzj.wiki.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public List<User> list(){
        return userMapper.list();
    }
}
