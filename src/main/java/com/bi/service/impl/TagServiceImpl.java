package com.bi.service.impl;

import com.bi.NotFoundException;
import com.bi.dao.TagMapper;
import com.bi.dao.TypeMapper;
import com.bi.entity.Tag;
import com.bi.entity.Type;
import com.bi.service.TagService;
import com.bi.service.TypeService;
import com.bi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagMapper tagMapper;
    @Transactional
    @Override
    public int saveTag(Tag tag) {
        int i = tagMapper.saveTag(tag);

        return i;
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }
    @Transactional
    @Override
    public Page<Tag> listTag(String cur) {
        Page<Tag> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Tag> tags = tagMapper.listTag();
        List<Tag> tags1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=tags.size()){
            tags1 = tags.subList(index * size, tags.size());
            page.setLast(true);
        }else {
           tags1 = tags.subList(index * size, index * size + size);
           page.setLast(false);
        }

        page.setContent(tags1);
        return page;
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag tag1 = tagMapper.getTag(id);
        if(tag1==null){
            throw new NotFoundException("不存在该类型");
        }


        return tagMapper.updateTag(id,tag);
    }
    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> listTagByIds(String ids) {
        List<Tag> list=new ArrayList<>();
        List<Long> longs = convertToList(ids);
        for(Long l: longs){
            list.add(getTag(l));
        }
        return list;
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        List<Tag> tags = tagMapper.listTag();
        tags.sort(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o2.getBlogs().size()-o1.getBlogs().size();
            }
        });
        if(tags.size()>size){
            tags=tags.subList(0,size);
        }
        return tags;
    }
}
