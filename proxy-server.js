const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const path = require('path');

const app = express();
const PORT = 3000;

// Logging middleware
app.use((req, res, next) => {
  console.log(`Incoming request: ${req.method} ${req.url}`);
  next();
});

// Proxy middleware for API requests
const apiProxy = createProxyMiddleware({
  target: 'http://localhost:8080',
  changeOrigin: true,
  logLevel: 'debug',
  // Don't rewrite paths - we want to keep the /api prefix
  pathRewrite: null,
  onError: (err, req, res) => {
    console.error('Proxy error:', err);
    res.status(500).json({ error: 'Proxy error', message: err.message });
  }
});

// Also catch requests to /graph and /relational directly and redirect them to /api prefix
app.use('/graph', (req, res) => {
  console.log(`Redirecting ${req.url} to /api${req.url}`);
  res.redirect(`/api/graph${req.url.replace('/graph', '')}`);
});

app.use('/relational', (req, res) => {
  console.log(`Redirecting ${req.url} to /api${req.url}`);
  res.redirect(`/api/relational${req.url.replace('/relational', '')}`);
});

// Use the proxy middleware
app.use('/api', apiProxy);

// Redirect all traffic to the React development server
app.all('*', createProxyMiddleware({
  target: 'http://localhost:5000',
  changeOrigin: true,
  ws: true,
  logLevel: 'debug',
  onError: (err, req, res) => {
    console.error('Frontend proxy error:', err);
    res.status(500).send('Proxy error connecting to React app');
  }
}));

app.listen(PORT, '0.0.0.0', () => {
  console.log(`Proxy server running at http://0.0.0.0:${PORT}`);
});