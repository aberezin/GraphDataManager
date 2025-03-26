-- Drop tables if they exist
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

-- Create tables
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS projects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert sample data
INSERT INTO users (username, email, first_name, last_name)
VALUES 
    ('johndoe', 'john.doe@example.com', 'John', 'Doe'),
    ('janesmith', 'jane.smith@example.com', 'Jane', 'Smith'),
    ('bobwilson', 'bob.wilson@example.com', 'Bob', 'Wilson');

INSERT INTO projects (name, description, created_at, updated_at, user_id)
VALUES 
    ('Graph Analysis Tool', 'A tool for visualizing and analyzing graph data structures', DATETIME('now', '-30 days'), DATETIME('now', '-5 days'), 1),
    ('Data Integration Platform', 'Platform for integrating relational and graph data sources', DATETIME('now', '-60 days'), DATETIME('now', '-2 days'), 1),
    ('Knowledge Graph Builder', 'Tool to create and maintain knowledge graphs from structured data', DATETIME('now', '-15 days'), DATETIME('now'), 2),
    ('Network Visualization', 'Interactive visualization of network relationships', DATETIME('now', '-45 days'), DATETIME('now', '-10 days'), 3);