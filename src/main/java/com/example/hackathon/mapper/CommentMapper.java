package com.example.hackathon.mapper;

import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.entities.Comment;

import java.util.List;

public interface CommentMapper {
    List<CommentResponse> toDtos(List<Comment> comments);

    CommentResponse toDto(Comment comment);
}
