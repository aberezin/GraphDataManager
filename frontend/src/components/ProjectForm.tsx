import React, { useState, useEffect } from 'react';
import { ProjectType, UserType } from '../types';

interface ProjectFormProps {
  project?: ProjectType;
  availableUsers: UserType[];
  onSubmit: (project: ProjectType) => void;
  onCancel: () => void;
}

const ProjectForm: React.FC<ProjectFormProps> = ({ 
  project, 
  availableUsers, 
  onSubmit, 
  onCancel 
}) => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [userId, setUserId] = useState<number | null>(null);

  useEffect(() => {
    if (project) {
      setName(project.name || '');
      setDescription(project.description || '');
      setUserId(project.user?.id || null);
    }
  }, [project]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const user = userId ? availableUsers.find(u => u.id === userId) || { id: userId } : null;
    
    onSubmit({
      id: project?.id,
      name,
      description,
      user: user || undefined,
      createdAt: project?.createdAt,
      updatedAt: new Date().toISOString()
    });
  };

  return (
    <form className="project-form" onSubmit={handleSubmit}>
      <h2>{project?.id ? 'Edit Project' : 'Create Project'}</h2>
      
      <div className="form-group">
        <label htmlFor="name">Name:</label>
        <input
          type="text"
          id="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="description">Description:</label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows={4}
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="userId">Assigned User:</label>
        <select
          id="userId"
          value={userId || ''}
          onChange={(e) => setUserId(e.target.value ? parseInt(e.target.value, 10) : null)}
        >
          <option value="">Unassigned</option>
          {availableUsers.map(user => (
            <option key={user.id} value={user.id}>
              {user.username} ({user.email})
            </option>
          ))}
        </select>
      </div>
      
      <div className="form-actions">
        <button type="submit" className="submit-btn">
          {project?.id ? 'Update' : 'Create'}
        </button>
        <button type="button" className="cancel-btn" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </form>
  );
};

export default ProjectForm;
