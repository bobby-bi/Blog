package com.bi.controller.admin;

import com.bi.dao.TagMapper;
import com.bi.dao.TypeMapper;
import com.bi.entity.Blog;
import com.bi.entity.User;
import com.bi.service.BlogService;
import com.bi.service.TagService;
import com.bi.service.TypeService;
import com.bi.util.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    BlogService blogService;
    @Autowired
    TypeMapper typeMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @GetMapping("/blogs")
    public String list(Model model, @RequestParam(value = "page",required =false) String page,BlogQuery blog) {
        if(page==null||page.equals("")){
            page="0";
        }
        model.addAttribute("types",typeMapper.listType());
        model.addAttribute("page",blogService.listBlog(page,blog));
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(Model model, @RequestParam(value = "page",required =false) String page,BlogQuery blog) {
        if(page==null||page.equals("")){
            page="0";
        }
        model.addAttribute("page",blogService.listBlog(page,blog));
        return "admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model) {
       setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeMapper.listType());
        model.addAttribute("tags",tagMapper.listTag());
    }
    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model, @PathVariable Long id) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session,RedirectAttributes attributes){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTagByIds(blog.getTagIds()));
        int i = blogService.saveBlog(blog);
        if(i==0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
