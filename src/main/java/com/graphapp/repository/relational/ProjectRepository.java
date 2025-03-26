package com.graphapp.repository.relational;

import com.graphapp.model.relational.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for relational Project entities
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find projects by name
     */
    List<Project> findByName(String name);

    /**
     * Find projects by user id
     */
    List<Project> findByUserId(Long userId);

    /**
     * Find projects created after a given date
     */
    List<Project> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find projects updated after a given date
     */
    List<Project> findByUpdatedAtAfter(LocalDateTime date);

    /**
     * Search projects by text (searches in name and description)
     */
    @Query("SELECT p FROM Project p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Project> searchProjects(@Param("text") String text);

    /**
     * Get a specific project with its user
     */
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.user WHERE p.id = :id")
    Optional<Project> findByIdWithUser(@Param("id") Long id);

    /**
     * Get recent projects
     */
    @Query("SELECT p FROM Project p ORDER BY p.updatedAt DESC")
    List<Project> findRecentProjects(org.springframework.data.domain.Pageable pageable);
}