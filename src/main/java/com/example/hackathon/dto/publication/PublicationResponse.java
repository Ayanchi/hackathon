package com.example.hackathon.dto.publication;


import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.dto.file.FileDataResponse;
import com.example.hackathon.dto.person.PersonResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublicationResponse {
    private Long petitionId;
    private FileDataResponse fileDataResponse;
    private PersonResponse personResponse;
    private String name;
    private String description;
    private List<CommentResponse> comments;
    private Long countSign;
    private String date;
    private Long goal;
}
