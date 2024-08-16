package com.example.mywork.user;

import com.example.mywork.answer.Answer;
import com.example.mywork.answer.AnswerService;
import com.example.mywork.question.Question;
import com.example.mywork.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "signup_form";
        }catch (Exception e){
            e.printStackTrace();;
            bindingResult.reject("signupFailed",e.getMessage());
            return "signup_form";
        }
        return "redirect:/";

    }
    @GetMapping("/login")
    public String login(){
        return "login_form";
    }

    @GetMapping("/profile")
    public String profile(Principal principal){
        String username=principal.getName();
        return String.format("redirect:/user/profile/%s",username);
    }
    @GetMapping("/profile/{username}")
    public String profile(Model model, @PathVariable("username") String username){
        SiteUser user =this.userService.getUser(username);
        Page<Question> questionList = this.questionService.getListByUser(user);
        Page<Answer> answerList = this.answerService.getListByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("questionList",questionList);
        model.addAttribute("answerList",answerList);
        return "user_profile";
    }
}
