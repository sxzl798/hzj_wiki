package com.hzj.wiki.controller;

import com.hzj.wiki.req.EbookReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.EbookResp;
import com.hzj.wiki.service.EbookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")

public class EbookController {
    @Resource
    private EbookService ebookService;
    @GetMapping("/list")
    public CommonResp list(EbookReq req)
    {
        CommonResp<List<EbookResp>> commonResp = new CommonResp<>();
        List<EbookResp> ebookList = ebookService.list(req);
        commonResp.setContent(ebookList);
        return commonResp;
    }
}
