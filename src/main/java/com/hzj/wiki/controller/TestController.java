package com.hzj.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //测试配置项
    @Value("${test.hello:Test}")
    private String testHello;

    //http://127.0.0.1:8080/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello World"+testHello;
    }

    @RequestMapping(value = "/hello/post",method = RequestMethod.POST)
    public  String helloPost(String name){
        return "Hello World Post,"+name;
    }
}
