package com.bi.service.impl;

import com.bi.NotFoundException;
import com.bi.dao.TypeMapper;
import com.bi.entity.Type;
import com.bi.service.TypeService;
import com.bi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;
    @Transactional
    @Override
    public int saveType(Type type) {
        int i = typeMapper.saveType(type);

        return i;
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(String cur) {
        Page<Type> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Type> types = typeMapper.listType();
        List<Type> types1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=types.size()){
            types1 = types.subList(index * size, types.size());
            page.setLast(true);
        }else {
           types1 = types.subList(index * size, index * size + size);
           page.setLast(false);
        }

        page.setContent(types1);
        return page;
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type type1 = typeMapper.getType(id);
        if(type1==null){
            throw new NotFoundException("不存在该类型");
        }


        return typeMapper.updateType(id,type);
    }
    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }
    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        List<Type> types = typeMapper.listType();
        types.sort(new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBlogs().size()-o1.getBlogs().size();
            }
        });
        if(types.size()>size){
            types=types.subList(0,size);
        }
        return types;
    }
}
