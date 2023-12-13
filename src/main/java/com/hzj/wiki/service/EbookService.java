package com.hzj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzj.wiki.domain.Ebook;
import com.hzj.wiki.domain.EbookExample;
import com.hzj.wiki.mapper.EbookMapper;
import com.hzj.wiki.req.EbookQueryReq;
import com.hzj.wiki.req.EbookSaveReq;
import com.hzj.wiki.resp.EbookQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.util.CopyUtil;
import com.hzj.wiki.util.SnowFlake;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);
    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;
    public PageResp<EbookQueryResp> list(EbookQueryReq req){

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
        if (!ObjectUtils.isEmpty(req.getCategory2Id())){
            criteria.andCategory2IdEqualTo(req.getCategory2Id());
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId())){
            criteria.andCategoryIdEqualTo(req.getCategoryId());
        }

        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());


        List<EbookQueryResp> respList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
    /*
    保存
     */
    public void save(EbookSaveReq saveReq){
        Ebook ebook = CopyUtil.copy(saveReq,Ebook.class);
        if (ObjectUtils.isEmpty(saveReq.getId())){
            //新增

            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }else{
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id){
        ebookMapper.deleteByPrimaryKey(id);
    }
}
