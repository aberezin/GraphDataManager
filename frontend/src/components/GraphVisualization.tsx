import React, { useEffect, useRef } from 'react';
import * as d3 from 'd3';
import { NodeType, RelationshipType } from '../types';

interface GraphVisualizationProps {
  nodes: NodeType[];
  relationships: RelationshipType[];
  width?: number;
  height?: number;
}

const GraphVisualization: React.FC<GraphVisualizationProps> = ({
  nodes,
  relationships,
  width = 800,
  height = 600
}) => {
  const svgRef = useRef<SVGSVGElement>(null);

  useEffect(() => {
    if (!nodes.length || !svgRef.current) return;

    // Clear existing visualization
    d3.select(svgRef.current).selectAll("*").remove();

    // Create the simulation
    const simulation = d3.forceSimulation(nodes as d3.SimulationNodeDatum[])
      .force('link', d3.forceLink(relationships)
        .id((d: any) => d.id)
        .distance(100))
      .force('charge', d3.forceManyBody().strength(-300))
      .force('center', d3.forceCenter(width / 2, height / 2))
      .force('collision', d3.forceCollide().radius(50));

    // Create the SVG container
    const svg = d3.select(svgRef.current)
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', [0, 0, width, height])
      .attr('style', 'max-width: 100%; height: auto;');

    // Add zoom behavior
    const zoom = d3.zoom()
      .scaleExtent([0.1, 4])
      .on('zoom', (event) => {
        g.attr('transform', event.transform);
      });

    svg.call(zoom as any);

    // Create a container for the graph
    const g = svg.append('g');

    // Create the links
    const link = g.append('g')
      .attr('stroke', '#999')
      .attr('stroke-opacity', 0.6)
      .selectAll('line')
      .data(relationships)
      .join('line')
      .attr('stroke-width', 2);

    // Create the nodes
    const node = g.append('g')
      .selectAll('.node')
      .data(nodes)
      .join('g')
      .attr('class', 'node')
      .call(drag(simulation) as any);

    // Add circles to nodes
    node.append('circle')
      .attr('r', 10)
      .attr('fill', (d: any) => getNodeColor(d.type));

    // Add labels to nodes
    node.append('text')
      .attr('dx', 15)
      .attr('dy', 4)
      .text((d: any) => d.label || `Node ${d.id}`)
      .attr('font-size', '12px');

    // Add title for hover
    node.append('title')
      .text((d: any) => JSON.stringify(d.properties, null, 2));

    // Add relationship labels
    g.append('g')
      .selectAll('.link-label')
      .data(relationships)
      .join('text')
      .attr('class', 'link-label')
      .attr('font-size', '8px')
      .attr('text-anchor', 'middle')
      .text((d: any) => d.type);

    // Update positions on simulation tick
    simulation.on('tick', () => {
      link
        .attr('x1', (d: any) => d.source.x)
        .attr('y1', (d: any) => d.source.y)
        .attr('x2', (d: any) => d.target.x)
        .attr('y2', (d: any) => d.target.y);

      node.attr('transform', (d: any) => `translate(${d.x},${d.y})`);

      g.selectAll('.link-label')
        .attr('x', (d: any) => (d.source.x + d.target.x) / 2)
        .attr('y', (d: any) => (d.source.y + d.target.y) / 2);
    });

    // Set initial zoom level
    svg.call(zoom.transform as any, d3.zoomIdentity.scale(0.8).translate(width / 4, height / 4));

    // Implement drag behavior
    function drag(simulation: d3.Simulation<d3.SimulationNodeDatum, undefined>) {
      function dragstarted(event: any, d: any) {
        if (!event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(event: any, d: any) {
        d.fx = event.x;
        d.fy = event.y;
      }

      function dragended(event: any, d: any) {
        if (!event.active) simulation.alphaTarget(0);
        d.fx = null;
        d.fy = null;
      }

      return d3.drag()
        .on('start', dragstarted)
        .on('drag', dragged)
        .on('end', dragended);
    }

    // Color mapping based on node type
    function getNodeColor(type: string): string {
      const colorMap: Record<string, string> = {
        'Person': '#4285F4',
        'Location': '#34A853',
        'Event': '#FBBC05',
        'Organization': '#EA4335',
        'default': '#7B68EE'
      };
      
      return colorMap[type] || colorMap.default;
    }

    // Cleanup
    return () => {
      simulation.stop();
    };
  }, [nodes, relationships, width, height]);

  return (
    <div className="graph-visualization">
      <svg ref={svgRef} />
    </div>
  );
};

export default GraphVisualization;
