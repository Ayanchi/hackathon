package com.example.hackathon.mapper.impl;

import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.entities.Comment;
import com.example.hackathon.mapper.CommentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public List<CommentResponse> toDtos(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment: comments){
            commentResponses.add(toDto(comment));
        }
        return commentResponses;
    }

    @Override
    public CommentResponse toDto(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentedTime(comment.getCommentedTime());
        commentResponse.setId(comment.getId());
        commentResponse.setSenderEmail(comment.getEmailOfSender());
        commentResponse.setSenderFirstname(comment.getFirstnameOfSender());
        commentResponse.setSenderLastname(comment.getLastnameOfSender());
        commentResponse.setComment(comment.getComment());
        commentResponse.setLikeCount(comment.getLikeCount());
        return commentResponse;
    }
}
