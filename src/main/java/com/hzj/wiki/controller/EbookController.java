package com.hzj.wiki.controller;

import com.hzj.wiki.domain.Ebook;
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
    public List<Ebook> list(){return ebookService.list();}
}
