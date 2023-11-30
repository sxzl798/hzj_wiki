package com.hzj.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    //http://127.0.0.1:8080/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = "/hello/post",method = RequestMethod.POST)
    public  String helloPost(String name){
        return "Hello World Post,"+name;
    }
}
