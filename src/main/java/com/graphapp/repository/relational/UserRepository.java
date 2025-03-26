package com.graphapp.repository.relational;

import com.graphapp.model.relational.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for relational User entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Search users by text (searches in username, email, firstName, lastName)
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<User> searchUsers(@Param("text") String text);

    /**
     * Get users with their projects
     */
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.projects")
    List<User> findAllWithProjects();

    /**
     * Get a specific user with their projects
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.id = :id")
    Optional<User> findByIdWithProjects(@Param("id") Long id);
}