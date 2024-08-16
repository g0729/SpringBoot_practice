package com.example.mywork;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


import com.example.mywork.answer.Answer;
import com.example.mywork.comment.CommentService;
import com.example.mywork.question.Question;
import com.example.mywork.answer.AnswerRepository;
import com.example.mywork.question.QuestionRepository;
import com.example.mywork.question.QuestionService;
import com.example.mywork.user.SiteUser;
import com.example.mywork.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MyWorkApplicationTests {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;


    @Test
    void testJPA() {
        for (int i = 1; i <= 10; i++) {

        }

    }
}