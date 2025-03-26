import axios from 'axios';
import { NodeType, RelationshipType, UserType, ProjectType } from '../types';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

// Configure axios defaults
axios.defaults.baseURL = API_BASE_URL;
axios.defaults.headers.common['Content-Type'] = 'application/json';

// Graph Data API

// Node operations
export const getNodes = async (): Promise<NodeType[]> => {
  const response = await axios.get('/graph/nodes');
  return response.data;
};

export const getNodeById = async (id: number): Promise<NodeType> => {
  const response = await axios.get(`/graph/nodes/${id}`);
  return response.data;
};

export const createNode = async (node: NodeType): Promise<NodeType> => {
  const response = await axios.post('/graph/nodes', node);
  return response.data;
};

export const updateNode = async (id: number, node: NodeType): Promise<NodeType> => {
  const response = await axios.put(`/graph/nodes/${id}`, node);
  return response.data;
};

export const deleteNode = async (id: number): Promise<void> => {
  await axios.delete(`/graph/nodes/${id}`);
};

// Relationship operations
export const getRelationships = async (): Promise<RelationshipType[]> => {
  const response = await axios.get('/graph/relationships');
  return response.data;
};

export const getRelationshipById = async (id: number): Promise<RelationshipType> => {
  const response = await axios.get(`/graph/relationships/${id}`);
  return response.data;
};

export const createRelationship = async (relationship: RelationshipType): Promise<RelationshipType> => {
  const response = await axios.post('/graph/relationships', relationship);
  return response.data;
};

export const updateRelationship = async (id: number, relationship: RelationshipType): Promise<RelationshipType> => {
  const response = await axios.put(`/graph/relationships/${id}`, relationship);
  return response.data;
};

export const deleteRelationship = async (id: number): Promise<void> => {
  await axios.delete(`/graph/relationships/${id}`);
};

// Graph visualization
export const getGraphVisualizationData = async (): Promise<{nodes: NodeType[], relationships: RelationshipType[]}> => {
  const response = await axios.get('/graph/visualization');
  return response.data;
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
