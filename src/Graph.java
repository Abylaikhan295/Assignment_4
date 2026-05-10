import java.util.*;

public class Graph {

    private final Map<Integer, Vertex> vertices = new HashMap<>();

    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjacencyList.put(v.getId(), new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            System.out.println("Cannot add edge: vertex " + from + " or " + to + " not found.");
            return;
        }
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public void printGraph() {
        System.out.println("Graph adjacency list:");
        List<Integer> sortedIds = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedIds);
        for (int id : sortedIds) {
            System.out.println("  [" + id + "] -> " + adjacencyList.get(id));
        }
    }

    public void bfs(int startId) {
        if (!vertices.containsKey(startId)) {
            System.out.println("BFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Integer>   visited = new HashSet<>();
        Queue<Integer> queue   = new LinkedList<>();

        queue.add(startId);
        visited.add(startId);

        System.out.print("BFS traversal from " + startId + ": ");

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            for (int neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        System.out.println();
    }

    public void dfs(int startId) {
        if (!vertices.containsKey(startId)) {
            System.out.println("DFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Integer>   visited = new HashSet<>();
        Deque<Integer> stack   = new ArrayDeque<>();

        stack.push(startId);

        System.out.print("DFS traversal from " + startId + ": ");

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (visited.contains(current)) continue;

            visited.add(current);
            System.out.print(current + " ");

            List<Integer> neighbours = adjacencyList.get(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                int nb = neighbours.get(i);
                if (!visited.contains(nb)) {
                    stack.push(nb);
                }
            }
        }
        System.out.println();
    }

    public int vertexCount() { return vertices.size(); }

    public int edgeCount() {
        int total = 0;
        for (List<Integer> neighbours : adjacencyList.values()) {
            total += neighbours.size();
        }
        return total / 2;
    }
}
 