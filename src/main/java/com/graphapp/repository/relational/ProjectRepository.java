package com.graphapp.repository.relational;

import com.graphapp.model.relational.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByUserId(Long userId);
    
    @Query("SELECT p FROM Project p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Project> searchProjects(@Param("query") String query);
    
    @Query("SELECT p FROM Project p ORDER BY p.createdAt DESC")
    List<Project> findRecentProjects();
    
    @Query("SELECT p FROM Project p ORDER BY p.updatedAt DESC")
    List<Project> findRecentlyUpdatedProjects();
    
    @Query("SELECT p FROM Project p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Project> findRecentProjectsByUserId(@Param("userId") Long userId);
}