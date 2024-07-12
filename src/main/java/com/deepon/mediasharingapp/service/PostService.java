package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Post;

import java.util.List;

public interface PostService {
    public Post createPost(Post post,Integer userId) throws UserException;
    public String deletePost(Integer postId,Integer userId) throws UserException, PostException;
    public List<Post> findPostByUserId(Integer userId) throws UserException;
    public Post findPostById(Integer postId) throws PostException;
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException,UserException;

    /** Services for saving and Unsaving Post */
    public String savedPost(Integer postId,Integer userId) throws UserException, PostException;
    public String unSavedPost(Integer postId,Integer userId) throws UserException, PostException;
    /** Services for Liking and UnLiking Post */
    public Post likePost(Integer postId,Integer userId) throws UserException, PostException;
    public Post unLikePost(Integer postId,Integer userId) throws UserException, PostException;
}
