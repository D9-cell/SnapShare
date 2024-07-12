package com.deepon.mediasharingapp.model;


import com.deepon.mediasharingapp.dto.UserDto;
import jakarta.persistence.*;
import com.deepon.mediasharingapp.model.Comments;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id",column = @Column(name = "user_id")),
            @AttributeOverride(name="email",column = @Column(name = "user_email"))
    })
    private UserDto user;
    @OneToMany
    private List<Comments> comments = new ArrayList<>();

    @Embedded
    @ElementCollection
    @JoinTable(name = "likedUsers",joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedUsers = new HashSet<>();

    public Post(Integer id, String caption, String image, String location, LocalDateTime createdAt, UserDto user, List<Comments> comments, Set<UserDto> likedUsers) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.location = location;
        this.createdAt = createdAt;
        this.user = user;
        this.comments = comments;
        this.likedUsers = likedUsers;
    }

    public Post() {  }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
    public List<Comments> getComments() {
        return comments;
    }
    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
    public Set<UserDto> getLikedUsers() {
        return likedUsers;
    }
    public void setLikedUsers(Set<UserDto> likedUsers) {
        this.likedUsers = likedUsers;
    }
}

/*
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;

    @OneToMany
    private List<Comments> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "liked_users", joinColumns = @JoinColumn(name = "post_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private Set<UserDto> likedUsers = new HashSet<>();

    public Post() {}

    public Post(Integer id, String caption, String image, String location, LocalDateTime createdAt, UserDto user, List<Comments> comments, Set<UserDto> likedUsers) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.location = location;
        this.createdAt = createdAt;
        this.user = user;
        this.comments = comments;
        this.likedUsers = likedUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Set<UserDto> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<UserDto> likedUsers) {
        this.likedUsers = likedUsers;
    }
}*/

