package com.bi.controller.admin;

import com.bi.entity.Type;
import com.bi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String list(Model model, @RequestParam(value = "page",required =false) String page){
        if(page==null){
            page="0";
        }
        model.addAttribute("page",typeService.listType(page));

        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){
            attributes.addFlashAttribute("message","分类已存在");
            return "redirect:/admin/types/input";
        }

        int i= typeService.saveType(type);
       if(i==0){
           attributes.addFlashAttribute("message","新增失败");
       }else {
           attributes.addFlashAttribute("message","新增成功");
       }

        return "redirect:/admin/types";
    }
    @PostMapping("/types/{id}")
    public String editPost(Type type,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){
            attributes.addFlashAttribute("message","分类已存在");
            return "redirect:/admin/types/"+id+"/input";
        }

        int i= typeService.updateType(id,type);
        if(i==0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }

        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int i = typeService.deleteType(id);
        if(i==0){
            attributes.addFlashAttribute("message","删除失败");
        }else {
            attributes.addFlashAttribute("message","删除成功");
        }
        return "redirect:/admin/types";
    }
}
