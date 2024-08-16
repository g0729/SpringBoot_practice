package com.example.mywork.answer;

import jakarta.validation.constraints.NotEmpty;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class AnswerForm {
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
