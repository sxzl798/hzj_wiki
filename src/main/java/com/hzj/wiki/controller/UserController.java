package com.hzj.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzj.wiki.req.UserLoginReq;
import com.hzj.wiki.req.UserQueryReq;
import com.hzj.wiki.req.UserResetPasswordReq;
import com.hzj.wiki.req.UserSaveReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.resp.UserLoginResp;
import com.hzj.wiki.resp.UserQueryResp;
import com.hzj.wiki.service.UserService;
import com.hzj.wiki.util.SnowFlake;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SnowFlake snowFlake;
    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req)
    {
        CommonResp<PageResp<UserQueryResp>> commonResp = new CommonResp<>();
        PageResp<UserQueryResp> userList = userService.list(req);
        commonResp.setContent(userList);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq saveReqreq)
    {
        saveReqreq.setPassword(DigestUtils.md5DigestAsHex(saveReqreq.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp<>();
        userService.save(saveReqreq);
        return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp commonResp = new CommonResp<>();
        userService.delete(id);
        return commonResp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req)
    {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp<>();
        userService.resetPassword(req);
        return commonResp;
    }

    @PostMapping("/login")
    public CommonResp<UserLoginResp> login(@Valid @RequestBody UserLoginReq req)
    {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> commonResp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token:{}，并放入redis中",token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp),3600*24, TimeUnit.SECONDS);
//        JSONObject.toJSONString(

        commonResp.setContent(userLoginResp);
        return commonResp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token)
    {
        CommonResp commonResp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token：{}",token);
        return commonResp;
    }


}
