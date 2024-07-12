package com.deepon.mediasharingapp.model;

import com.deepon.mediasharingapp.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id",column = @Column(name = "user_id")),
            @AttributeOverride(name="email",column = @Column(name = "user_email"))
    })
    private UserDto user;

    @Embedded
    @ElementCollection
    private Set<UserDto> likedByUsers = new HashSet<>();

    private LocalDateTime createdAt;

    public Comments(Integer id, UserDto user, Set<UserDto> likedByUsers, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.likedByUsers = likedByUsers;
        this.createdAt = createdAt;
    }

    public Comments() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<UserDto> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<UserDto> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
