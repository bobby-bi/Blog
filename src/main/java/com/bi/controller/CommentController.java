package com.bi.controller;

import com.bi.entity.Comment;
import com.bi.entity.User;
import com.bi.service.BlogService;
import com.bi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avator}")
    private String avator;
    @GetMapping("/comments/{blogId}")
    public String commentList(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user!=null){
            comment.setAdminComment(true);
            comment.setAvator(user.getAvator());
        }else {
            comment.setAvator(avator);
            comment.setAdminComment(false);
        }
        comment.setBlog(blogService.getBlog(comment.getBlog().getId()));

        int i = commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }
}
