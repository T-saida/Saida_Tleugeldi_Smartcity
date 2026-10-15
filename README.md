# Smart City Infrastructure and Route Planning

## 1. Overview
This project implements a Java application that models a smart city's infrastructure network. The system represents city districts as vertices and roads/infrastructure connections as weighted edges. The application supports infrastructure planning (finding minimum-cost networks), connectivity analysis (topological sorting and strongly connected components), and route planning (shortest paths using Dijkstra's algorithm).

## 2. Implementation & Architecture
The project is structured into modular packages:
- **graph**: Defines core data structures including Edge and graph representations.
- **dsu**: Implements Disjoint-Set Union (Union-Find) with Path Compression and Union by Rank optimizations.
- **mst**: Implements Kruskal's algorithm (using DSU and edge sorting) and Prim's algorithm (using a priority queue).
- **connectivity**: Implements Topological Sorting (with cycle detection via DFS) and Kosaraju's algorithm for Strongly Connected Components (SCC).
- **shortestpath**: Implements Dijkstra's algorithm for single-source shortest paths.
- **experiment**: Contains benchmarking tools to evaluate algorithm performance across various graph sizes.

## 3. Complexity Analysis
- **DSU Operations (find & union)**: Amortized time complexity of O(alpha(V)) per operation due to path compression and union by rank, where alpha is the inverse Ackermann function.
- **Kruskal's Algorithm**: O(E log E) time complexity dominated by sorting the edges, combined with O(E alpha(V)) for DSU operations.
- **Prim's Algorithm**: O(E log V) time complexity using a binary min-heap (PriorityQueue) and adjacency list representation.
- **Strongly Connected Components (Kosaraju's)**: O(V + E) time complexity requiring two passes of Depth-First Search (DFS) and graph transposition.
- **Topological Sorting**: O(V + E) time complexity using DFS with recursion stack tracking.
- **Dijkstra's Algorithm**: O((V + E) log V) time complexity using a priority queue.

## 4. Experimental Setup
Experiments were conducted using three reproducible graph configurations generated with fixed random seeds:
1. Small: V = 10, E = 30
2. Medium: V = 100, E = 500
3. Large: V = 1,000, E = 5,000

Each experiment was repeated 5 times, and average execution times were measured using System.nanoTime().

## 5. Results

| Graph Size | Vertices (V) | Edges (E) | Kruskal Avg Time (ms) | Prim Avg Time (ms) | Dijkstra Avg Time (ms) |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Small** | 10 | 30 | 0.32 | 0.29 | 1.25 |
| **Medium** | 100 | 500 | 0.44 | 0.27 | 0.23 |
| **Large** | 1,000 | 5,000 | 3.37 | 2.03 | 2.30 |

## 6. Comparison and Discussion
- **Kruskal vs. Prim**: Both algorithms successfully computed the minimum-cost network. Prim's algorithm demonstrated slightly faster execution times on dense and medium graphs when implemented with a binary heap, whereas Kruskal's performance is heavily tied to the initial edge-sorting step (O(E log E)).
- **DSU Optimizations**: Path compression and union by rank flatten the tree structure during find operations, ensuring nearly constant-time performance in practice.
- **Dijkstra's Limitations**: Dijkstra's algorithm operates efficiently with non-negative edge weights but fails with negative weights because greedy selections cannot anticipate decreases in path costs caused by negative cycles.

## 7. Testing & Correctness
The implementation was rigorously tested against edge cases, verifying that:
- Kruskal and Prim yield identical total MST weights for connected undirected graphs.
- Cyclic graphs are correctly detected during topological sort attempts.
- Unreachable vertices are properly handled by Dijkstra's algorithm (returning Infinity).
- Every vertex belongs to exactly one SCC.

## 8. Conclusion
The developed smart city infrastructure application effectively models urban networks, providing robust tools for cost-effective cable/road placement, traffic dependency analysis, and optimal route planning. Prim and Kruskal prove optimal for MST tasks, while Dijkstra and DFS-based utilities handle routing and connectivity reliably.
