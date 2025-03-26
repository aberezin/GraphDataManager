import React, { useState, useEffect } from 'react';
import GraphVisualization from '../components/GraphVisualization';
import NodeForm from '../components/NodeForm';
import RelationshipForm from '../components/RelationshipForm';
import SearchBar from '../components/SearchBar';
import { 
  getNodes, 
  getRelationships, 
  createNode, 
  updateNode, 
  deleteNode, 
  createRelationship, 
  updateRelationship, 
  deleteRelationship,
  searchGraph
} from '../services/api';
import { NodeType, RelationshipType } from '../types';

const GraphData: React.FC = () => {
  const [nodes, setNodes] = useState<NodeType[]>([]);
  const [relationships, setRelationships] = useState<RelationshipType[]>([]);
  const [selectedNode, setSelectedNode] = useState<NodeType | null>(null);
  const [selectedRelationship, setSelectedRelationship] = useState<RelationshipType | null>(null);
  const [showNodeForm, setShowNodeForm] = useState(false);
  const [showRelationshipForm, setShowRelationshipForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<'visualization' | 'nodes' | 'relationships'>('visualization');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const [nodesData, relationshipsData] = await Promise.all([
        getNodes(),
        getRelationships()
      ]);
      
      setNodes(nodesData);
      setRelationships(relationshipsData);
      setLoading(false);
    } catch (err) {
      console.error('Error fetching graph data:', err);
      setError('Failed to load graph data. Please try again later.');
      setLoading(false);
    }
  };

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

  // Node operations
  const handleCreateNode = async (node: NodeType) => {
    try {
      setLoading(true);
      const newNode = await createNode(node);
      setNodes(prev => [...prev, newNode]);
      setShowNodeForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error creating node:', err);
      setError('Failed to create node. Please try again.');
      setLoading(false);
    }
  };

  const handleUpdateNode = async (node: NodeType) => {
    if (!node.id) return;
    
    try {
      setLoading(true);
      const updatedNode = await updateNode(node.id, node);
      setNodes(prev => prev.map(n => n.id === updatedNode.id ? updatedNode : n));
      setSelectedNode(null);
      setShowNodeForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error updating node:', err);
      setError('Failed to update node. Please try again.');
      setLoading(false);
    }
  };

  const handleDeleteNode = async (id: number) => {
    try {
      setLoading(true);
      await deleteNode(id);
      setNodes(prev => prev.filter(n => n.id !== id));
      
      // Also filter out relationships that contain this node
      setRelationships(prev => prev.filter(r => 
        r.source?.id !== id && r.target?.id !== id
      ));
      
      setLoading(false);
    } catch (err) {
      console.error('Error deleting node:', err);
      setError('Failed to delete node. Please try again.');
      setLoading(false);
    }
  };

  // Relationship operations
  const handleCreateRelationship = async (relationship: RelationshipType) => {
    try {
      setLoading(true);
      const newRelationship = await createRelationship(relationship);
      setRelationships(prev => [...prev, newRelationship]);
      setShowRelationshipForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error creating relationship:', err);
      setError('Failed to create relationship. Please try again.');
      setLoading(false);
    }
  };

  const handleUpdateRelationship = async (relationship: RelationshipType) => {
    if (!relationship.id) return;
    
    try {
      setLoading(true);
      const updatedRelationship = await updateRelationship(relationship.id, relationship);
      setRelationships(prev => 
        prev.map(r => r.id === updatedRelationship.id ? updatedRelationship : r)
      );
      setSelectedRelationship(null);
      setShowRelationshipForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error updating relationship:', err);
      setError('Failed to update relationship. Please try again.');
      setLoading(false);
    }
  };

  const handleDeleteRelationship = async (id: number) => {
    try {
      setLoading(true);
      await deleteRelationship(id);
      setRelationships(prev => prev.filter(r => r.id !== id));
      setLoading(false);
    } catch (err) {
      console.error('Error deleting relationship:', err);
      setError('Failed to delete relationship. Please try again.');
      setLoading(false);
    }
  };

  const handleNodeSubmit = (node: NodeType) => {
    if (node.id) {
      handleUpdateNode(node);
    } else {
      handleCreateNode(node);
    }
  };

  const handleRelationshipSubmit = (relationship: RelationshipType) => {
    if (relationship.id) {
      handleUpdateRelationship(relationship);
    } else {
      handleCreateRelationship(relationship);
    }
  };

  return (
    <div className="graph-data-page">
      <h1>Graph Data Management</h1>
      
      <div className="search-container">
        <SearchBar onSearch={handleSearch} placeholder="Search graph data..." />
      </div>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="tabs">
        <button 
          className={`tab ${activeTab === 'visualization' ? 'active' : ''}`}
          onClick={() => setActiveTab('visualization')}
        >
          Visualization
        </button>
        <button 
          className={`tab ${activeTab === 'nodes' ? 'active' : ''}`}
          onClick={() => setActiveTab('nodes')}
        >
          Nodes
        </button>
        <button 
          className={`tab ${activeTab === 'relationships' ? 'active' : ''}`}
          onClick={() => setActiveTab('relationships')}
        >
          Relationships
        </button>
      </div>
      
      <div className="tab-content">
        {activeTab === 'visualization' && (
          <div className="visualization-tab">
            {loading ? (
              <div className="loading">Loading visualization...</div>
            ) : nodes.length === 0 ? (
              <div className="empty-state">No graph data available. Add nodes and relationships to see visualization.</div>
            ) : (
              <GraphVisualization nodes={nodes} relationships={relationships} width={800} height={500} />
            )}
          </div>
        )}
        
        {activeTab === 'nodes' && (
          <div className="nodes-tab">
            <div className="controls">
              <button 
                className="add-button"
                onClick={() => {
                  setSelectedNode(null);
                  setShowNodeForm(true);
                }}
              >
                Add New Node
              </button>
            </div>
            
            {showNodeForm && (
              <div className="form-overlay">
                <div className="form-container">
                  <NodeForm 
                    node={selectedNode || undefined}
                    onSubmit={handleNodeSubmit}
                    onCancel={() => {
                      setSelectedNode(null);
                      setShowNodeForm(false);
                    }}
                  />
                </div>
              </div>
            )}
            
            {loading ? (
              <div className="loading">Loading nodes...</div>
            ) : nodes.length === 0 ? (
              <div className="empty-state">No nodes available. Add a new node to get started.</div>
            ) : (
              <div className="node-list">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Label</th>
                      <th>Type</th>
                      <th>Properties</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {nodes.map(node => (
                      <tr key={node.id}>
                        <td>{node.id}</td>
                        <td>{node.label}</td>
                        <td>{node.type}</td>
                        <td>
                          {node.properties && Object.keys(node.properties).length > 0 ? (
                            <details>
                              <summary>Show properties</summary>
                              <pre>{JSON.stringify(node.properties, null, 2)}</pre>
                            </details>
                          ) : (
                            <span>No properties</span>
                          )}
                        </td>
                        <td>
                          <button 
                            className="edit-button"
                            onClick={() => {
                              setSelectedNode(node);
                              setShowNodeForm(true);
                            }}
                          >
                            Edit
                          </button>
                          <button 
                            className="delete-button"
                            onClick={() => {
                              if (window.confirm('Are you sure you want to delete this node?')) {
                                handleDeleteNode(node.id!);
                              }
                            }}
                          >
                            Delete
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}
        
        {activeTab === 'relationships' && (
          <div className="relationships-tab">
            <div className="controls">
              <button 
                className="add-button"
                onClick={() => {
                  setSelectedRelationship(null);
                  setShowRelationshipForm(true);
                }}
                disabled={nodes.length < 2}
              >
                Add New Relationship
              </button>
              {nodes.length < 2 && (
                <p className="note">Need at least two nodes to create a relationship.</p>
              )}
            </div>
            
            {showRelationshipForm && (
              <div className="form-overlay">
                <div className="form-container">
                  <RelationshipForm 
                    relationship={selectedRelationship || undefined}
                    availableNodes={nodes}
                    onSubmit={handleRelationshipSubmit}
                    onCancel={() => {
                      setSelectedRelationship(null);
                      setShowRelationshipForm(false);
                    }}
                  />
                </div>
              </div>
            )}
            
            {loading ? (
              <div className="loading">Loading relationships...</div>
            ) : relationships.length === 0 ? (
              <div className="empty-state">No relationships available. Add a new relationship to get started.</div>
            ) : (
              <div className="relationship-list">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Type</th>
                      <th>Source</th>
                      <th>Target</th>
                      <th>Properties</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {relationships.map(relationship => (
                      <tr key={relationship.id}>
                        <td>{relationship.id}</td>
                        <td>{relationship.type}</td>
                        <td>
                          {relationship.source ? (
                            `${relationship.source.label || 'Node'} (ID: ${relationship.source.id})`
                          ) : 'Unknown'}
                        </td>
                        <td>
                          {relationship.target ? (
                            `${relationship.target.label || 'Node'} (ID: ${relationship.target.id})`
                          ) : 'Unknown'}
                        </td>
                        <td>
                          {relationship.properties && Object.keys(relationship.properties).length > 0 ? (
                            <details>
                              <summary>Show properties</summary>
                              <pre>{JSON.stringify(relationship.properties, null, 2)}</pre>
                            </details>
                          ) : (
                            <span>No properties</span>
                          )}
                        </td>
                        <td>
                          <button 
                            className="edit-button"
                            onClick={() => {
                              setSelectedRelationship(relationship);
                              setShowRelationshipForm(true);
                            }}
                          >
                            Edit
                          </button>
                          <button 
                            className="delete-button"
                            onClick={() => {
                              if (window.confirm('Are you sure you want to delete this relationship?')) {
                                handleDeleteRelationship(relationship.id!);
                              }
                            }}
                          >
                            Delete
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default GraphData;
