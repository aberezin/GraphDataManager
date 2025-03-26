import React, { useState, useEffect } from 'react';
import UserForm from '../components/UserForm';
import ProjectForm from '../components/ProjectForm';
import SearchBar from '../components/SearchBar';
import { 
  getUsers, 
  getProjects, 
  getProjectsByUserId,
  createUser, 
  updateUser, 
  deleteUser, 
  createProject, 
  updateProject, 
  deleteProject,
  searchUsers,
  searchProjects
} from '../services/api';
import { UserType, ProjectType } from '../types';

const RelationalData: React.FC = () => {
  const [users, setUsers] = useState<UserType[]>([]);
  const [projects, setProjects] = useState<ProjectType[]>([]);
  const [selectedUser, setSelectedUser] = useState<UserType | null>(null);
  const [selectedProject, setSelectedProject] = useState<ProjectType | null>(null);
  const [showUserForm, setShowUserForm] = useState(false);
  const [showProjectForm, setShowProjectForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<'users' | 'projects'>('users');
  const [currentSearchType, setCurrentSearchType] = useState<'user' | 'project'>('user');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const [usersData, projectsData] = await Promise.all([
        getUsers(),
        getProjects()
      ]);
      
      setUsers(usersData);
      setProjects(projectsData);
      setLoading(false);
    } catch (err) {
      console.error('Error fetching relational data:', err);
      setError('Failed to load relational data. Please try again later.');
      setLoading(false);
    }
  };

  const handleSearch = async (query: string) => {
    try {
      setLoading(true);
      
      if (currentSearchType === 'user') {
        const results = await searchUsers(query);
        setUsers(results);
        setActiveTab('users');
      } else {
        const results = await searchProjects(query);
        setProjects(results);
        setActiveTab('projects');
      }
      
      setLoading(false);
    } catch (err) {
      console.error('Error searching:', err);
      setError('Search failed. Please try again.');
      setLoading(false);
    }
  };

  // User operations
  const handleCreateUser = async (user: UserType) => {
    try {
      setLoading(true);
      const newUser = await createUser(user);
      setUsers(prev => [...prev, newUser]);
      setShowUserForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error creating user:', err);
      setError('Failed to create user. Please try again.');
      setLoading(false);
    }
  };

  const handleUpdateUser = async (user: UserType) => {
    if (!user.id) return;
    
    try {
      setLoading(true);
      const updatedUser = await updateUser(user.id, user);
      setUsers(prev => prev.map(u => u.id === updatedUser.id ? updatedUser : u));
      setSelectedUser(null);
      setShowUserForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error updating user:', err);
      setError('Failed to update user. Please try again.');
      setLoading(false);
    }
  };

  const handleDeleteUser = async (id: number) => {
    try {
      setLoading(true);
      await deleteUser(id);
      setUsers(prev => prev.filter(u => u.id !== id));
      
      // Update projects that belong to this user
      setProjects(prev => prev.map(p => 
        p.user?.id === id ? { ...p, user: undefined } : p
      ));
      
      setLoading(false);
    } catch (err) {
      console.error('Error deleting user:', err);
      setError('Failed to delete user. Please try again.');
      setLoading(false);
    }
  };

  // Project operations
  const handleCreateProject = async (project: ProjectType) => {
    try {
      setLoading(true);
      const newProject = await createProject(project);
      setProjects(prev => [...prev, newProject]);
      setShowProjectForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error creating project:', err);
      setError('Failed to create project. Please try again.');
      setLoading(false);
    }
  };

  const handleUpdateProject = async (project: ProjectType) => {
    if (!project.id) return;
    
    try {
      setLoading(true);
      const updatedProject = await updateProject(project.id, project);
      setProjects(prev => 
        prev.map(p => p.id === updatedProject.id ? updatedProject : p)
      );
      setSelectedProject(null);
      setShowProjectForm(false);
      setLoading(false);
    } catch (err) {
      console.error('Error updating project:', err);
      setError('Failed to update project. Please try again.');
      setLoading(false);
    }
  };

  const handleDeleteProject = async (id: number) => {
    try {
      setLoading(true);
      await deleteProject(id);
      setProjects(prev => prev.filter(p => p.id !== id));
      setLoading(false);
    } catch (err) {
      console.error('Error deleting project:', err);
      setError('Failed to delete project. Please try again.');
      setLoading(false);
    }
  };

  const handleUserProjectsView = async (userId: number) => {
    try {
      setLoading(true);
      const userProjects = await getProjectsByUserId(userId);
      setProjects(userProjects);
      setActiveTab('projects');
      setLoading(false);
    } catch (err) {
      console.error('Error fetching user projects:', err);
      setError('Failed to load user projects. Please try again.');
      setLoading(false);
    }
  };

  const handleUserSubmit = (user: UserType) => {
    if (user.id) {
      handleUpdateUser(user);
    } else {
      handleCreateUser(user);
    }
  };

  const handleProjectSubmit = (project: ProjectType) => {
    if (project.id) {
      handleUpdateProject(project);
    } else {
      handleCreateProject(project);
    }
  };

  return (
    <div className="relational-data-page">
      <h1>Relational Data Management</h1>
      
      <div className="search-container">
        <div className="search-type-selector">
          <button 
            className={`search-type-btn ${currentSearchType === 'user' ? 'active' : ''}`}
            onClick={() => setCurrentSearchType('user')}
          >
            Search Users
          </button>
          <button 
            className={`search-type-btn ${currentSearchType === 'project' ? 'active' : ''}`}
            onClick={() => setCurrentSearchType('project')}
          >
            Search Projects
          </button>
        </div>
        <SearchBar 
          onSearch={handleSearch} 
          placeholder={`Search ${currentSearchType === 'user' ? 'users' : 'projects'}...`} 
        />
      </div>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="tabs">
        <button 
          className={`tab ${activeTab === 'users' ? 'active' : ''}`}
          onClick={() => setActiveTab('users')}
        >
          Users
        </button>
        <button 
          className={`tab ${activeTab === 'projects' ? 'active' : ''}`}
          onClick={() => setActiveTab('projects')}
        >
          Projects
        </button>
      </div>
      
      <div className="tab-content">
        {activeTab === 'users' && (
          <div className="users-tab">
            <div className="controls">
              <button 
                className="add-button"
                onClick={() => {
                  setSelectedUser(null);
                  setShowUserForm(true);
                }}
              >
                Add New User
              </button>
              <button 
                className="refresh-button"
                onClick={() => fetchData()}
              >
                Refresh Data
              </button>
            </div>
            
            {showUserForm && (
              <div className="form-overlay">
                <div className="form-container">
                  <UserForm 
                    user={selectedUser || undefined}
                    onSubmit={handleUserSubmit}
                    onCancel={() => {
                      setSelectedUser(null);
                      setShowUserForm(false);
                    }}
                  />
                </div>
              </div>
            )}
            
            {loading ? (
              <div className="loading">Loading users...</div>
            ) : users.length === 0 ? (
              <div className="empty-state">No users available. Add a new user to get started.</div>
            ) : (
              <div className="user-list">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Username</th>
                      <th>Email</th>
                      <th>Name</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {users.map(user => (
                      <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                        <td>{`${user.firstName || ''} ${user.lastName || ''}`.trim() || '-'}</td>
                        <td>
                          <button 
                            className="view-projects-button"
                            onClick={() => handleUserProjectsView(user.id!)}
                          >
                            View Projects
                          </button>
                          <button 
                            className="edit-button"
                            onClick={() => {
                              setSelectedUser(user);
                              setShowUserForm(true);
                            }}
                          >
                            Edit
                          </button>
                          <button 
                            className="delete-button"
                            onClick={() => {
                              if (window.confirm('Are you sure you want to delete this user?')) {
                                handleDeleteUser(user.id!);
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
        
        {activeTab === 'projects' && (
          <div className="projects-tab">
            <div className="controls">
              <button 
                className="add-button"
                onClick={() => {
                  setSelectedProject(null);
                  setShowProjectForm(true);
                }}
              >
                Add New Project
              </button>
              <button 
                className="refresh-button"
                onClick={() => fetchData()}
              >
                Refresh Data
              </button>
            </div>
            
            {showProjectForm && (
              <div className="form-overlay">
                <div className="form-container">
                  <ProjectForm 
                    project={selectedProject || undefined}
                    availableUsers={users}
                    onSubmit={handleProjectSubmit}
                    onCancel={() => {
                      setSelectedProject(null);
                      setShowProjectForm(false);
                    }}
                  />
                </div>
              </div>
            )}
            
            {loading ? (
              <div className="loading">Loading projects...</div>
            ) : projects.length === 0 ? (
              <div className="empty-state">No projects available. Add a new project to get started.</div>
            ) : (
              <div className="project-list">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Description</th>
                      <th>Created</th>
                      <th>User</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {projects.map(project => (
                      <tr key={project.id}>
                        <td>{project.id}</td>
                        <td>{project.name}</td>
                        <td>{project.description || '-'}</td>
                        <td>{project.createdAt ? new Date(project.createdAt).toLocaleDateString() : '-'}</td>
                        <td>{project.user ? project.user.username : 'Unassigned'}</td>
                        <td>
                          <button 
                            className="edit-button"
                            onClick={() => {
                              setSelectedProject(project);
                              setShowProjectForm(true);
                            }}
                          >
                            Edit
                          </button>
                          <button 
                            className="delete-button"
                            onClick={() => {
                              if (window.confirm('Are you sure you want to delete this project?')) {
                                handleDeleteProject(project.id!);
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

export default RelationalData;
