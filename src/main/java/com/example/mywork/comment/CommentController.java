package com.example.mywork.comment;

import com.example.mywork.question.Question;
import com.example.mywork.question.QuestionService;
import com.example.mywork.user.SiteUser;
import com.example.mywork.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final QuestionService questionService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public String list(Model model, @PathVariable("id")Integer id,CommentForm commentForm){
        Question question=this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "comment_list";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "comment_list";
        }
        commentService.create(question, commentForm.getContent(), siteUser);
        return String.format("redirect:/comment/%s", id);
    }

}
