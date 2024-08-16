package com.example.mywork;

import com.example.mywork.answer.AnswerService;
import com.example.mywork.question.QuestionService;
import com.example.mywork.user.SiteUser;
import com.example.mywork.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyworkApplicationTests {

    @Autowired
    private  QuestionService questionService;
    @Autowired
    private  UserService userService;
    @Autowired
    private AnswerService answerService;
    @Test
    void contextLoads() {
        for (int i= 1;i<=20;i++){
            SiteUser siteUser=this.userService.getUser("abcd");
            String subject=String.format("테스트 데이터입니다[%03d]",i);
            String content="내용무";
            String category="국어";
            this.questionService.create(subject,content,category,siteUser);
        }
    }

}
