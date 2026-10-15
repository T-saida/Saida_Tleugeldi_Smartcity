package experiment;

import graph.Edge;
import mst.KruskalMST;
import mst.PrimMST;
import shortestpath.DijkstraShortestPath;

import java.util.*;

public class ExperimentRunner {

    public static void main(String[] args) {
        System.out.println("=== Smart City Infrastructure: Algorithm Benchmarking ===");

        int[][] configs = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };

        for (int[] config : configs) {
            int V = config[0];
            int E = config[1];
            System.out.println("\nConfiguration: Vertices (V) = " + V + ", Edges (E) = " + E);

            long kruskalTimeSum = 0;
            long primTimeSum = 0;
            long dijkstraTimeSum = 0;

            for (int run = 0; run < 5; run++) {
                List<Edge> edges = generateRandomEdges(V, E, 42 + run);
                List<List<Edge>> adjList = buildAdjacencyList(V, edges);

                long startK = System.nanoTime();
                KruskalMST.run(V, edges);
                kruskalTimeSum += (System.nanoTime() - startK);

                long startP = System.nanoTime();
                PrimMST.run(V, adjList);
                primTimeSum += (System.nanoTime() - startP);

                long startD = System.nanoTime();
                DijkstraShortestPath.run(V, adjList, 0);
                dijkstraTimeSum += (System.nanoTime() - startD);
            }

            System.out.println("  Average Kruskal Time:  " + (kruskalTimeSum / 5) / 1_000_000.0 + " ms");
            System.out.println("  Average Prim Time:     " + (primStateFix(primTimeSum) / 5) / 1_000_000.0 + " ms"); // или просто primTimeSum
            System.out.println("  Average Dijkstra Time: " + (dijkstraTimeSum / 5) / 1_000_000.0 + " ms");
        }
    }

    private static long primStateFix(long val) {
        return val;
    }

    private static List<Edge> generateRandomEdges(int V, int E, long seed) {
        Random random = new Random(seed);
        List<Edge> edges = new ArrayList<>();
        Set<String> existing = new HashSet<>();

        for (int i = 1; i < V; i++) {
            int parent = random.nextInt(i);
            double weight = 1 + random.nextDouble() * 99;
            edges.add(new Edge(parent, i, weight));
            existing.add(parent + "-" + i);
        }

        while (edges.size() < E) {
            int u = random.nextInt(V);
            int v = random.nextInt(V);
            if (u == v) continue;
            String key = Math.min(u, v) + "-" + Math.max(u, v);
            if (!existing.contains(key)) {
                existing.add(key);
                double weight = 1 + random.nextDouble() * 99;
                edges.add(new Edge(u, v, weight));
            }
        }
        return edges;
    }

    private static List<List<Edge>> buildAdjacencyList(int V, List<Edge> edges) {
        List<List<Edge>> adjList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }
        for (Edge edge : edges) {
            adjList.get(edge.getSource()).add(edge);
            adjList.get(edge.getDestination()).add(new Edge(edge.getDestination(), edge.getSource(), edge.getWeight()));
        }
        return adjList;
    }
}