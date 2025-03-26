package com.graphapp.repository.relational;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.graphapp.model.relational.User;

/**
 * Repository interface for User entities.
 * This interface provides methods to perform CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by username.
     * 
     * @param username The username to search for.
     * @return An optional containing the user if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email.
     * 
     * @param email The email to search for.
     * @return An optional containing the user if found, or empty if not found.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if a user with the given username exists.
     * 
     * @param username The username to check.
     * @return True if a user with the username exists, false otherwise.
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if a user with the given email exists.
     * 
     * @param email The email to check.
     * @return True if a user with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
    
    /**
     * Search for users by username, email, first name, or last name containing the given text.
     * 
     * @param searchText The text to search for.
     * @return A list of users matching the search criteria.
     */
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String searchText, String searchText2, String searchText3, String searchText4);
}