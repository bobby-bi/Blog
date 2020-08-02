package com.bi.service;

import com.bi.entity.Type;
import com.bi.util.Page;

import java.util.List;


public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);
    Page<Type> listType(String cur);
    int updateType(Long id,Type type);
    int deleteType(Long id);
    Type getTypeByName(String name);
    List<Type> listTypeTop(Integer size);
}
