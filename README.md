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

### Time and Space Complexity Summary
| Algorithm / Component | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| **DSU Operations (`find` & `union`)** | O(α(V)) amortized | O(V) |
| **Kruskal's Algorithm** | O(E log E) | O(V + E) |
| **Prim's Algorithm** | O(E log V) | O(V + E) |
| **Strongly Connected Components (Kosaraju's)** | O(V + E) | O(V + E) |
| **Topological Sorting** | O(V + E) | O(V) |
| **Dijkstra's Algorithm** | O((V + E) log V) | O(V + E) |

*Note: α(V) is the inverse Ackermann function, which remains ≤ 4 for all practical graph sizes.*

## 4. Experimental Setup
Experiments were conducted using three reproducible graph configurations generated with fixed random seeds:
1. **Small**: V = 10, E = 30
2. **Medium**: V = 100, E = 500
3. **Large**: V = 1,000, E = 5,000

Each experiment was repeated 5 times, and average execution times were measured using `System.nanoTime()`.

## 5. Results & Benchmarks

### Execution Time and DSU Operations
| Graph Size | Vertices (V) | Edges (E) | Kruskal Avg Time (ms) | Prim Avg Time (ms) | Dijkstra Avg Time (ms) | DSU Finds | DSU Unions |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Small** | 10 | 30 | 0.32 | 0.29 | 1.25 | 52 | 9 |
| **Medium** | 100 | 500 | 0.44 | 0.27 | 0.23 | 941 | 99 |
| **Large** | 1,000 | 5,000 | 3.37 | 2.03 | 2.30 | 9,871 | 999 |

### Strongly Connected Components (SCC) Results
Kosaraju's algorithm successfully decomposed the directed infrastructure graphs into maximal strongly connected subgraphs. The number of discovered components scales with graph size and density:
- **Small Graph (V=10, E=30)**: Discovered **3** distinct SCCs.
- **Medium Graph (V=100, E=500)**: Discovered **12** distinct SCCs.
- **Large Graph (V=1,000, E=5,000)**: Discovered **45** distinct SCCs, reflecting complex multi-cyclic dependencies.

### Topological Sorting Examples
- **DAG Example (A -> B -> C -> D)**: 
  - Valid Topological Order: `A -> B -> C -> D`
- **Cyclic Example (A -> B -> C -> A)**: 
  - Result: Cycle detected successfully via recursion stack tracking, topological sorting safely aborted.

## 6. Performance Plots
- **Execution Time vs. Number of Vertices**:
  ![Time vs Vertices]
  <img width="680" height="424" alt="image" src="https://github.com/user-attachments/assets/d8f5a004-7c03-4d4a-9186-1a68735af2e4" />

- **Execution Time vs. Number of Edges**:
  ![Time vs Edges]
  <img width="569" height="228" alt="image" src="https://github.com/user-attachments/assets/36937578-a5e4-4a75-927b-428c016fe3ff" />


## 7. Comparison and Discussion
- **Kruskal vs. Prim**: Both algorithms successfully computed the minimum-cost network. Prim's algorithm demonstrated slightly faster execution times on dense and medium graphs when implemented with a binary heap, whereas Kruskal's performance is heavily tied to the initial edge-sorting step (O(E log E)).
- **DSU Optimizations**: Path compression and union by rank flatten the tree structure during `find` operations, ensuring nearly constant-time amortized performance in practice.
- **Dijkstra's Limitation with Negative Weights**: Dijkstra's algorithm relies on the greedy property that path distances are monotonically non-decreasing when extracting from the priority queue. If negative edge weights are introduced, a newly visited vertex could theoretically be reached via a cheaper path later, violating the algorithm's core assumption and leading to incorrect distance calculations.

## 8. Testing & Correctness
The implementation was rigorously tested against edge cases, verifying that:
- Kruskal and Prim yield identical total MST weights for connected undirected graphs.
- Cyclic graphs are correctly detected during topological sort attempts.
- Unreachable vertices are properly handled by Dijkstra's algorithm (returning `Infinity`).
- Every vertex belongs to exactly one SCC.

## 9. Conclusion
The developed smart city infrastructure application effectively models urban networks, providing robust tools for cost-effective cable/road placement, traffic dependency analysis, and optimal route planning. Prim and Kruskal prove optimal for MST tasks, while Dijkstra and DFS-based utilities handle routing and connectivity reliably.
