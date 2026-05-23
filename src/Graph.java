import java.util.*;

public class Graph {

    private final Map<Integer, Vertex> vertices = new HashMap<>();

    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    private final Map<Integer, List<int[]>> weightedAdjacencyList = new HashMap<>();

    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjacencyList.put(v.getId(), new ArrayList<>());
            weightedAdjacencyList.put(v.getId(), new ArrayList<>());
        }
    }
    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }
    public void addEdge(int from, int to, int weight) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            System.out.println("Cannot add edge: vertex " + from + " or " + to + " not found.");
            return;
        }
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);

        weightedAdjacencyList.get(from).add(new int[]{to,   weight});
        weightedAdjacencyList.get(to).add(new int[]{from, weight});
    }
    public void printGraph() {
        System.out.println("Graph adjacency list (weighted):");
        List<Integer> sortedIds = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedIds);
        for (int id : sortedIds) {
            StringBuilder sb = new StringBuilder("  [" + id + "] -> [");
            List<int[]> neighbours = weightedAdjacencyList.get(id);
            for (int i = 0; i < neighbours.size(); i++) {
                int[] nb = neighbours.get(i);
                sb.append(nb[0]).append("(w=").append(nb[1]).append(")");
                if (i < neighbours.size() - 1) sb.append(", ");
            }
            sb.append("]");
            System.out.println(sb);
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
                if (!visited.contains(nb)) stack.push(nb);
            }
        }
        System.out.println();
    }
    public void dijkstra(int startId) {
        if (!vertices.containsKey(startId)) {
            System.out.println("Dijkstra: start vertex " + startId + " not found.");
            return;
        }

        int   n        = vertices.size();
        int[] dist     = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev     = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[startId] = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && vertices.containsKey(v)) {
                    if (u == -1 || dist[v] < dist[u]) u = v;
                }
            }

            if (u == -1 || dist[u] == Integer.MAX_VALUE) break;
            visited[u] = true;
            for (int[] edge : weightedAdjacencyList.get(u)) {
                int neighbour = edge[0];
                int weight    = edge[1];

                if (!visited[neighbour] && dist[u] + weight < dist[neighbour]) {
                    dist[neighbour] = dist[u] + weight;
                    prev[neighbour] = u;
                }
            }
        }
        System.out.println("\nDijkstra's shortest paths from vertex " + startId + ":");
        System.out.printf("  %-10s %-15s %-20s%n", "Vertex", "Distance", "Path");
        System.out.println("  " + "-".repeat(45));

        List<Integer> sortedIds = new ArrayList<>(vertices.keySet());
        Collections.sort(sortedIds);

        for (int v : sortedIds) {
            if (dist[v] == Integer.MAX_VALUE) {
                System.out.printf("  %-10d %-15s %-20s%n", v, "UNREACHABLE", "-");
            } else {
                System.out.printf("  %-10d %-15d %-20s%n", v, dist[v], buildPath(prev, startId, v));
            }
        }
        System.out.println();
    }
    private String buildPath(int[] prev, int startId, int targetId) {
        List<Integer> path = new ArrayList<>();
        for (int at = targetId; at != -1; at = prev[at]) {
            path.add(at);
            if (at == startId) break;
        }
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }
    public int vertexCount() { return vertices.size(); }

    public int edgeCount() {
        int total = 0;
        for (List<Integer> neighbours : adjacencyList.values()) total += neighbours.size();
        return total / 2;
    }
}
