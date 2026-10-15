package mst;

import graph.Edge;

import java.util.*;

public class PrimMST {

    public static class Result {
        public List<Edge> mstEdges;
        public double totalWeight;
        public int operationsCount;

        public Result(List<Edge> mstEdges, double totalWeight, int operationsCount) {
            this.mstEdges = mstEdges;
            this.totalWeight = totalWeight;
            this.operationsCount = operationsCount;
        }
    }

    public static Result run(int V, List<List<Edge>> adjList) {
        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;
        int operationsCount = 0;

        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        if (V == 0) return new Result(mstEdges, totalWeight, operationsCount);

        visited[0] = true;
        addEdges(0, adjList, visited, pq);

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            operationsCount++;
            Edge edge = pq.poll();
            int u = edge.getSource();
            int v = edge.getDestination();

            int nextNode = visited[u] ? v : u;

            if (visited[nextNode]) {
                continue;
            }

            visited[nextNode] = true;
            mstEdges.add(edge);
            totalWeight += edge.getWeight();

            addEdges(nextNode, adjList, visited, pq);
        }

        return new Result(mstEdges, totalWeight, operationsCount);
    }

    private static void addEdges(int u, List<List<Edge>> adjList, boolean[] visited, PriorityQueue<Edge> pq) {
        for (Edge edge : adjList.get(u)) {
            int v = edge.getDestination();
            if (!visited[v]) {
                pq.add(edge);
            }
        }
    }
}