// Graph data types
export interface NodeType {
  id?: number;
  label?: string;
  type?: string;
  properties?: Record<string, any>;
}

export interface RelationshipType {
  id?: number;
  type?: string;
  source?: NodeType;
  target?: NodeType;
  properties?: Record<string, any>;
}

// Relational data types
export interface UserType {
  id?: number;
  username?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  projects?: ProjectType[];
}

export interface ProjectType {
  id?: number;
  name?: string;
  description?: string;
  createdAt?: string;
  updatedAt?: string;
  user?: UserType;
}

// Search result types
export interface GraphSearchResult {
  nodes: NodeType[];
  relationships: RelationshipType[];
}

export interface RelationalSearchResult<T> {
  results: T[];
}
