package com.hzj.wiki.service;

import com.hzj.wiki.domain.Ebook;
import com.hzj.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

@Service
public class EbookService {

    private EbookMapper ebookMapper;
    public Ebook list(){
        return ebookMapper.selectByPrimaryKey(1l);
    }
}
