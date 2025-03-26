import React, { useState, useEffect } from 'react';
import { NodeType, RelationshipType } from '../types';

interface RelationshipFormProps {
  relationship?: RelationshipType;
  availableNodes: NodeType[];
  onSubmit: (relationship: RelationshipType) => void;
  onCancel: () => void;
}

const RelationshipForm: React.FC<RelationshipFormProps> = ({ 
  relationship, 
  availableNodes, 
  onSubmit, 
  onCancel 
}) => {
  const [type, setType] = useState('');
  const [sourceNodeId, setSourceNodeId] = useState<number | null>(null);
  const [targetNodeId, setTargetNodeId] = useState<number | null>(null);
  const [properties, setProperties] = useState<Array<{key: string, value: string}>>([{ key: '', value: '' }]);

  useEffect(() => {
    if (relationship) {
      setType(relationship.type || '');
      setSourceNodeId(relationship.source?.id || null);
      setTargetNodeId(relationship.target?.id || null);
      
      const props = relationship.properties ? 
        Object.entries(relationship.properties).map(([key, value]) => ({
          key,
          value: typeof value === 'string' ? value : JSON.stringify(value)
        })) : 
        [{ key: '', value: '' }];
      
      setProperties(props);
    }
  }, [relationship]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!sourceNodeId || !targetNodeId) {
      alert('Please select both source and target nodes');
      return;
    }
    
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
    
    const sourceNode = availableNodes.find(node => node.id === sourceNodeId);
    const targetNode = availableNodes.find(node => node.id === targetNodeId);
    
    onSubmit({
      id: relationship?.id,
      type,
      source: sourceNode || { id: sourceNodeId },
      target: targetNode || { id: targetNodeId },
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
    <form className="relationship-form" onSubmit={handleSubmit}>
      <h2>{relationship?.id ? 'Edit Relationship' : 'Create Relationship'}</h2>
      
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
        <label htmlFor="sourceNode">Source Node:</label>
        <select
          id="sourceNode"
          value={sourceNodeId || ''}
          onChange={(e) => setSourceNodeId(e.target.value ? parseInt(e.target.value, 10) : null)}
          required
        >
          <option value="">Select Source Node</option>
          {availableNodes.map(node => (
            <option key={`source-${node.id}`} value={node.id}>
              {node.label || `Node ${node.id}`} ({node.type})
            </option>
          ))}
        </select>
      </div>
      
      <div className="form-group">
        <label htmlFor="targetNode">Target Node:</label>
        <select
          id="targetNode"
          value={targetNodeId || ''}
          onChange={(e) => setTargetNodeId(e.target.value ? parseInt(e.target.value, 10) : null)}
          required
        >
          <option value="">Select Target Node</option>
          {availableNodes.map(node => (
            <option key={`target-${node.id}`} value={node.id}>
              {node.label || `Node ${node.id}`} ({node.type})
            </option>
          ))}
        </select>
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
          {relationship?.id ? 'Update' : 'Create'}
        </button>
        <button type="button" className="cancel-btn" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </form>
  );
};

export default RelationshipForm;
