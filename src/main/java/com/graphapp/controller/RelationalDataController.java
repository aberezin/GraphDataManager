package com.graphapp.controller;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.service.RelationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for relational data operations.
 */
@RestController
@RequestMapping("/api/relational")
@CrossOrigin(origins = "*")
public class RelationalDataController {

    private final RelationalDataService relationalDataService;

    @Autowired
    public RelationalDataController(RelationalDataService relationalDataService) {
        this.relationalDataService = relationalDataService;
    }

    /**
     * Get all users.
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = relationalDataService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by ID.
     * @param id ID of the user to find
     * @return ResponseEntity containing the user if found
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return relationalDataService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new user.
     * @param user User to create
     * @return ResponseEntity containing the created user
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = relationalDataService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing user.
     * @param id ID of the user to update
     * @param user Updated user details
     * @return ResponseEntity containing the updated user
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = relationalDataService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e instanceof IllegalArgumentException) {
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a user by ID.
     * @param id ID of the user to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            relationalDataService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Search users by a query string.
     * @param query Query string to search for
     * @return ResponseEntity containing a list of users matching the search query
     */
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = relationalDataService.searchUsers(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get all projects.
     * @return ResponseEntity containing a list of all projects
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = relationalDataService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Get a project by ID.
     * @param id ID of the project to find
     * @return ResponseEntity containing the project if found
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return relationalDataService.getProjectById(id)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get projects by user ID.
     * @param userId ID of the user to find projects for
     * @return ResponseEntity containing a list of projects for the given user
     */
    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = relationalDataService.getProjectsByUserId(userId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Create a new project.
     * @param project Project to create
     * @return ResponseEntity containing the created project
     */
    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project createdProject = relationalDataService.createProject(project);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (RuntimeException | IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing project.
     * @param id ID of the project to update
     * @param project Updated project details
     * @return ResponseEntity containing the updated project
     */
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = relationalDataService.updateProject(id, project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            if (e instanceof IllegalArgumentException) {
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a project by ID.
     * @param id ID of the project to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            relationalDataService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Search projects by a query string.
     * @param query Query string to search for
     * @return ResponseEntity containing a list of projects matching the search query
     */
    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        List<Project> projects = relationalDataService.searchProjects(query);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Get recent projects.
     * @param limit Maximum number of projects to return (optional)
     * @return ResponseEntity containing a list of recent projects
     */
    @GetMapping("/projects/recent")
    public ResponseEntity<List<Project>> getRecentProjects(@RequestParam(required = false, defaultValue = "5") int limit) {
        List<Project> projects = relationalDataService.getRecentProjects(limit);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Search for users and projects.
     * @param query Query string to search for
     * @return ResponseEntity containing users and projects matching the search query
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchRelationalData(@RequestParam String query) {
        List<User> users = relationalDataService.searchUsers(query);
        List<Project> projects = relationalDataService.searchProjects(query);
        
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("projects", projects);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}