package lab2.zad1;

import org.jgrapht.Graph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Random;

class Test {

    private static final Random generator = new Random();

    @SuppressWarnings("unchecked")
    static void run(int graphType, int iterations) {
            int all = 0;
            int passed = 0;
            SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
            while (all!=iterations) {
                graph = getGraph(graphType);
                ArrayList<DefaultWeightedEdge> edges = new ArrayList<>(graph.edgeSet());
                for (DefaultWeightedEdge edge : edges) {
                    double weight = graph.getEdgeWeight(edge);
                    double number = generator.nextDouble();
                    if (number > weight) {
                        graph.removeEdge(edge);
                    }
                }
                if (isConnected(graph)) {
                    passed++;
                }
                all++;
            }
        System.out.println("Graph type = "+graphType);
        System.out.println(passed * 100 / all + "%  " + passed+"/" + all);
    }

    @SuppressWarnings("unchecked")
    private static boolean isConnected(Graph graph) {
        return new ConnectivityInspector<>(graph).isGraphConnected();
    }

    private static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph(int graphType){
        switch (graphType){
            case 1 :
                return Graph1.getGraph();
            case 2 :
                return Graph2.getGraph();
            case 3 :
                return Graph3.getGraph();
            case 4 :
                return Graph4.getGraph();
            case 5 :
                return MYG.getGraph();
            default:
                System.err.println("WRONG GRAPH TYPE!");
                return null;
        }
    }


}
