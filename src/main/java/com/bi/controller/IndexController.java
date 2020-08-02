package com.bi.controller;


import com.bi.NotFoundException;
import com.bi.entity.Tag;
import com.bi.service.BlogService;
import com.bi.service.TagService;
import com.bi.service.TypeService;
import com.bi.util.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "page",required =false) String page){
        if(page==null||page.equals("")){
            page="0";
        }
        model.addAttribute("page",blogService.listBlog(page));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlog",blogService.listRecommendBlogTop(8));
        return "index";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
    @PostMapping("/search")
    public String search(Model model, @RequestParam(value = "page",required =false) String page,@RequestParam("query") String query){
        if(page==null||page.equals("")){
            page="0";
        }
        model.addAttribute("page",blogService.listBlog(page,query));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
