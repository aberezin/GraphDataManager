package com.graphapp.controller;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.service.RelationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for relational data operations.
 */
@RestController
@RequestMapping("/api/relational")
public class RelationalDataController {

    private final RelationalDataService relationalDataService;

    /**
     * Constructor for RelationalDataController.
     * 
     * @param relationalDataService The relational data service.
     */
    @Autowired
    public RelationalDataController(RelationalDataService relationalDataService) {
        this.relationalDataService = relationalDataService;
    }

    /**
     * Get all users.
     * 
     * @return The list of users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(relationalDataService.getAllUsers());
    }

    /**
     * Get a user by ID.
     * 
     * @param id The ID of the user.
     * @return The user if found, or a 404 response.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = relationalDataService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new user.
     * 
     * @param user The user to create.
     * @return The created user.
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(relationalDataService.createUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a user.
     * 
     * @param id The ID of the user to update.
     * @param user The updated user details.
     * @return The updated user, or a 404 response.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = relationalDataService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a user.
     * 
     * @param id The ID of the user to delete.
     * @return A 204 response if successful, or a 404 response.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            relationalDataService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find a user by username.
     * 
     * @param username The username.
     * @return The user if found, or a 404 response.
     */
    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        Optional<User> user = relationalDataService.findUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Find a user by email.
     * 
     * @param email The email.
     * @return The user if found, or a 404 response.
     */
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        Optional<User> user = relationalDataService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Search users by various criteria.
     * 
     * @param query The search query.
     * @return The list of users.
     */
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchUsers(query));
    }

    /**
     * Get all projects.
     * 
     * @return The list of projects.
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(relationalDataService.getAllProjects());
    }

    /**
     * Get a project by ID.
     * 
     * @param id The ID of the project.
     * @return The project if found, or a 404 response.
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = relationalDataService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new project.
     * 
     * @param project The project to create.
     * @return The created project.
     */
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(relationalDataService.createProject(project));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a project.
     * 
     * @param id The ID of the project to update.
     * @param project The updated project details.
     * @return The updated project, or a 404 response.
     */
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = relationalDataService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a project.
     * 
     * @param id The ID of the project to delete.
     * @return A 204 response if successful, or a 404 response.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            relationalDataService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find projects by user ID.
     * 
     * @param userId The user ID.
     * @return The list of projects.
     */
    @GetMapping("/projects/user/{userId}")
    public ResponseEntity<List<Project>> findProjectsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(relationalDataService.findProjectsByUserId(userId));
    }

    /**
     * Search projects by name or description.
     * 
     * @param query The search query.
     * @return The list of projects.
     */
    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchProjects(query));
    }

    /**
     * Get recent projects up to the given limit.
     * 
     * @param limit The maximum number of projects to return.
     * @return The list of projects.
     */
    @GetMapping("/projects/recent")
    public ResponseEntity<List<Project>> getRecentProjects(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(relationalDataService.getRecentProjects(limit));
    }

    /**
     * Get statistics about users and projects.
     * 
     * @return A map containing various statistics.
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(relationalDataService.getStatistics());
    }
}