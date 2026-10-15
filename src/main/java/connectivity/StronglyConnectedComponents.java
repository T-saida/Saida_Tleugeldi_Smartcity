package connectivity;

import graph.Edge;
import java.util.*;

public class StronglyConnectedComponents {

    public static class Result {
        public List<List<Integer>> components;
        public int operationsCount;

        public Result(List<List<Integer>> components, int operationsCount) {
            this.components = components;
            this.operationsCount = operationsCount;
        }
    }

    public static Result run(int V, List<List<Edge>> adjList) {
        List<List<Integer>> components = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        int opsCount = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                fillOrder(i, adjList, visited, stack);
            }
        }


        List<List<Edge>> transposeAdj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            transposeAdj.add(new ArrayList<>());
        }

        for (int u = 0; u < V; u++) {
            for (Edge edge : adjList.get(u)) {
                opsCount++;
                transposeAdj.get(edge.getDestination()).add(new Edge(edge.getDestination(), u, edge.getWeight()));
            }
        }

        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                List<Integer> component = new ArrayList<>();
                dfsTranspose(u, transposeAdj, visited, component);
                components.add(component);
                opsCount++;
            }
        }

        return new Result(components, opsCount);
    }

    private static void fillOrder(int u, List<List<Edge>> adjList, boolean[] visited, Stack<Integer> stack) {
        visited[u] = true;
        for (Edge edge : adjList.get(u)) {
            if (!visited[edge.getDestination()]) {
                fillOrder(edge.getDestination(), adjList, visited, stack);
            }
        }
        stack.push(u);
    }

    private static void dfsTranspose(int u, List<List<Edge>> transposeAdj, boolean[] visited, List<Integer> component) {
        visited[u] = true;
        component.add(u);
        for (Edge edge : transposeAdj.get(u)) {
            if (!visited[edge.getDestination()]) {
                dfsTranspose(edge.getDestination(), transposeAdj, visited, component);
            }
        }
    }
}