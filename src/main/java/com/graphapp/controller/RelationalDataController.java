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

@RestController
@RequestMapping("/api/relational")
public class RelationalDataController {
    
    private final RelationalDataService relationalDataService;
    
    @Autowired
    public RelationalDataController(RelationalDataService relationalDataService) {
        this.relationalDataService = relationalDataService;
    }
    
    // User endpoints
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(relationalDataService.getAllUsers());
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return relationalDataService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/users/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return relationalDataService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(relationalDataService.createUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(relationalDataService.updateUser(id, userDetails));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            relationalDataService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchUsers(query));
    }
    
    // Project endpoints
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(relationalDataService.getAllProjects());
    }
    
    @GetMapping("/projects/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return relationalDataService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/projects/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(relationalDataService.getProjectsByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }
    
    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(relationalDataService.createProject(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        try {
            return ResponseEntity.ok(relationalDataService.updateProject(id, projectDetails));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            relationalDataService.deleteProject(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchProjects(query));
    }
    
    @GetMapping("/projects/recent")
    public ResponseEntity<List<Project>> getRecentProjects() {
        return ResponseEntity.ok(relationalDataService.getRecentProjects());
    }
    
    // Combined endpoints for dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        List<User> recentUsers = relationalDataService.getAllUsers();
        List<Project> recentProjects = relationalDataService.getRecentProjects();
        
        return ResponseEntity.ok(Map.of(
                "users", recentUsers,
                "projects", recentProjects
        ));
    }
}