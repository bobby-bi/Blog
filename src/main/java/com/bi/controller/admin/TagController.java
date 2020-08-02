package com.bi.controller.admin;

import com.bi.entity.Tag;
import com.bi.entity.Type;
import com.bi.service.TagService;
import com.bi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String list(Model model, @RequestParam(value = "page",required =false) String page){
        if(page==null){
            page="0";
        }
        model.addAttribute("page",tagService.listTag(page));

        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            attributes.addFlashAttribute("message","标签已存在");
            return "redirect:/admin/tags/input";
        }

        int i= tagService.saveTag(tag);
       if(i==0){
           attributes.addFlashAttribute("message","新增失败");
       }else {
           attributes.addFlashAttribute("message","新增成功");
       }

        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag,@PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            attributes.addFlashAttribute("message","标签已存在");
            return "redirect:/admin/tags/"+id+"/input";
        }

        int i= tagService.updateTag(id,tag);
        if(i==0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int i = tagService.deleteTag(id);
        if(i==0){
            attributes.addFlashAttribute("message","删除失败");
        }else {
            attributes.addFlashAttribute("message","删除成功");
        }
        return "redirect:/admin/tags";
    }
}
