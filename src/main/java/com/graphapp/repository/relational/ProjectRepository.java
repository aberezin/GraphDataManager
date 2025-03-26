package com.graphapp.repository.relational;

import com.graphapp.model.relational.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Project entities in the relational database.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find projects by name.
     * @param name Name of the projects to find
     * @return List of projects with the given name
     */
    List<Project> findByName(String name);

    /**
     * Find projects by user ID.
     * @param userId ID of the user to find projects for
     * @return List of projects owned by the given user
     */
    List<Project> findByUserId(Long userId);

    /**
     * Find projects created after a specific date.
     * @param date Date to compare against
     * @return List of projects created after the given date
     */
    List<Project> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find projects updated after a specific date.
     * @param date Date to compare against
     * @return List of projects updated after the given date
     */
    List<Project> findByUpdatedAtAfter(LocalDateTime date);

    /**
     * Search projects by a query string.
     * @param query The query string to search for
     * @return List of projects matching the search query
     */
    @Query("SELECT p FROM Project p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Project> searchProjects(@Param("query") String query);

    /**
     * Find projects by name and user ID.
     * @param name Name of the projects to find
     * @param userId ID of the user to find projects for
     * @return List of projects with the given name owned by the given user
     */
    List<Project> findByNameAndUserId(String name, Long userId);

    /**
     * Find projects containing a specific keyword in their description.
     * @param keyword Keyword to search for in project descriptions
     * @return List of projects with descriptions containing the given keyword
     */
    @Query("SELECT p FROM Project p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Project> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Find the most recently created projects.
     * @param pageable Pageable object specifying page size and sorting
     * @return List of the most recently created projects
     */
    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC")
    List<Project> findMostRecentProjects(Pageable pageable);

    /**
     * Find projects for a specific user, ordered by creation date.
     * @param userId ID of the user to find projects for
     * @return List of projects owned by the given user, ordered by creation date
     */
    @Query("SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Project> findProjectsByUserIdOrderByCreatedAt(@Param("userId") Long userId);
}