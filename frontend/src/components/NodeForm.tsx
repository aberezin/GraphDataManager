import React, { useState, useEffect } from 'react';
import { NodeType } from '../types';

interface NodeFormProps {
  node?: NodeType;
  onSubmit: (node: NodeType) => void;
  onCancel: () => void;
}

const NodeForm: React.FC<NodeFormProps> = ({ node, onSubmit, onCancel }) => {
  const [label, setLabel] = useState('');
  const [type, setType] = useState('');
  const [properties, setProperties] = useState<Array<{key: string, value: string}>>([{ key: '', value: '' }]);

  useEffect(() => {
    if (node) {
      setLabel(node.label || '');
      setType(node.type || '');
      
      const props = node.properties ? 
        Object.entries(node.properties).map(([key, value]) => ({
          key,
          value: typeof value === 'string' ? value : JSON.stringify(value)
        })) : 
        [{ key: '', value: '' }];
      
      setProperties(props);
    }
  }, [node]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const propertyObject: Record<string, any> = {};
    properties.forEach(prop => {
      if (prop.key && prop.value) {
        // Try to parse as JSON if it looks like an object or array
        try {
          if ((prop.value.startsWith('{') && prop.value.endsWith('}')) || 
              (prop.value.startsWith('[') && prop.value.endsWith(']'))) {
            propertyObject[prop.key] = JSON.parse(prop.value);
          } else {
            propertyObject[prop.key] = prop.value;
          }
        } catch {
          propertyObject[prop.key] = prop.value;
        }
      }
    });
    
    onSubmit({
      id: node?.id,
      label,
      type,
      properties: propertyObject
    });
  };

  const handlePropertyChange = (index: number, field: 'key' | 'value', value: string) => {
    const newProperties = [...properties];
    newProperties[index][field] = value;
    setProperties(newProperties);
    
    // Add a new empty row if the last row is being edited
    if (index === properties.length - 1 && (newProperties[index].key || newProperties[index].value)) {
      setProperties([...newProperties, { key: '', value: '' }]);
    }
  };

  const removeProperty = (index: number) => {
    if (properties.length > 1) {
      const newProperties = [...properties];
      newProperties.splice(index, 1);
      setProperties(newProperties);
    }
  };

  return (
    <form className="node-form" onSubmit={handleSubmit}>
      <h2>{node?.id ? 'Edit Node' : 'Create Node'}</h2>
      
      <div className="form-group">
        <label htmlFor="label">Label:</label>
        <input
          type="text"
          id="label"
          value={label}
          onChange={(e) => setLabel(e.target.value)}
          required
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="type">Type:</label>
        <input
          type="text"
          id="type"
          value={type}
          onChange={(e) => setType(e.target.value)}
          required
        />
      </div>
      
      <div className="form-group">
        <label>Properties:</label>
        {properties.map((prop, index) => (
          <div className="property-row" key={index}>
            <input
              type="text"
              placeholder="Key"
              value={prop.key}
              onChange={(e) => handlePropertyChange(index, 'key', e.target.value)}
            />
            <input
              type="text"
              placeholder="Value"
              value={prop.value}
              onChange={(e) => handlePropertyChange(index, 'value', e.target.value)}
            />
            {index !== properties.length - 1 && (
              <button 
                type="button" 
                className="remove-btn"
                onClick={() => removeProperty(index)}
              >
                X
              </button>
            )}
          </div>
        ))}
      </div>
      
      <div className="form-actions">
        <button type="submit" className="submit-btn">
          {node?.id ? 'Update' : 'Create'}
        </button>
        <button type="button" className="cancel-btn" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </form>
  );
};

export default NodeForm;
