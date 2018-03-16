package com.lee.dao;

import com.lee.entity.User;

public interface UserDao {

public int insert(User user);
    
    public int deleteById(Integer id);
    
    public int update(User user);
    
    public User getById(Integer id);
    
}
