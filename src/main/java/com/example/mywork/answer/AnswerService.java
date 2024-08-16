package com.example.mywork.answer;

import com.example.mywork.DataNotFoundException;
import com.example.mywork.question.Question;
import com.example.mywork.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);
        question.setAnswerNum(question.getAnswerNum()+1);
        siteUser.setAnswernum(siteUser.getAnswernum()+1);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);

        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser){
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }

    public Page<Answer> getList(int page,Question question){
        List<Sort.Order> sorts=new ArrayList<>();
        sorts.add(Sort.Order.by("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.answerRepository.findAllByQuestion(pageable,question);
    }

    public Page<Answer> getListByUser(SiteUser siteUser){
        List<Sort.Order> sorts=new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0,10,Sort.by(sorts));
        return this.answerRepository.findAllByAuthor(siteUser,pageable);
    }
}
