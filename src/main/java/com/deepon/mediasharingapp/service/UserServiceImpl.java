package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.dto.UserDto;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.repository.UserRepository;
import com.deepon.mediasharingapp.security.JwtTokenClaims;
import com.deepon.mediasharingapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) throws UserException {

        //Existing Email Checking and if found then throwing exception
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist.isPresent()){
            throw new UserException("Email already exists");
        }

        //Existing UserName Checking and if found then throwing exception
        Optional<User> isUsernameExist = userRepository.findByEmail(user.getUsername());
        if(isUsernameExist.isPresent()){
            throw new UserException("Username already exists");
        }

        //Checking the necessary details that must be included during register of the User
        if(user.getEmail() == null || user.getPassword() == null || user.getUsername() == null || user.getName() == null){
            throw new UserException("All fields are required");
        }

        //Creating new user to save in the database
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //Saving it to the database
        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found with id " + userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token = token.substring(7);//"Bearer "....
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
        String email = jwtTokenClaims.getUsername();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("Invalid Token");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found with username " + username);
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);//Finding the User who requested to follow
        User followUser = findUserById(followUserId);//Finding the User who is to be followed.

        //Creating UserDto of request User instead of full User to store only required
        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        //Creating UserDto of following User instead of full User to store only required
        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        following.setUserImage(followUser.getImage());

        reqUser.getFollowing().add(following);
        followUser.getFollowers().add(follower);

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You are following " + followUser.getUsername();
    }

    @Override
    public String unfollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);//Finding the User who requested to follow
        User followUser = findUserById(followUserId);//Finding the User who is to be followed.

        //Creating UserDto of request User instead of full User to store only required
        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        //Creating UserDto of following User instead of full User to store only required
        UserDto following = new UserDto();
        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        following.setUserImage(followUser.getImage());

        reqUser.getFollowing().remove(following);
        followUser.getFollowers().remove(follower);

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You  Unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) {
        return userRepository.findAllUsersByUserIds(userIds);
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findbyQuery(query);
        if(users.isEmpty()){
            throw new UserException("User not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if(updatedUser.getUsername() != null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getName() != null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getEmail() != null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getMobile() != null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getWebsite() != null){
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if(updatedUser.getBio() != null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getGender() != null){
            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getImage() != null){
            existingUser.setImage(updatedUser.getImage());
        }
/*      if(updatedUser.getPassword() != null){
           existingUser.setPassword(updatedUser.getPassword());
        }*/
        if(updatedUser.getId().equals(existingUser.getId())){
            return userRepository.save(existingUser);
        }

        throw new UserException("You can't update this user");
    }
}
