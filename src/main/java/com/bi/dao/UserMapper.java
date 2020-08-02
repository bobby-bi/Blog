package com.bi.dao;

import com.bi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
   public User findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
   public User findById(Long id);
}
