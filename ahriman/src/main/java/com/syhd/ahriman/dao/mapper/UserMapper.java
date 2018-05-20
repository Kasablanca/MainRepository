package com.syhd.ahriman.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.web.Pagination;
import com.syhd.ahriman.web.Sort;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> findAll(@Param("page") Pagination pagination, @Param("sort") Sort sort);
    
    long findAllCount();
    
    List<Map<?, ?>> userDistribution();
}