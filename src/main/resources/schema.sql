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
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data for development
INSERT INTO users (username, email, first_name, last_name) VALUES
    ('john_doe', 'john.doe@example.com', 'John', 'Doe'),
    ('jane_smith', 'jane.smith@example.com', 'Jane', 'Smith'),
    ('bob_johnson', 'bob.johnson@example.com', 'Bob', 'Johnson');

INSERT INTO projects (name, description, user_id) VALUES
    ('Knowledge Graph', 'A project to represent organizational knowledge as a graph', 1),
    ('Social Network Analysis', 'Analyzing connections between people in a social network', 1),
    ('Recommendation Engine', 'Graph-based recommendation system for products', 2),
    ('Data Visualization Tool', 'Tool to visualize complex data relationships', 3);