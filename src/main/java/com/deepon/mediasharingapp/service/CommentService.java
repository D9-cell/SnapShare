package com.deepon.mediasharingapp.service;


import com.deepon.mediasharingapp.exception.CommentException;
import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Comments;

public interface CommentService {
    Comments createComment(Comments comment, Integer postId, Integer userId) throws UserException, PostException;
    Comments findCommentById(Integer commentId) throws CommentException;
    Comments likeComment(Integer commentId, Integer userId) throws CommentException,UserException;
    Comments unlikeComment(Integer commentId, Integer userId) throws CommentException,UserException;
}
