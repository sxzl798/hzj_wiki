package com.hzj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzj.wiki.domain.Content;
import com.hzj.wiki.domain.Doc;
import com.hzj.wiki.domain.DocExample;
import com.hzj.wiki.exception.BusinessException;
import com.hzj.wiki.exception.BusinessExceptionCode;
import com.hzj.wiki.mapper.ContentMapper;
import com.hzj.wiki.mapper.DocMapper;
import com.hzj.wiki.mapper.DocMapperCust;
import com.hzj.wiki.req.DocQueryReq;
import com.hzj.wiki.req.DocSaveReq;
import com.hzj.wiki.resp.DocQueryResp;
import com.hzj.wiki.resp.PageResp;
import com.hzj.wiki.util.CopyUtil;
import com.hzj.wiki.util.RedisUtil;
import com.hzj.wiki.util.RequestContext;
import com.hzj.wiki.util.SnowFlake;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);
    @Resource
    private DocMapper docMapper;

    @Resource
    private DocMapperCust docMapperCust;
    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

    public PageResp<DocQueryResp> list(DocQueryReq req){

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort ASC");
        DocExample.Criteria criteria = docExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());


        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
    public List<DocQueryResp> all(Long ebookId){

        DocExample docExample = new DocExample();

        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort ASC");
        List<Doc> docList = docMapper.selectByExample(docExample);
        //列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }
    /*
    保存
     */
    public void save(DocSaveReq saveReq){
        Doc doc = CopyUtil.copy(saveReq, Doc.class);
        Content content = CopyUtil.copy(saveReq, Content.class);

        if (ObjectUtils.isEmpty(saveReq.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);
            content.setId(doc.getId());
            contentMapper.insert(content);
        }else{
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0){
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }
    public void delete(List<String> ids){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort ASC");
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public String findContent(Long id){
        Content content = contentMapper.selectByPrimaryKey(id);
        docMapperCust.increaseViewCount(id);
        //文档阅读数+1
        if (ObjectUtils.isEmpty(content)){
            return "";
        }else {
            return content.getContent();
        }

    }

    /**
     * 点赞
     */
    public void vote(Long id){
//        docMapperCust.increaseVoteCount(id);
        //远程IP+doc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddr();
        if (redisUtil.validateRepeat("DOC_VOTE_"+ id +"_" + ip,3600*24)){
            docMapperCust.increaseVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
    }
    public void updateEbookInfo(){
        docMapperCust.updateEbookInfo();
    }

}
