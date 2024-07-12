package com.deepon.mediasharingapp.service;

import com.deepon.mediasharingapp.dto.UserDto;
import com.deepon.mediasharingapp.exception.StoryException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Story;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.repository.StoryRepository;
import com.deepon.mediasharingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService{

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());
        user.getStories().add(story);

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();
        if(stories.isEmpty()){
            throw new StoryException("This User doesn't have any stories");
        }
        return stories;
    }
}
