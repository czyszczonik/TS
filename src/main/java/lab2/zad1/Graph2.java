package lab2.zad1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

class Graph2{
    static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = Graph1.getGraph();
        graph.setEdgeWeight(graph.addEdge(1, 20), 0.95);
        return graph;
    }
}
