package com.example.mywork.comment;

import com.example.mywork.question.Question;
import com.example.mywork.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Question question, String content, SiteUser siteUser){
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setQuestion(question);
        comment.setAuthor(siteUser);
        this.commentRepository.save(comment);
    }
}
