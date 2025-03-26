import axios from 'axios';
import { NodeType, RelationshipType, UserType, ProjectType } from '../types';

// With the proxy setup, we always use the same relative path
const API_BASE_URL = '/api';

// Configure axios defaults
axios.defaults.baseURL = API_BASE_URL;
axios.defaults.headers.common['Content-Type'] = 'application/json';

console.log('API Base URL set to:', API_BASE_URL);

// Graph Data API

// Node operations
export const getNodes = async (): Promise<NodeType[]> => {
  try {
    const response = await axios.get('/graph/nodes');
    return response.data;
  } catch (error) {
    console.error('Error fetching graph data:', error);
    throw error;
  }
};

export const getNodeById = async (id: number): Promise<NodeType> => {
  try {
    const response = await axios.get(`/graph/nodes/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching node with id ${id}:`, error);
    throw error;
  }
};

export const createNode = async (node: NodeType): Promise<NodeType> => {
  console.log('Creating node with data:', node);
  try {
    const response = await axios.post('/graph/nodes', node);
    console.log('Node creation response:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error creating node:', error);
    throw error;
  }
};

export const updateNode = async (id: number, node: NodeType): Promise<NodeType> => {
  try {
    const response = await axios.put(`/graph/nodes/${id}`, node);
    return response.data;
  } catch (error) {
    console.error(`Error updating node with id ${id}:`, error);
    throw error;
  }
};

export const deleteNode = async (id: number): Promise<void> => {
  try {
    await axios.delete(`/graph/nodes/${id}`);
  } catch (error) {
    console.error(`Error deleting node with id ${id}:`, error);
    throw error;
  }
};

// Relationship operations
export const getRelationships = async (): Promise<RelationshipType[]> => {
  try {
    const response = await axios.get('/graph/relationships');
    return response.data;
  } catch (error) {
    console.error('Error fetching relationships:', error);
    throw error;
  }
};

export const getRelationshipById = async (id: number): Promise<RelationshipType> => {
  try {
    const response = await axios.get(`/graph/relationships/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching relationship with id ${id}:`, error);
    throw error;
  }
};

export const createRelationship = async (relationship: RelationshipType): Promise<RelationshipType> => {
  try {
    const response = await axios.post('/graph/relationships', relationship);
    return response.data;
  } catch (error) {
    console.error('Error creating relationship:', error);
    throw error;
  }
};

export const updateRelationship = async (id: number, relationship: RelationshipType): Promise<RelationshipType> => {
  try {
    const response = await axios.put(`/graph/relationships/${id}`, relationship);
    return response.data;
  } catch (error) {
    console.error(`Error updating relationship with id ${id}:`, error);
    throw error;
  }
};

export const deleteRelationship = async (id: number): Promise<void> => {
  try {
    await axios.delete(`/graph/relationships/${id}`);
  } catch (error) {
    console.error(`Error deleting relationship with id ${id}:`, error);
    throw error;
  }
};

// Graph visualization
export const getGraphVisualizationData = async (): Promise<{nodes: NodeType[], relationships: RelationshipType[]}> => {
  try {
    const response = await axios.get('/graph/visualization');
    return response.data;
  } catch (error) {
    console.error('Error fetching visualization data:', error);
    throw error;
  }
};

// Graph search
export const searchGraph = async (query: string): Promise<{nodes: NodeType[], relationships: RelationshipType[]}> => {
  const response = await axios.get(`/graph/search?query=${encodeURIComponent(query)}`);
  return response.data;
};

// Relational Data API

// User operations
export const getUsers = async (): Promise<UserType[]> => {
  const response = await axios.get('/relational/users');
  return response.data;
};

export const getUserById = async (id: number): Promise<UserType> => {
  const response = await axios.get(`/relational/users/${id}`);
  return response.data;
};

export const createUser = async (user: UserType): Promise<UserType> => {
  const response = await axios.post('/relational/users', user);
  return response.data;
};

export const updateUser = async (id: number, user: UserType): Promise<UserType> => {
  const response = await axios.put(`/relational/users/${id}`, user);
  return response.data;
};

export const deleteUser = async (id: number): Promise<void> => {
  await axios.delete(`/relational/users/${id}`);
};

export const searchUsers = async (query: string): Promise<UserType[]> => {
  const response = await axios.get(`/relational/search?query=${encodeURIComponent(query)}&type=user`);
  return response.data;
};

// Project operations
export const getProjects = async (): Promise<ProjectType[]> => {
  const response = await axios.get('/relational/projects');
  return response.data;
};

export const getProjectById = async (id: number): Promise<ProjectType> => {
  const response = await axios.get(`/relational/projects/${id}`);
  return response.data;
};

export const getProjectsByUserId = async (userId: number): Promise<ProjectType[]> => {
  const response = await axios.get(`/relational/users/${userId}/projects`);
  return response.data;
};

export const createProject = async (project: ProjectType): Promise<ProjectType> => {
  const response = await axios.post('/relational/projects', project);
  return response.data;
};

export const updateProject = async (id: number, project: ProjectType): Promise<ProjectType> => {
  const response = await axios.put(`/relational/projects/${id}`, project);
  return response.data;
};

export const deleteProject = async (id: number): Promise<void> => {
  await axios.delete(`/relational/projects/${id}`);
};

export const searchProjects = async (query: string): Promise<ProjectType[]> => {
  const response = await axios.get(`/relational/search?query=${encodeURIComponent(query)}&type=project`);
  return response.data;
};

// Additional operations
export const getRecentProjects = async (): Promise<ProjectType[]> => {
  // We'll use the standard endpoint but we'll limit the results on the backend
  const response = await axios.get('/relational/projects');
  return response.data;
};
