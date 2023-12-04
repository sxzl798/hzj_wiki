package com.hzj.wiki.controller;

import com.hzj.wiki.domain.User;
import com.hzj.wiki.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    //测试配置项
    @Value("${test.hello:Test}")
    private String testHello;

    @Resource
    private UserService userService;

    //http://127.0.0.1:8080/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello World"+testHello;
    }

    @RequestMapping(value = "/hello/post",method = RequestMethod.POST)
    public  String helloPost(String name){
        return "Hello World Post,"+name;
    }
    @GetMapping("/user/list")
    public List<User> list(){
        return userService.list();
    }
}
