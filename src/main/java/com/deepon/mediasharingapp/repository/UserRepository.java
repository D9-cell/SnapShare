package com.deepon.mediasharingapp.repository;

import com.deepon.mediasharingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    public List<User> findAllUsersByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u where u.username LIKE %:query% OR u.email LIKE %:query% ")
    public List<User> findbyQuery(@Param("query") String query);

}
