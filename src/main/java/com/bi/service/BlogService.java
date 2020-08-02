package com.bi.service;

import com.bi.entity.Blog;
import com.bi.util.BlogQuery;
import com.bi.util.Page;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(String cur,BlogQuery blog);
    int saveBlog(Blog blog);
    int updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    Page<Blog> listBlog(String cur);
    List<Blog> listRecommendBlogTop(Integer size);
    Page<Blog> listBlog(String cur,String query);
    Page<Blog> listBlogByTag(String cur,Long tag_id);
}
