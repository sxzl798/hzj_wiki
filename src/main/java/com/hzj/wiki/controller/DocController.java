package com.hzj.wiki.controller;

import com.hzj.wiki.req.DocQueryReq;
import com.hzj.wiki.req.DocSaveReq;
import com.hzj.wiki.resp.CommonResp;
import com.hzj.wiki.resp.DocQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.service.DocService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;
    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req)
    {
        CommonResp<PageResp<DocQueryResp>> commonResp = new CommonResp<>();
        PageResp<DocQueryResp> docList = docService.list(req);
        commonResp.setContent(docList);
        return commonResp;
    }
    @GetMapping("/all")
    public CommonResp all()
    {
        CommonResp<List<DocQueryResp>> commonResp = new CommonResp<>();
        List<DocQueryResp> docList = docService.all();
        commonResp.setContent(docList);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq saveReqreq)
    {
        CommonResp commonResp = new CommonResp<>();
        docService.save(saveReqreq);
        return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp commonResp = new CommonResp<>();
        docService.delete(id);
        return commonResp;
    }


}