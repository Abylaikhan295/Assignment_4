public class Main {

    public static void main(String[] args) {

        Experiment experiment = new Experiment();

        System.out.println("=".repeat(60));
        System.out.println("  DEMO: Small graph (10 vertices)");
        System.out.println("=".repeat(60));

        Graph small = new Graph();
        for (int i = 0; i < 10; i++) small.addVertex(new Vertex(i));

        int[][] demoEdges = {{0,1},{0,2},{1,3},{1,4},{2,4},{2,5},{3,6},{4,6},{4,7},{5,8},{7,9},{8,9}
        };
        for (int[] e : demoEdges) small.addEdge(e[0], e[1]);

        small.printGraph();
        System.out.println();
        experiment.runTraversals(small);
        experiment.runMultipleTests();
        experiment.printResults();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  BONUS: Dijkstra's Shortest Path Algorithm");
        System.out.println("=".repeat(60));

        Graph weighted = new Graph();
        for (int i = 0; i < 8; i++) weighted.addVertex(new Vertex(i));

        weighted.addEdge(0, 1, 4);
        weighted.addEdge(0, 2, 1);
        weighted.addEdge(2, 1, 2);
        weighted.addEdge(1, 3, 1);
        weighted.addEdge(2, 4, 5);
        weighted.addEdge(3, 4, 3);
        weighted.addEdge(3, 5, 2);
        weighted.addEdge(4, 6, 1);
        weighted.addEdge(5, 6, 4);
        weighted.addEdge(5, 7, 3);
        weighted.addEdge(6, 7, 2);

        System.out.println("\nWeighted graph structure:");
        weighted.printGraph();
        weighted.dijkstra(0);
    }
}