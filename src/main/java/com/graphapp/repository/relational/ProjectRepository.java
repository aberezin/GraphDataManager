package com.graphapp.repository.relational;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Project entities in the relational database.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find projects by name.
     * 
     * @param name The name of the project.
     * @return The list of projects.
     */
    List<Project> findByName(String name);
    
    /**
     * Find projects by name containing the given string.
     * 
     * @param nameFragment The fragment of the name.
     * @return The list of projects.
     */
    List<Project> findByNameContaining(String nameFragment);
    
    /**
     * Find projects by description containing the given string.
     * 
     * @param descriptionFragment The fragment of the description.
     * @return The list of projects.
     */
    List<Project> findByDescriptionContaining(String descriptionFragment);
    
    /**
     * Find projects by user.
     * 
     * @param user The user.
     * @return The list of projects.
     */
    List<Project> findByUser(User user);
    
    /**
     * Find projects by user ID.
     * 
     * @param userId The user ID.
     * @return The list of projects.
     */
    List<Project> findByUserId(Long userId);
    
    /**
     * Find projects created after the given date.
     * 
     * @param date The date.
     * @return The list of projects.
     */
    List<Project> findByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Find projects updated after the given date.
     * 
     * @param date The date.
     * @return The list of projects.
     */
    List<Project> findByUpdatedAtAfter(LocalDateTime date);
    
    /**
     * Search projects by name or description.
     * 
     * @param query The search query.
     * @return The list of projects.
     */
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Project> searchProjects(@Param("query") String query);
    
    /**
     * Find recent projects up to the given limit.
     * 
     * @param limit The maximum number of projects to return.
     * @return The list of projects.
     */
    @Query("SELECT p FROM Project p ORDER BY p.updatedAt DESC")
    List<Project> findRecentProjects(@Param("limit") int limit);
    
    /**
     * Find projects by user and created after the given date.
     * 
     * @param userId The user ID.
     * @param date The date.
     * @return The list of projects.
     */
    @Query("SELECT p FROM Project p WHERE p.user.id = :userId AND p.createdAt >= :date")
    List<Project> findByUserIdAndCreatedAtAfter(@Param("userId") Long userId, @Param("date") LocalDateTime date);
    
    /**
     * Count projects by user.
     * 
     * @return The map of user ID to project count.
     */
    @Query("SELECT p.user.id, COUNT(p) FROM Project p GROUP BY p.user.id")
    List<Object[]> countProjectsByUser();
}