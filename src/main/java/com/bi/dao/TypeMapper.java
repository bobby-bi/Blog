package com.bi.dao;

import com.bi.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {
    int saveType(Type type);

    Type getType(Long id);
    List<Type> listType();
    int updateType(@Param("id")Long id,@Param("type") Type type);
    int deleteType(Long id);
    Type getTypeByName(String name);

}
