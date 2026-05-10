import java.util.Random;

public class Experiment {

    private static final int SMALL  =  10;
    private static final int MEDIUM =  30;
    private static final int LARGE  = 100;

    private Graph buildRandomGraph(int size) {
        Graph graph = new Graph();
        Random rand = new Random(42);

        for (int i = 0; i < size; i++) {
            graph.addVertex(new Vertex(i));
        }

        for (int i = 0; i < size - 1; i++) {
            graph.addEdge(i, i + 1);
        }

        int extraEdges = size + size / 2;
        for (int i = 0; i < extraEdges; i++) {
            int from = rand.nextInt(size);
            int to   = rand.nextInt(size);
            if (from != to) graph.addEdge(from, to);
        }

        return graph;
    }

    public void runTraversals(Graph g) {
        g.bfs(0);
        g.dfs(0);
    }

    public void runMultipleTests() {
        int[] sizes = { SMALL, MEDIUM, LARGE };

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  PERFORMANCE EXPERIMENT");
        System.out.println("=".repeat(60));
        System.out.printf("%-12s %-10s %-18s %-18s%n",
                "Size", "Edges", "BFS time (ns)", "DFS time (ns)");
        System.out.println("-".repeat(60));

        for (int size : sizes) {
            Graph g = buildRandomGraph(size);

            long bfsTime = measureBfs(g, 0);
            long dfsTime = measureDfs(g, 0);

            System.out.printf("%-12d %-10d %-18d %-18d%n",
                    size, g.edgeCount(), bfsTime, dfsTime);
        }

        System.out.println("=".repeat(60));
    }

    private long measureBfs(Graph g, int start) {
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(java.io.OutputStream.nullOutputStream()));
        long startTime = System.nanoTime();
        g.bfs(start);
        long endTime = System.nanoTime();
        System.setOut(original);
        return endTime - startTime;
    }

    private long measureDfs(Graph g, int start) {
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(java.io.OutputStream.nullOutputStream()));
        long startTime = System.nanoTime();
        g.dfs(start);
        long endTime = System.nanoTime();
        System.setOut(original);
        return endTime - startTime;
    }

    public void printResults() {
        System.out.println("\n  ANALYSIS:");
        System.out.println("  Both BFS and DFS are O(V + E) — time grows linearly with graph size.");
        System.out.println("  BFS uses a Queue; DFS uses a Stack — both visit each vertex once.");
        System.out.println("  BFS finds shortest paths; DFS is better for connectivity/cycle checks.");
        System.out.println("  On a random graph, both traversals should have very similar timings.");
    }
}