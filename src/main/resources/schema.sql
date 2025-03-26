-- Drop tables if they exist
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
INSERT INTO users (username, email, first_name, last_name)
VALUES 
    ('user1', 'user1@example.com', 'John', 'Doe'),
    ('user2', 'user2@example.com', 'Jane', 'Smith'),
    ('user3', 'user3@example.com', 'Bob', 'Johnson');

-- Insert sample projects
INSERT INTO projects (name, description, created_at, updated_at, user_id)
VALUES 
    ('Project 1', 'This is a sample project 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('Project 2', 'This is a sample project 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('Project 3', 'This is a sample project 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
    ('Project 4', 'This is a sample project 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);