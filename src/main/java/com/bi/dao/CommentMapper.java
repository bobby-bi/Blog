package com.bi.dao;


import com.bi.entity.Comment;
import com.bi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    Comment getComment(Long id);
    List<Comment> getBlogComment(Long blogId);
    int saveComment(Comment comment);
    List<Comment> getBlogCommentAndParentCommentNull(Long blogId);
    void deleteCommentByBlog(Long blogId);
}
