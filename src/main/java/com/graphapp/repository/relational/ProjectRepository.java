package com.graphapp.repository.relational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.graphapp.model.relational.Project;

/**
 * Repository interface for Project entities.
 * This interface provides methods to perform CRUD operations on Project entities.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find projects by user ID.
     * 
     * @param userId The ID of the user.
     * @return A list of projects belonging to the user.
     */
    List<Project> findByUserId(Long userId);
    
    /**
     * Search for projects by name or description containing the given text.
     * 
     * @param searchText The text to search for.
     * @return A list of projects matching the search criteria.
     */
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String searchText, String searchText2);
    
    /**
     * Find recent projects, ordered by creation date (descending).
     * 
     * @param limit The maximum number of projects to return.
     * @return A list of recent projects.
     */
    @Query(value = "SELECT p.* FROM projects p ORDER BY p.created_at DESC LIMIT :limit", 
            nativeQuery = true)
    List<Project> findRecentProjects(@Param("limit") int limit);
    
    /**
     * Count the number of projects belonging to a user.
     * 
     * @param userId The ID of the user.
     * @return The number of projects belonging to the user.
     */
    long countByUserId(Long userId);
}