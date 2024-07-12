package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.dto.UserDto;
import com.deepon.mediasharingapp.exception.CommentException;
import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Comments;
import com.deepon.mediasharingapp.model.Post;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.repository.CommentRepository;
import com.deepon.mediasharingapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Comments createComment(Comments comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());
        Comments createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);
        postRepository.save(post);
        return createdComment;
    }

    @Override
    public Comments findCommentById(Integer commentId) throws CommentException {
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        }
        throw new CommentException("Comment not found with id " + commentId);
    }

    @Override
    public Comments likeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user = userService.findUserById(userId);
        Comments comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().add(userDto);


        return commentRepository.save(comment);
    }

    @Override
    public Comments unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findUserById(userId);
        Comments comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikedByUsers().remove(userDto);


        return commentRepository.save(comment);
    }
}
