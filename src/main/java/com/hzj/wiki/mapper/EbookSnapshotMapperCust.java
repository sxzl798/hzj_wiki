package com.hzj.wiki.mapper;

import com.hzj.wiki.resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    public void genSnapshot();
    List<StatisticResp> getStatistic();
}
