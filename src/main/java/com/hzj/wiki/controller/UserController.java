package com.hzj.wiki.controller;

import com.hzj.wiki.req.UserLoginReq;
import com.hzj.wiki.req.UserQueryReq;
import com.hzj.wiki.req.UserResetPasswordReq;
import com.hzj.wiki.req.UserSaveReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.UserLoginResp;
import com.hzj.wiki.resp.UserQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
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
        commonResp.setContent(userLoginResp);
        return commonResp;
    }


}
