package lab2.zad2;

import org.jgrapht.Graph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {
    private final Random generator = new Random();

    public void run(String fileLocation) {
        int all = 0;
        int passed = 0;
        GraphCreator graphCreator;
        try {
            graphCreator = new GraphCreator(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        SimpleGraph<Integer, CapacityEdge> graph;
        int[][] bandwidthMatrix = graphCreator.getBandwidthMatrix();
        List<Integer> delays = new ArrayList<>();
        int delay;
        for (int p = 1; p <= graphCreator.getIteration(); p++) {
            graph =  graphCreator.getEmptyGraph();
            breakEdges(graph,graphCreator.getReliability());
            if (isGraphConnected(graph)) {
                computeMatrix(graph,bandwidthMatrix);
                if (capacityTest(graph)) {
                    delay = computeDelay(graph,bandwidthMatrix,graphCreator.getPackageSize());
                    delays.add(delay);
                    if (delay < graphCreator.getMaxDelay()) {
                        passed++;
                    }
                }
            }
            all++;
        }
        System.out.println(passed * 100 / all + "%  " + passed + "/" + all);
        int avgDelay = (delays.parallelStream().mapToInt(Integer::intValue).sum()/delays.size());
        System.out.print("Avg Delay: " + avgDelay);

    }

    private boolean isGraphConnected(SimpleGraph<Integer,CapacityEdge> graph){
        ConnectivityInspector<Integer, CapacityEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.isGraphConnected();
    }

    private boolean capacityTest(Graph<Integer, CapacityEdge> graph) {
        for (CapacityEdge edge : graph.edgeSet()) {
            if (edge.getCapacity() > edge.getCapacityMax()) {
                return false;
            }
        }
        return true;
    }

    private void breakEdges(SimpleGraph<Integer,CapacityEdge> graph, double reliability){
        ArrayList<CapacityEdge> edges = new ArrayList<>(graph.edgeSet());
        for (CapacityEdge edge : edges) {
            double number = generator.nextDouble();
            if (number > reliability) {
                graph.removeEdge(edge);
            }
        }
    }

    private void computeMatrix(SimpleGraph<Integer,CapacityEdge> graph, int[][] matrix){
        int graphSize = graph.vertexSet().size();
        for (int i = 0; i < graphSize; i++) {
            for (int j = 0; j < graphSize; j++) {
                if (i == j) continue;
                int vertex1 = i + 1;
                int vertex2 = j + 1;
                int weight = matrix[i][j];
                List<CapacityEdge> list = new DijkstraShortestPath<Integer,CapacityEdge>(graph).getPath(vertex1, vertex2).getEdgeList();
                list.parallelStream().forEach(edge -> edge.setCapacity(edge.getCapacity() + weight));
            }
        }
    }

    private  int computeDelay(SimpleGraph<Integer,CapacityEdge> graph, int[][] bandwidthMatrix, double packageSize){
        int sumOfPackages = Arrays.stream(bandwidthMatrix).flatMapToInt(Arrays::stream).sum();
        double edgesSum = graph.edgeSet()
                .stream()
                .mapToDouble(myEdge -> ((myEdge.getCapacity() * packageSize) /
                        myEdge.getCapacityMax() - myEdge.getCapacity()))
                .sum();
        return (int) (edgesSum / sumOfPackages);
    }
}
