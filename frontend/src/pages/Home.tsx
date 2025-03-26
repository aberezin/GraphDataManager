import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import GraphVisualization from '../components/GraphVisualization';
import SearchBar from '../components/SearchBar';
import { getGraphVisualizationData, searchGraph, getRecentProjects } from '../services/api';
import { NodeType, RelationshipType, ProjectType } from '../types';

const Home: React.FC = () => {
  const [nodes, setNodes] = useState<NodeType[]>([]);
  const [relationships, setRelationships] = useState<RelationshipType[]>([]);
  const [recentProjects, setRecentProjects] = useState<ProjectType[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        
        // Get graph visualization data
        const graphData = await getGraphVisualizationData();
        setNodes(graphData.nodes || []);
        setRelationships(graphData.relationships || []);
        
        // Get recent projects
        const projects = await getRecentProjects();
        setRecentProjects(projects);
        
        setLoading(false);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError('Failed to load data. Please try again later.');
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleSearch = async (query: string) => {
    try {
      setLoading(true);
      const results = await searchGraph(query);
      setNodes(results.nodes || []);
      setRelationships(results.relationships || []);
      setLoading(false);
    } catch (err) {
      console.error('Error searching:', err);
      setError('Search failed. Please try again.');
      setLoading(false);
    }
  };

  return (
    <div className="home-page">
      <div className="intro-section">
        <h1>Welcome to Graph App</h1>
        <p>
          Explore and manage your graph and relational data in one place.
          Visualize connections between entities and manage your projects efficiently.
        </p>
        
        <div className="search-container">
          <SearchBar onSearch={handleSearch} placeholder="Search nodes, relationships..." />
        </div>
      </div>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="graph-preview">
        <h2>Graph Visualization</h2>
        {loading ? (
          <div className="loading">Loading visualization...</div>
        ) : nodes.length === 0 ? (
          <div className="empty-state">No graph data available. Add nodes and relationships to see visualization.</div>
        ) : (
          <GraphVisualization nodes={nodes} relationships={relationships} />
        )}
        <div className="view-more">
          <Link to="/graph-data" className="view-more-link">
            Manage Graph Data →
          </Link>
        </div>
      </div>
      
      <div className="recent-projects">
        <h2>Recent Projects</h2>
        {loading ? (
          <div className="loading">Loading projects...</div>
        ) : recentProjects.length === 0 ? (
          <div className="empty-state">No projects available.</div>
        ) : (
          <div className="project-list">
            {recentProjects.slice(0, 5).map(project => (
              <div key={project.id} className="project-card">
                <h3>{project.name}</h3>
                <p>{project.description || 'No description'}</p>
                <div className="project-meta">
                  <span>Created: {new Date(project.createdAt || '').toLocaleDateString()}</span>
                  {project.user && <span>Owner: {project.user.username}</span>}
                </div>
              </div>
            ))}
          </div>
        )}
        <div className="view-more">
          <Link to="/relational-data" className="view-more-link">
            Manage Relational Data →
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
