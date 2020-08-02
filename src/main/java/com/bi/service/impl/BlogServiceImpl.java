package com.bi.service.impl;

import com.bi.NotFoundException;
import com.bi.dao.BlogMapper;
import com.bi.dao.CommentMapper;
import com.bi.entity.Blog;
import com.bi.service.BlogService;
import com.bi.util.BlogQuery;
import com.bi.util.MarkdownUtils;
import com.bi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogMapper.getBlog(id);
        if(blog==null){
            throw new NotFoundException("博客不存在");
        }
        Integer views = blog.getViews();
        views++;
        blogMapper.updateBlogView(id,views);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));

        return blog;
    }

    @Override
    public Page<Blog> listBlog(String cur, BlogQuery blog) {
        Page<Blog> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Blog> blogs = blogMapper.listBlog(blog);
        List<Blog> blogs1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=blogs.size()){
            blogs1 = blogs.subList(index * size, blogs.size());
            page.setLast(true);
        }else {
            blogs1 = blogs.subList(index * size, index * size + size);
            page.setLast(false);
        }

        page.setContent(blogs1);
        return page;
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        int i=0;
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            i=blogMapper.saveBlog(blog);
            blogMapper.saveBlogTags(blog.getId(),blog.getTags());
        }else {
            blog.setUpdateTime(new Date());
            i=updateBlog(blog.getId(),blog);
            blogMapper.deleteBlogTags(blog.getId());
            blogMapper.saveBlogTags(blog.getId(),blog.getTags());
        }
        return i;
    }

    @Transactional
    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog blog1 = blogMapper.getBlog(id);
        if(blog1==null){
            throw  new NotFoundException("该博客不存在");
        }
        int i = blogMapper.updateBlog(id, blog);

        return i;
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
        commentMapper.deleteCommentByBlog(id);
        blogMapper.deleteBlogTags(id);

    }
    @Override
    public Page<Blog> listBlog(String cur) {
        Page<Blog> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Blog> blogs = blogMapper.listPublishedBlog();
        List<Blog> blogs1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=blogs.size()){
            blogs1 = blogs.subList(index * size, blogs.size());
            page.setLast(true);
        }else {
            blogs1 = blogs.subList(index * size, index * size + size);
            page.setLast(false);
        }

        page.setContent(blogs1);
        return page;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        List<Blog> blogs = blogMapper.listRecommendeBlog();
        blogs.sort(new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return o2.getUpdateTime().compareTo(o1.getUpdateTime());
            }
        });
        if(blogs.size()>size){
            blogs.subList(0,size);
        }
        return blogs;
    }

    @Override
    public Page<Blog> listBlog(String cur, String query) {
        Page<Blog> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Blog> blogs = blogMapper.listQueryBlog(query);
        List<Blog> blogs1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=blogs.size()){
            blogs1 = blogs.subList(index * size, blogs.size());
            page.setLast(true);
        }else {
            blogs1 = blogs.subList(index * size, index * size + size);
            page.setLast(false);
        }

        page.setContent(blogs1);
        return page;
    }

    @Override
    public Page<Blog> listBlogByTag(String cur, Long tag_id) {
        Page<Blog> page=new Page<>();
        page.setNumber(cur);
        if(cur.equals("0"))
            page.setFirst(true);
        else
            page.setFirst(false);
        List<Blog> blogs = blogMapper.tagblogs(tag_id);
        List<Blog> blogs1=null;
        int index=Integer.parseInt(cur);
        int size=Integer.parseInt(page.getSize());
        if(index * size + size>=blogs.size()){
            blogs1 = blogs.subList(index * size, blogs.size());
            page.setLast(true);
        }else {
            blogs1 = blogs.subList(index * size, index * size + size);
            page.setLast(false);
        }

        page.setContent(blogs1);
        return page;
    }
}
