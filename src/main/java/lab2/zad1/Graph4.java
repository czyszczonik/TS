package lab2.zad1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Random;

class Graph4{
    private final static Random random = new Random();
    static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        int edge1 = 0, edge2 = 0;
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = Graph3.getGraph();
        for (int iterator = 0; iterator < 4; iterator++) {
            while (edge1 == edge2 || graph.containsEdge(edge1, edge2) || graph.containsEdge(edge2, edge1)) {
                edge1 = getRandom(1, 20);
                edge2 = getRandom(1, 20);
            }
            graph.setEdgeWeight(graph.addEdge(edge1, edge2), 0.7);
        }
        return graph;
    }

    private static int getRandom(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
