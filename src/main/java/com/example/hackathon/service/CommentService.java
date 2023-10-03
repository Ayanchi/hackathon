package com.example.hackathon.service;

import com.example.hackathon.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Boolean likeTheComment(String token, Long commentId);

    List<CommentResponse> getbyPublicationId(Long publicationId);

    void delete(Long commentId);
}
