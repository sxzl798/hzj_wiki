package com.hzj.wiki.mapper;

import com.hzj.wiki.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    public List<User> list();
}
