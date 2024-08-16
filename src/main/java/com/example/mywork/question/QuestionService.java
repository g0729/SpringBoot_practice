package com.example.mywork.question;

import com.example.mywork.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.example.mywork.answer.Answer;
import com.example.mywork.user.SiteUser;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;


    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, String category,SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(author);
        q.setView(0);
        q.setAnswerNum(0);
        q.setVoterNum(0);
        q.setCategory(category);
        author.setQuestionnum(author.getQuestionnum()+1);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page, String kw, String so,String ca) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (so.equals("recent")) {
            sorts.add(Sort.Order.desc("createDate"));
        } else if (so.equals("old")) {
            sorts.add(Sort.Order.asc("createDate"));
        } else if (so.equals("recommend")) {
            sorts.add(Sort.Order.desc("voterNum"));
        } else if (so.equals("popular")) {
            sorts.add(Sort.Order.desc("view"));
        } else if (so.equals("answerNum")) {
            sorts.add(Sort.Order.desc("answerNum"));
        }
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        if (ca.equals("all")){
         return this.questionRepository.findAllByKeyword(kw,pageable);
        }
        else {
            return this.questionRepository.findAllByKeyword(kw, ca, pageable);
        }
    }

    public Page<Question> getListByUser(SiteUser siteUser){
        List<Sort.Order> sorts =new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0,10,Sort.by(sorts));
        return this.questionRepository.findAllByAuthor(siteUser,pageable);
    }
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        question.setVoterNum(question.getVoter().size());
        this.questionRepository.save(question);
    }

    public void increaseView(Question question) {
        question.setView(question.getView() + 1);
        this.questionRepository.save(question);
    }
}
