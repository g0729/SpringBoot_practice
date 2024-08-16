package com.example.mywork.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    String content;
}
