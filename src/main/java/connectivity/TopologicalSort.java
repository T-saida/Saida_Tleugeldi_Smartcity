package connectivity;

import graph.Edge;
import java.util.*;

public class TopologicalSort {

    public static class Result {
        public List<Integer> order;
        public boolean hasCycle;
        public int operationsCount;

        public Result(List<Integer> order, boolean hasCycle, int operationsCount) {
            this.order = order;
            this.hasCycle = hasCycle;
            this.operationsCount = operationsCount;
        }
    }

    public static Result run(int V, List<List<Edge>> adjList) {
        List<Integer> order = new ArrayList<>();
        int[] visited = new int[V];
        int opsCount = 0;
        boolean[] cycleDetected = {false};

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < V; i++) {
            if (visited[i] == 0) {
                if (dfs(i, adjList, visited, stack, cycleDetected)) {
                    return new Result(Collections.emptyList(), true, opsCount);
                }
            }
        }

        while (!stack.isEmpty()) {
            order.add(stack.pop());
            opsCount++;
        }

        return new Result(order, false, opsCount);
    }

    private static boolean dfs(int u, List<List<Edge>> adjList, int[] visited, Stack<Integer> stack, boolean[] cycleDetected) {
        visited[u] = 1;

        for (Edge edge : adjList.get(u)) {
            int v = edge.getDestination();
            if (visited[v] == 1) {
                cycleDetected[0] = true;
                return true;
            }
            if (visited[v] == 0) {
                if (dfs(v, adjList, visited, stack, cycleDetected)) {
                    return true;
                }
            }
        }

        visited[u] = 2;
        stack.push(u);
        return false;
    }
}