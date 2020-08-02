package com.bi.controller;

import com.bi.entity.Tag;
import com.bi.service.BlogService;
import com.bi.service.TagService;
import com.bi.service.TagService;
import com.bi.util.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(Model model, @RequestParam(value = "page",required =false) String page,@PathVariable Long id){
        if(page==null||page.equals("")){
            page="0";
        }
        List<Tag> tags = tagService.listTagTop(10000);
        if(id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlogByTag(page,id));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
