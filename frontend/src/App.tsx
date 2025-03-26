import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import GraphData from './pages/GraphData';
import RelationalData from './pages/RelationalData';

const App: React.FC = () => {
  return (
    <div className="app">
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/graph-data" element={<GraphData />} />
          <Route path="/relational-data" element={<RelationalData />} />
        </Routes>
      </main>
    </div>
  );
};

export default App;
