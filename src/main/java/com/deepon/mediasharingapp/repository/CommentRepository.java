package com.deepon.mediasharingapp.repository;

import com.deepon.mediasharingapp.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
}
