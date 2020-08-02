package com.bi.controller;

import com.bi.entity.Type;
import com.bi.service.BlogService;
import com.bi.service.TypeService;
import com.bi.util.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(Model model, @RequestParam(value = "page",required =false) String page,@PathVariable Long id){
        if(page==null||page.equals("")){
            page="0";
        }
        List<Type> types = typeService.listTypeTop(10000);
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(page,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
