package com.deepon.mediasharingapp.controller;

import com.deepon.mediasharingapp.exception.CommentException;
import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Comments;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.service.CommentService;
import com.deepon.mediasharingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @PostMapping("/create/{postId}")
    public ResponseEntity<Comments> createCommentHandler(@RequestBody Comments comment,@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        Comments createdComment = commentService.createComment(comment,postId,user.getId());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comments> likeCommentHandler(@RequestHeader("Authorization") String token,@PathVariable Integer commentId) throws UserException, CommentException {
        User user = userService.findUserProfile(token);
        Comments comment = commentService.likeComment(commentId, user.getId());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comments> unLikeCommentHandler(@RequestHeader("Authorization") String token,@PathVariable Integer commentId) throws UserException, CommentException {
        User user = userService.findUserProfile(token);
        Comments comment = commentService.unlikeComment(commentId, user.getId());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


}
