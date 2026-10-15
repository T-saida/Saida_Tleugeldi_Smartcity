package mst;

import dsu.DisjointSetUnion;
import graph.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

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

    public static Result run(int V, List<Edge> edges) {
        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;


        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges);

        DisjointSetUnion dsu = new DisjointSetUnion(V);
        int comparisons = sortedEdges.size();
        for (Edge edge : sortedEdges) {
            int rootU = dsu.find(edge.getSource());
            int rootV = dsu.find(edge.getDestination());


            if (rootU != rootV) {
                dsu.union(rootU, rootV);
                mstEdges.add(edge);
                totalWeight += edge.getWeight();

                if (mstEdges.size() == V - 1) {
                    break;
                }
            }
        }


        boolean isConnected = (V == 0) || (mstEdges.size() == V - 1);
        if (!isConnected && V > 1) {

        }

        int totalOperations = dsu.getOperationsCount() + comparisons;
        return new Result(mstEdges, totalWeight, totalOperations);
    }
}