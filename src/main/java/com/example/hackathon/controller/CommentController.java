package com.example.hackathon.controller;

import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.service.CommentService;
import com.example.hackathon.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
    private final PublicationService publicationService;
    private final CommentService commentService;

        @PostMapping("/comment/toPublication/{publicationId}")
        public void commentToPetition(@RequestHeader("Authorization") String token, @PathVariable Long publicationId,
                                      @RequestBody(required = false) String comment){
            System.out.println("\n\ncomment:" + comment);
            publicationService.commentToPetition(token, publicationId, comment);
        }

    @PostMapping("/like/{commentId}")
    public void likeComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId){
        commentService.likeTheComment(token, commentId);
    }

    @GetMapping("/getByPublicationId/{publicationId}")
    public List<CommentResponse> publicationId(
                                                 @PathVariable Long publicationId){
        return commentService.getbyPublicationId(publicationId);
    }

}
