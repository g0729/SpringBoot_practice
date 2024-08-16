package com.example.mywork.answer;

import com.example.mywork.question.Question;
import com.example.mywork.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Page<Answer> findAllByQuestion(Pageable pageable, Question question);
    Page<Answer> findAllByAuthor(SiteUser siteUser,Pageable pageable);
}
