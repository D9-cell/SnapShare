package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.exception.StoryException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Story;

import java.util.List;

public interface StoryService {
    Story createStory(Story story,Integer userId) throws UserException;
    List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}
