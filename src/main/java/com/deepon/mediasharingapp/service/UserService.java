package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService  {
    User registerUser(User user) throws UserException;
    User findUserById(Integer userId) throws UserException;
    User findUserProfile(String token) throws UserException;
    User findUserByUsername(String username) throws UserException;
    String followUser(Integer reqUserId,Integer followUserId) throws UserException;

    String unfollowUser(Integer reqUserId,Integer followUserId) throws UserException;
    List<User> findUserByIds(List<Integer> userIds) throws UserException;
    List<User> searchUser(String query) throws UserException;
    User updateUserDetails(User updatedUser,User existingUser) throws UserException;
}
