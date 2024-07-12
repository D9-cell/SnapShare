package com.deepon.mediasharingapp.repository;

import com.deepon.mediasharingapp.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    @Query("SELECT s FROM Story s WHERE s.user.id = :userId ")
    public List<Story> findAllStoryByUserId(@Param("userId") Integer userId);
}
