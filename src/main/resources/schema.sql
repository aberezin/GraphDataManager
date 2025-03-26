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
    updated_at TIMESTAMP,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample users
INSERT INTO users (username, email, first_name, last_name)
VALUES
    ('johndoe', 'john.doe@example.com', 'John', 'Doe'),
    ('janedoe', 'jane.doe@example.com', 'Jane', 'Doe'),
    ('bobsmith', 'bob.smith@example.com', 'Bob', 'Smith');

-- Insert sample projects
INSERT INTO projects (name, description, created_at, updated_at, user_id)
VALUES
    ('Social Network Analysis', 'Analyzing connections between people in a social network', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('Knowledge Graph', 'Building a knowledge graph for a specific domain', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('Supply Chain Visualization', 'Modeling supply chain dependencies as a graph', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
    ('Recommendation System', 'Product recommendation system based on user preferences', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);