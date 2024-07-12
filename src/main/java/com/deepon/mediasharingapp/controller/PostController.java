package com.deepon.mediasharingapp.controller;

import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Post;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.response.MessageResponse;
import com.deepon.mediasharingapp.service.PostService;
import com.deepon.mediasharingapp.service.UserService;
import jakarta.persistence.AttributeOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post,@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all/{Id}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("Id") Integer userid) throws UserException {
        List<Post> posts = postService.findPostByUserId(userid);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{Ids}")
    public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable("Ids") List<Integer> userids) throws UserException, PostException {
        List<Post> posts = postService.findAllPostByUserIds(userids);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException {
            Post post = postService.findPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());
        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);
        Post unLikedPost = postService.unLikePost(postId, user.getId());
        return new ResponseEntity<>(unLikedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String  message = postService.deletePost(postId, user.getId());

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavedPostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }


}
