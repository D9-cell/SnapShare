package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.dto.UserDto;
import com.deepon.mediasharingapp.exception.PostException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Post;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.repository.PostRepository;
import com.deepon.mediasharingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Post createPost(Post post,Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        post.setUser(userDto);
        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = postRepository.findById(postId).get();
        User user = userService.findUserById(userId);
        if(post.getUser().getId().equals(user.getId())){
            postRepository.deleteById(post.getId());
            return "Post Deleted Successfully";
        }
        throw new PostException("You do not have permission to delete this post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.isEmpty()){
            throw new UserException("No posts found");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            return optionalPost.get();
        }
        throw new PostException("No post found with Id : " + postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if(posts.isEmpty()){
            throw new UserException("No Posts Available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post Saved Successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post Removed Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();

        userDto.setId(userId);
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        post.getLikedUsers().add(userDto);
        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();

        userDto.setId(userId);
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        post.getLikedUsers().remove(userDto);
        return postRepository.save(post);
    }

    private UserDto getUserDtoByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();

        userDto.setId(userId);
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        return userDto;
    }
}
