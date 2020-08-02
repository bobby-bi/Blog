package com.bi.service;

import com.bi.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);
    int saveComment(Comment comment);

}
