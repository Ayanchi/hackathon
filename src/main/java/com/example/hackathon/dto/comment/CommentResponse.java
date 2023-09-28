package com.example.hackathon.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String senderFirstname;
    private String senderLastname;
    private String senderEmail;
    private String comment;
    private String commentedTime;
    private Integer likeCount;

}
