const express = require('express');
const app = express();
const PORT = 7687; // Neo4j Bolt protocol port

// In-memory storage for our graph database
const graphDB = {
  nodes: [
    { id: 1, label: 'Person', type: 'Person', properties: { name: 'John', age: 30 } },
    { id: 2, label: 'Person', type: 'Person', properties: { name: 'Alice', age: 28 } },
    { id: 3, label: 'Company', type: 'Organization', properties: { name: 'Acme Inc', founded: 2005 } }
  ],
  relationships: [
    { 
      id: 1, 
      type: 'WORKS_AT', 
      source: { id: 1, label: 'Person', type: 'Person' },
      target: { id: 3, label: 'Company', type: 'Organization' },
      properties: { since: 2015, position: 'Developer' } 
    },
    { 
      id: 2, 
      type: 'KNOWS', 
      source: { id: 1, label: 'Person', type: 'Person' },
      target: { id: 2, label: 'Person', type: 'Person' },
      properties: { since: 2018 } 
    }
  ]
};

// Middleware
app.use(express.json());

// Enable CORS
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  if (req.method === 'OPTIONS') {
    return res.sendStatus(200);
  }
  next();
});

// Basic Neo4j Bolt protocol emulation
app.get('/', (req, res) => {
  res.send('Neo4j-compatible service running');
});

// Handle Neo4j-style bolt protocol handshake (simple version)
app.post('/', (req, res) => {
  res.json({ 
    server: "Neo4j/4.4.0",
    connection_id: "bolt-" + Math.floor(Math.random() * 10000),
    status: "success"
  });
});

// Simple HTTP API endpoints for testing
app.get('/api/nodes', (req, res) => {
  res.json(graphDB.nodes);
});

app.get('/api/relationships', (req, res) => {
  res.json(graphDB.relationships);
});

app.get('/api/visualization', (req, res) => {
  res.json({
    nodes: graphDB.nodes,
    relationships: graphDB.relationships
  });
});

// Start the server
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Neo4j-compatible service running on http://0.0.0.0:${PORT}`);
});