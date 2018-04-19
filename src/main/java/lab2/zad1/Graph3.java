package lab2.zad1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

class Graph3{
    static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = Graph2.getGraph();
        graph.setEdgeWeight(graph.addEdge(1, 10), 0.8);
        graph.setEdgeWeight(graph.addEdge(5, 15), 0.7);
        return graph;
    }
}
