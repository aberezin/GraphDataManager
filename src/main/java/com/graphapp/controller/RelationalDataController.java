package com.graphapp.controller;

import com.graphapp.model.relational.User;
import com.graphapp.model.relational.Project;
import com.graphapp.service.RelationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/relational")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = relationalDataService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = relationalDataService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!relationalDataService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(relationalDataService.saveUser(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!relationalDataService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        relationalDataService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Project endpoints
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(relationalDataService.getAllProjects());
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = relationalDataService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        if (!relationalDataService.getUserById(userId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(relationalDataService.getProjectsByUserId(userId));
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = relationalDataService.saveProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        if (!relationalDataService.getProjectById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        project.setId(id);
        return ResponseEntity.ok(relationalDataService.saveProject(project));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!relationalDataService.getProjectById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        relationalDataService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // Search endpoint
    @GetMapping("/search")
    public ResponseEntity<List<?>> search(@RequestParam String query, @RequestParam String type) {
        if ("user".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(relationalDataService.searchUsers(query));
        } else if ("project".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(relationalDataService.searchProjects(query));
        }
        return ResponseEntity.badRequest().build();
    }
}
