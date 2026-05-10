Full Name: Abylaikhan Zhumagul

Group: IT-2502

Project Overview:

This project implements a graph traversal and representation system in Java. A **graph** is a data structure made up of **vertices** (nodes) and **edges** (connections between nodes). Graphs are used to model real-world relationships such as social networks, road maps, internet routing, and dependency trees.

### What are vertices and edges?

- A **vertex** (or node) is a single point in the graph — for example, a city on a map or a user in a social network.
- An **edge** is a connection between two vertices — for example, a road between two cities or a friendship between two users.
- This project uses an **undirected** graph, meaning every edge goes both ways (A connects to B and B connects to A).

### Purpose of the experiment

The goal is to implement two fundamental graph traversal algorithms — **Breadth-First Search (BFS)** and **Depth-First Search (DFS)** — and compare their behaviour and performance across graphs of different sizes (10, 30, and 100 vertices). The project also explores how each algorithm explores the graph structure differently, and what situations favour one over the other.

---

## Class Descriptions

### `Vertex`
Represents a single node in the graph. Each vertex has a unique integer `id` that identifies it. The class provides a constructor, a getter for the id, and a `toString()` method for readable output.

### `Edge`
Represents a directed connection between two vertices — a `source` and a `destination`. Since this project uses an undirected graph, every logical connection is stored as two edge entries (one in each direction). The class provides a constructor, getters, and a `toString()` method.

### `Graph`
The core data structure of the project, represented using an **adjacency list** implemented as a `HashMap<Integer, List<Integer>>`. Each key is a vertex ID and its value is a list of neighbouring vertex IDs.

**Why an adjacency list?**
- Space: O(V + E) — much more efficient than an adjacency matrix O(V²) for sparse graphs.
- Access: O(degree) to iterate over a vertex's neighbours, which is exactly what BFS and DFS require.
- Flexibility: vertices and edges can be added dynamically at runtime.

Key methods:
- `addVertex(Vertex v)` — registers a vertex and initialises its neighbour list.
- `addEdge(int from, int to)` — adds an undirected edge (inserts both directions).
- `printGraph()` — prints the full adjacency list to the console.
- `bfs(int start)` — performs Breadth-First Search from the given vertex.
- `dfs(int start)` — performs Depth-First Search from the given vertex.

### `Experiment`
Handles graph construction, traversal execution, and timing. It builds random connected graphs of sizes 10, 30, and 100, measures BFS and DFS with `System.nanoTime()`, and prints a formatted results table with analysis.

### `Main`
The program entry point. Creates a 10-vertex demo graph, prints its adjacency list, runs BFS and DFS to show traversal order, then calls the full performance experiment.

---

## Algorithm Descriptions

### Breadth-First Search (BFS)

**How it works:**

BFS explores a graph level by level, starting from the source vertex. It uses a **Queue (FIFO)** to track which vertices to visit next. The algorithm:

1. Enqueue the start vertex and mark it as visited.
2. Dequeue the front vertex and process it.
3. Enqueue all unvisited neighbours of the current vertex.
4. Repeat steps 2–3 until the queue is empty.

Because BFS always processes the closest vertices first, it naturally finds the **shortest path** (fewest edges) from the start vertex to any other reachable vertex.

**Traversal output from the experiment:**
```
BFS traversal from 0: 0 1 2 3 4 5 6 7 8 9
```
BFS visits vertices in strict distance order — all vertices at distance 1 from vertex 0 (vertices 1 and 2) before moving to distance 2 (vertices 3, 4, 5), and so on. The result is a clean sequential sweep of the graph.

**Use cases:**
- Finding the shortest path in an unweighted graph (GPS routing, degrees of separation).
- Level-order traversal of trees.
- Checking if a graph is bipartite.
- Web crawlers that explore pages layer by layer.

**Time complexity:** O(V + E) — every vertex and every edge is processed exactly once.  
**Space complexity:** O(V) — the queue holds at most V vertices at a time.

---

### Depth-First Search (DFS)

**How it works:**

DFS explores a graph by going as deep as possible along each branch before backtracking. It uses a **Stack (LIFO)** — either an explicit stack or recursion. The algorithm:

1. Push the start vertex onto the stack.
2. Pop the top vertex; if not yet visited, mark it and process it.
3. Push all unvisited neighbours onto the stack.
4. Repeat steps 2–3 until the stack is empty.

DFS commits to one path until it can go no further, then backtracks and tries another branch.

**Traversal output from the experiment:**
```
DFS traversal from 0: 0 1 3 6 4 2 5 8 9 7
```
DFS immediately dives: from 0 → 1 → 3 → 6, going as deep as possible before backtracking to explore the remaining branches. This is very different from BFS's layer-by-layer approach on the same graph.

**Use cases:**
- Detecting cycles in a graph.
- Topological sorting (e.g. task scheduling with dependencies).
- Finding connected components.
- Solving mazes and path problems where exploring one route fully makes sense.

**Limitations of DFS:**
- Does **not** guarantee the shortest path between two vertices.
- May take a long detour to find a nearby target if it goes down a long wrong branch first.
- Recursive DFS can cause a stack overflow on very deep graphs (this implementation uses an explicit stack to prevent this).

**Time complexity:** O(V + E) — every vertex and every edge is processed exactly once.  
**Space complexity:** O(V) — the stack holds at most V vertices at a time.

---

## Experimental Results

All experiments were run on randomly generated connected graphs. Each graph is first built as a chain (guaranteeing full connectivity), then extra random edges are added. Times are measured in nanoseconds using `System.nanoTime()`. The program exited with code 0, confirming successful execution with no errors.

### Adjacency list — small graph (10 vertices) and Traversal order (10-vertex graph, starting from vertex 0)
<img width="512" height="401" alt="image" src="https://github.com/user-attachments/assets/4d2d46f8-192b-4bea-a562-943519a5d1f9" />

### Execution time comparison

| Graph size (vertices) | Edges | BFS time (ns) | DFS time (ns) |
|-----------------------|-------|---------------|---------------|
| 10                    | 23    | 787,400       | 439,500       |
| 30                    | 71    | 959,200       | 1,061,000     |
| 100                   | 246   | 3,563,900     | 4,615,400     |

### Observations and patterns

- **Both algorithms scale linearly with graph size**, consistent with the expected O(V + E) complexity. From size 10 to size 100 (10× more vertices and ~10× more edges), BFS time grew roughly 4.5× and DFS roughly 10.5×, both remaining in the linear range.

- **DFS was significantly faster on the 10-vertex graph** (439,500 ns vs BFS's 787,400 ns — about 44% faster). This is likely due to JVM warm-up effects on the first run, where BFS incurs extra overhead initialising the `LinkedList` queue.

- **The results flip at size 30 and 100.** At 30 vertices, DFS became slightly slower (1,061,000 ns vs BFS's 959,200 ns). At 100 vertices, DFS was noticeably slower (4,615,400 ns vs BFS's 3,563,900 ns — about 29% slower). This suggests that as the graph grows, the `ArrayDeque` stack in DFS requires more memory reallocations than the `LinkedList` queue in BFS, which distributes allocation cost more evenly.

- **The traversal orders confirm the algorithms work correctly.** BFS produces 0 1 2 3 4 5 6 7 8 9 — a perfectly ordered layer-by-layer sweep. DFS produces 0 1 3 6 4 2 5 8 9 7 — diving immediately along 0→1→3→6 before backtracking. Both visit all 10 vertices exactly once.

- **Graph density (edges) grows proportionally with vertex count.** Edges grew from 23 (size 10) to 71 (size 30) to 246 (size 100), confirming the O(V + E) relationship holds in the experimental setup.

---


## Reflection

### What I learned about graph traversal

Working on this assignment gave me a much clearer understanding of how graphs are structured and why the choice of traversal algorithm matters. Before implementing them, I thought of BFS and DFS as just two different orderings of the same process. But seeing the actual output made the difference concrete — BFS produced the clean sequence 0 1 2 3 4 5 6 7 8 9 on the 10-vertex graph, visiting nodes in perfect distance order, while DFS produced 0 1 3 6 4 2 5 8 9 7, immediately diving down the 0→1→3→6 path before backtracking. That difference in output comes entirely from their data structures: BFS uses a queue, which forces it to finish one distance layer before going further, while DFS uses a stack, which lets it commit fully to one direction first. Understanding this made it clear why BFS is the right tool when you need the shortest path, and why DFS is better when you need to explore structure — cycles, connectivity, or reachability through deep paths.

Implementing the adjacency list was also a valuable insight. It is a far more efficient representation for sparse graphs than an adjacency matrix. For the 100-vertex graph with only 246 edges, an adjacency matrix would allocate space for all 10,000 possible vertex pairs and use fewer than 5% of those slots. The `HashMap<Integer, List<Integer>>` structure used here only stores connections that actually exist, keeping memory usage proportional to the real graph density.

### Differences between theory and practice

Theoretically, BFS and DFS have identical complexity — O(V + E) time and O(V) space. In practice, the experimental results showed interesting differences. At 10 vertices, DFS was around 44% faster than BFS, likely due to JVM warm-up effects on the first measurement. At 30 and 100 vertices, BFS became consistently faster, with DFS taking about 29% longer at size 100. This kind of result is a useful reminder that Big-O notation describes growth rate, not absolute speed — constant factors such as data structure implementation, memory allocation patterns, and JVM optimisation all affect real timings. The key takeaway is that both algorithms are highly practical: even at 100 vertices with 246 edges, both completed in under 5 milliseconds, confirming that O(V + E) graph traversal is efficient even without JVM tuning or warm-up.
