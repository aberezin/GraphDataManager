-- Drop tables if they exist
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

-- Create projects table
CREATE TABLE projects (
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
('Web Application', 'A web application project using Spring Boot and React', datetime('now', '-30 days'), datetime('now', '-5 days'), 1),
('Mobile App', 'A cross-platform mobile application for iOS and Android', datetime('now', '-60 days'), datetime('now', '-2 days'), 1),
('Data Analysis', 'Project analyzing large datasets using machine learning', datetime('now', '-45 days'), datetime('now', '-1 days'), 2),
('IoT Platform', 'Internet of Things platform for smart home devices', datetime('now', '-90 days'), datetime('now', '-10 days'), 3),
('E-commerce Site', 'Online shopping website with payment processing', datetime('now', '-120 days'), datetime('now', '-25 days'), 2);