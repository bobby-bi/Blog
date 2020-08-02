package com.bi.dao;


import com.bi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    int saveTag(Tag tag);

    Tag getTag(Long id);
    List<Tag> listTag();
    int updateTag(@Param("id") Long id, @Param("tag") Tag tag);
    int deleteTag(Long id);
    Tag getTagByName(String name);
}
