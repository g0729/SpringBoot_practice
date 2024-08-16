package com.example.mywork.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.mywork.answer.Answer;
import com.example.mywork.comment.Comment;
import com.example.mywork.user.SiteUser;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    private Integer answerNum;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    private String category;

    private Integer voterNum;

    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view;
}
