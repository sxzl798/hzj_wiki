package com.hzj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzj.wiki.domain.User;
import com.hzj.wiki.domain.UserExample;
import com.hzj.wiki.mapper.UserMapper;
import com.hzj.wiki.req.UserQueryReq;
import com.hzj.wiki.req.UserSaveReq;
import com.hzj.wiki.resp.UserQueryResp;
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
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;
    public PageResp<UserQueryResp> list(UserQueryReq req){

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());


        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
    /*
    保存
     */
    public void save(UserSaveReq saveReq){
        User user = CopyUtil.copy(saveReq,User.class);
        if (ObjectUtils.isEmpty(saveReq.getId())){
            //新增

            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        }else{
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }
}
