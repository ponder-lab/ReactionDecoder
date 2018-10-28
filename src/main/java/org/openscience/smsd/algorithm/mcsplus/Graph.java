/*
 * Copyright (c) 2018. BioInception Labs Pvt. Ltd.
 */
package org.openscience.smsd.algorithm.mcsplus;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Syed Asad Rahman <asad.rahman at bioinceptionlabs.com>
 */
public final class Graph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final TreeMap<Vertex, Set<Vertex>> adj;
    private final Set<Edge> edges;
    private final Set<Vertex> vertices;

    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges. param V
     * the number of vertices
     *
     */
    public Graph() {
        this.edges = new HashSet<>();
        this.vertices = new HashSet<>();
        this.adj = new TreeMap<>();
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return vertices.size();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return edges.size();
    }

    /**
     * Returns Nodes in this graph.
     *
     * @return vertices in this graph
     */
    public Set<Vertex> nodes() {
        Set<Vertex> nodes = new HashSet<>();
        nodes.addAll(vertices);
        return nodes;
    }

    /**
     * Returns edges in this graph.
     *
     * @return edges in this graph
     */
    public Set<Edge> edges() {
        Set<Edge> nodes = new HashSet<>();
        nodes.addAll(edges);
        return nodes;
    }

    private void validateVertex(Vertex v) {
        if (!vertices.contains(v)) {
            throw new IllegalArgumentException("vertex " + v + " not found in the graph");
        }
    }

    /**
     * Adds the undirected edge v-w to this graph. Assumes that the nodes
     * assigned in the edge is already present
     *
     * @param e edge to be added
     * @param directed
     */
    public void addEdge(Edge e, boolean directed) {
        validateVertex(e.getSource());
        validateVertex(e.getSink());
        adj.get(e.getSource()).add(e.getSink());
        edges.add(e);
        if (!directed) {
            Edge edgeRev = new Edge(e.getSink(), e.getSource());
            edges.add(edgeRev);
            adj.get(e.getSink()).add(e.getSource());
        }
    }

    /**
     * Adds Vertex to this graph.
     *
     * @param node Vertex to be added
     */
    public void addNode(Vertex node) {
        if (!adj.containsKey(node)) {
            adj.put(node, new TreeSet<>());
            vertices.add(node);
        } else {
            throw new IllegalArgumentException("Node " + node + " found in the graph");
        }
    }

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     */
    public Set<Vertex> getNeighbours(Vertex v) {
        validateVertex(v);
        return new TreeSet<>(adj.get(v));
    }

    /**
     * Returns the getDegree of vertex {@code v}.
     *
     * @param v the vertex
     * @return the getDegree of vertex {@code v}
     */
    public int getDegree(Vertex v) {
        validateVertex(v);
        return adj.get(v).size();
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of
     * edges <em>E</em>, followed by the <em>V</em> adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertices.size()).append(" vertices, ").append(edges.size()).append(" edges ").append(NEWLINE);
        adj.entrySet().stream().map((m) -> {
            s.append(m.getKey()).append(": ");
            return m;
        }).map((m) -> {
            m.getValue().forEach((w) -> {
                s.append(w).append(" ");
            });
            return m;
        }).forEachOrdered((_item) -> {
            s.append(NEWLINE);
        });
        return s.toString();
    }

    /**
     * Clean graph
     */
    public void clear() {
        this.edges.clear();
        this.vertices.clear();
        this.adj.clear();
    }

    /**
     *
     * @param u
     * @param v
     * @return if an edge exists between vertex
     */
    public boolean hasEdge(Vertex u, Vertex v) {
        return adj.containsKey(u) && adj.get(u).contains(v) ? true
                : adj.containsKey(v) && adj.get(v).contains(u);
    }

    /**
     * Returns edges of the vertex
     *
     * @param currentVertex
     * @return
     */
    public Iterable<Edge> edgesOf(Vertex currentVertex) {
        Set<Edge> edgesOfVertex = new LinkedHashSet<>();
        for (Edge e : edges) {
            if (e.getSource().equals(currentVertex) || e.getSink().equals(currentVertex)) {
                edgesOfVertex.add(e);
            }
        }
        return edgesOfVertex;
    }

    /**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise returns null. If
     * any of the specified vertices is null returns null In undirected graphs,
     * the returned edge may have its source and target vertices in the opposite
     * order.
     *
     * @param edge
     * @return
     */
    public Vertex getEdgeSource(Edge edge) {
        return edge.getSource();
    }

    /**
     * Returns the target vertex of an edge. For an undirected graph, source and
     * target are distinguishable designations (but without any mathematical
     * meaning)
     *
     * @param edge
     * @return
     */
    public Vertex getEdgeTarget(Edge edge) {
        return edge.getSink();
    }
}