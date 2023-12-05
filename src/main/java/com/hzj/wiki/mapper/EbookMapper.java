package com.hzj.wiki.mapper;

import com.hzj.wiki.domain.Ebook;

public interface EbookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ebook row);

    int insertSelective(Ebook row);

    Ebook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ebook row);

    int updateByPrimaryKey(Ebook row);
}