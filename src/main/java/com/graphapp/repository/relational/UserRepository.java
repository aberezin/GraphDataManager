package com.graphapp.repository.relational;

import com.graphapp.model.relational.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for User entities in the relational database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by username.
     * 
     * @param username The username.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email.
     * 
     * @param email The email.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find users by first name.
     * 
     * @param firstName The first name.
     * @return The list of users.
     */
    List<User> findByFirstName(String firstName);
    
    /**
     * Find users by last name.
     * 
     * @param lastName The last name.
     * @return The list of users.
     */
    List<User> findByLastName(String lastName);
    
    /**
     * Find users by first name and last name.
     * 
     * @param firstName The first name.
     * @param lastName The last name.
     * @return The list of users.
     */
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Check if a username exists.
     * 
     * @param username The username.
     * @return True if the username exists, false otherwise.
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email exists.
     * 
     * @param email The email.
     * @return True if the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
    
    /**
     * Search users by username, email, first name, or last name.
     * 
     * @param query The search query.
     * @return The list of users.
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsers(@Param("query") String query);
    
    /**
     * Find users that have projects.
     * 
     * @return The list of users.
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.projects")
    List<User> findUsersWithProjects();
    
    /**
     * Count users by the number of projects they have.
     * 
     * @param minProjectCount The minimum number of projects.
     * @return The list of users.
     */
    @Query("SELECT u FROM User u WHERE SIZE(u.projects) >= :minProjectCount")
    List<User> findUsersByMinProjectCount(@Param("minProjectCount") int minProjectCount);
}