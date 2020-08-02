package com.bi.dao;

import com.bi.entity.Blog;
import com.bi.entity.Tag;
import com.bi.util.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogMapper {
    Blog getBlog(Long id);
    List<Blog> listBlog(BlogQuery blog);
    int saveBlog(Blog blog);
    int updateBlog(@Param("id")Long id,@Param("blog") Blog blog);
    int saveBlogTags(@Param("id")Long id, @Param("tags")List<Tag> tags);
    void deleteBlogTags(Long id);
    void deleteBlog(Long id);
    List<Blog> listAllBlog();
    List<Blog> listPublishedBlog();
    List<Blog> listRecommendeBlog();
    List<Blog> listQueryBlog(String query);
    int updateBlogView(@Param("id")Long id,@Param("view") int view);
    List<Blog> listBlogByType(Long type_id);
    List<Blog> tagblogs(Long tag_id);

}
