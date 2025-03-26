-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert sample data for development
INSERT OR IGNORE INTO users (id, username, email, first_name, last_name)
VALUES 
    (1, 'johndoe', 'john.doe@example.com', 'John', 'Doe'),
    (2, 'janedoe', 'jane.doe@example.com', 'Jane', 'Doe'),
    (3, 'bobsmith', 'bob.smith@example.com', 'Bob', 'Smith');

INSERT OR IGNORE INTO projects (id, name, description, created_at, updated_at, user_id)
VALUES 
    (1, 'Project Alpha', 'A sample project for demonstration', datetime('now', '-10 days'), datetime('now', '-1 day'), 1),
    (2, 'Project Beta', 'Another sample project for testing', datetime('now', '-5 days'), datetime('now'), 1),
    (3, 'Data Analysis Project', 'Analyze and visualize data using Neo4j', datetime('now', '-20 days'), datetime('now', '-15 days'), 2),
    (4, 'Graph Visualization', 'Project for creating interactive graph visualizations', datetime('now', '-2 days'), datetime('now'), 3);