package com.deepon.mediasharingapp.controller;

import com.deepon.mediasharingapp.exception.StoryException;
import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.Story;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.service.StoryService;
import com.deepon.mediasharingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Story createStory = storyService.createStory(story, user.getId());
        return new ResponseEntity<>(createStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {
        List<Story> stories = storyService.findStoryByUserId(userId);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }


}
