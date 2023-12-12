package com.hzj.wiki.controller;

import com.hzj.wiki.req.CategoryQueryReq;
import com.hzj.wiki.req.CategorySaveReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.CategoryQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req)
    {
        CommonResp<PageResp<CategoryQueryResp>> commonResp = new CommonResp<>();
        PageResp<CategoryQueryResp> categoryList = categoryService.list(req);
        commonResp.setContent(categoryList);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq saveReqreq)
    {
        CommonResp commonResp = new CommonResp<>();
        categoryService.save(saveReqreq);
        return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp commonResp = new CommonResp<>();
        categoryService.delete(id);
        return commonResp;
    }


}
