-- Drop tables if they exist to ensure a clean state
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

-- Create projects table
CREATE TABLE IF NOT EXISTS projects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert sample users
INSERT INTO users (username, email, first_name, last_name) VALUES
('johndoe', 'john.doe@example.com', 'John', 'Doe'),
('janedoe', 'jane.doe@example.com', 'Jane', 'Doe'),
('bobsmith', 'bob.smith@example.com', 'Bob', 'Smith');

-- Insert sample projects
INSERT INTO projects (name, description, created_at, updated_at, user_id) VALUES
('Project Alpha', 'This is the first project', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
('Project Beta', 'This is the second project with more details', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
('Project Gamma', 'A project for testing graph visualization', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
('Project Delta', 'A comprehensive data analysis project', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);

-- Neo4j equivalent statements (for reference only - to be run in Neo4j console)
-- 
-- // Clear the database
-- MATCH (n) DETACH DELETE n;
-- 
-- // Create Person nodes
-- CREATE (n:Person {id: 1, label: 'John Doe', type: 'person', properties: {age: 30, occupation: 'Developer'}});
-- CREATE (n:Person {id: 2, label: 'Jane Doe', type: 'person', properties: {age: 28, occupation: 'Designer'}});
-- CREATE (n:Person {id: 3, label: 'Bob Smith', type: 'person', properties: {age: 35, occupation: 'Manager'}});
-- 
-- // Create Technology nodes
-- CREATE (n:Technology {id: 4, label: 'Java', type: 'technology', properties: {category: 'Programming Language'}});
-- CREATE (n:Technology {id: 5, label: 'Spring', type: 'technology', properties: {category: 'Framework'}});
-- CREATE (n:Technology {id: 6, label: 'Neo4j', type: 'technology', properties: {category: 'Database'}});
-- CREATE (n:Technology {id: 7, label: 'React', type: 'technology', properties: {category: 'Framework'}});
-- 
-- // Create Project nodes
-- CREATE (n:Project {id: 8, label: 'Project Alpha', type: 'project', properties: {status: 'In Progress'}});
-- CREATE (n:Project {id: 9, label: 'Project Beta', type: 'project', properties: {status: 'Planning'}});
-- CREATE (n:Project {id: 10, label: 'Project Gamma', type: 'project', properties: {status: 'Completed'}});
-- 
-- // Create relationships
-- MATCH (a:Person {id: 1}), (b:Technology {id: 4}) CREATE (a)-[:KNOWS {type: 'KNOWS', properties: {level: 'Expert'}}]->(b);
-- MATCH (a:Person {id: 1}), (b:Technology {id: 5}) CREATE (a)-[:KNOWS {type: 'KNOWS', properties: {level: 'Intermediate'}}]->(b);
-- MATCH (a:Person {id: 2}), (b:Technology {id: 7}) CREATE (a)-[:KNOWS {type: 'KNOWS', properties: {level: 'Expert'}}]->(b);
-- MATCH (a:Person {id: 3}), (b:Technology {id: 6}) CREATE (a)-[:KNOWS {type: 'KNOWS', properties: {level: 'Beginner'}}]->(b);
--
-- MATCH (a:Person {id: 1}), (b:Project {id: 8}) CREATE (a)-[:WORKS_ON {type: 'WORKS_ON', properties: {role: 'Developer'}}]->(b);
-- MATCH (a:Person {id: 2}), (b:Project {id: 8}) CREATE (a)-[:WORKS_ON {type: 'WORKS_ON', properties: {role: 'Designer'}}]->(b);
-- MATCH (a:Person {id: 3}), (b:Project {id: 9}) CREATE (a)-[:WORKS_ON {type: 'WORKS_ON', properties: {role: 'Manager'}}]->(b);
-- MATCH (a:Person {id: 1}), (b:Project {id: 10}) CREATE (a)-[:WORKS_ON {type: 'WORKS_ON', properties: {role: 'Developer'}}]->(b);
--
-- MATCH (a:Project {id: 8}), (b:Technology {id: 4}) CREATE (a)-[:USES {type: 'USES', properties: {}}]->(b);
-- MATCH (a:Project {id: 8}), (b:Technology {id: 5}) CREATE (a)-[:USES {type: 'USES', properties: {}}]->(b);
-- MATCH (a:Project {id: 9}), (b:Technology {id: 6}) CREATE (a)-[:USES {type: 'USES', properties: {}}]->(b);
-- MATCH (a:Project {id: 10}), (b:Technology {id: 7}) CREATE (a)-[:USES {type: 'USES', properties: {}}]->(b);