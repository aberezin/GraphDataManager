package com.graphapp.repository.relational;

import com.graphapp.model.relational.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entities in the relational database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     * @param username Username of the user to find
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email.
     * @param email Email of the user to find
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find users by first name.
     * @param firstName First name to search for
     * @return List of users with the given first name
     */
    List<User> findByFirstName(String firstName);

    /**
     * Find users by last name.
     * @param lastName Last name to search for
     * @return List of users with the given last name
     */
    List<User> findByLastName(String lastName);

    /**
     * Search users by a query string.
     * @param query The query string to search for
     * @return List of users matching the search query
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsers(@Param("query") String query);

    /**
     * Find users who have projects.
     * @return List of users who have at least one project
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.projects p")
    List<User> findUsersWithProjects();

    /**
     * Find users who have a project with the given name.
     * @param projectName Name of the project to search for
     * @return List of users who have a project with the given name
     */
    @Query("SELECT u FROM User u JOIN u.projects p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :projectName, '%'))")
    List<User> findUsersByProjectName(@Param("projectName") String projectName);
}