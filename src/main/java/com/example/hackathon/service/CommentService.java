package com.example.hackathon.service;

import com.example.hackathon.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    void likeTheComment(String token, Long commentId);

    List<CommentResponse> getbyPetitionId(Long petitionId);
}
