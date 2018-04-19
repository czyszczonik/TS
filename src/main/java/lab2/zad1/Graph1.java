package lab2.zad1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

class Graph1{
    static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 1; i <= 20; i++) {
            graph.addVertex(i);
        }
        for (int i = 1; i < 20; i++) {
            graph.setEdgeWeight(graph.addEdge(i, i + 1), 0.95);
        }
        return graph;
    }
}
