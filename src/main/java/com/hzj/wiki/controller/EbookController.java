package com.hzj.wiki.controller;

import com.hzj.wiki.req.EbookQueryReq;
import com.hzj.wiki.req.EbookSaveReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.EbookQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.service.EbookService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;
    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req)
    {
        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();
        PageResp<EbookQueryResp> ebookList = ebookService.list(req);
        commonResp.setContent(ebookList);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq saveReqreq)
    {
        CommonResp commonResp = new CommonResp<>();
        ebookService.save(saveReqreq);
        return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp commonResp = new CommonResp<>();
        ebookService.delete(id);
        return commonResp;
    }


}
