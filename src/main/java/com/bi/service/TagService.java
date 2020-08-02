package com.bi.service;

import com.bi.entity.Tag;
import com.bi.entity.Type;
import com.bi.util.Page;

import java.util.List;


public interface TagService {

    int saveTag(Tag tag);

    Tag getTag(Long id);
    Page<Tag> listTag(String cur);
    int updateTag(Long id, Tag tag);
    int deleteTag(Long id);
    List<Tag> listTagByIds(String ids);
    Tag getTagByName(String name);
    List<Tag> listTagTop(Integer size);
}
