package shortestpath;

import graph.Edge;
import java.util.*;

public class DijkstraShortestPath {

    public static class Result {
        public double[] distances;
        public int[] predecessors;
        public int operationsCount;

        public Result(double[] distances, int[] predecessors, int operationsCount) {
            this.distances = distances;
            this.predecessors = predecessors;
            this.operationsCount = operationsCount;
        }
    }

    public static Result run(int V, List<List<Edge>> adjList, int source) {
        double[] dist = new double[V];
        int[] parent = new int[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0.0;

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));
        pq.add(new NodeDistance(source, 0.0));

        int opsCount = 0;

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            int u = current.node;
            opsCount++;

            if (current.distance > dist[u]) continue;

            for (Edge edge : adjList.get(u)) {
                opsCount++;
                int v = edge.getDestination();
                double weight = edge.getWeight();

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new NodeDistance(v, dist[v]));
                }
            }
        }

        return new Result(dist, parent, opsCount);
    }

    private static class NodeDistance {
        int node;
        double distance;
        NodeDistance(int node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}