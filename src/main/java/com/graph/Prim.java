package com.graph;

import java.util.PriorityQueue;

/**
 * Created by Wushudeng on 2018/10/20.
 */
public class Prim<V, E> extends GraphList<V, E> {
    public void prim() {
        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>();
        Vertex<V, E> vertex = vertices.get(0);
        do {
            if (vertex.status != VStatus.VISITED) {
                vertex.status = VStatus.VISITED;
                for (Edge<V, E> edge : vertex.edges) {
                    if (edge.end_vertex.status == VStatus.UNDISCOVERED) {
                        queue.add(edge);
                    }
                }
            }

            Edge<V, E> edge = queue.poll();
            if (edge.end_vertex.status == VStatus.UNDISCOVERED) {
                edge.type = EType.TREE;
                vertex = edge.end_vertex;
            }
        } while (!queue.isEmpty());
    }

    public static void main(String[] args) {
        Prim<Integer, Integer> prim = new Prim<>();
        for (int i=0; i<8; i++) {
            prim.insert(i);
        }

        int[][] edges = {
                {0, 1, 5},
                {0, 2, 4},
                {1, 4, 2},

                {1, 3, 2},
                {2, 4, 3},
                {2, 3, 3},

                {4, 6, 4},
                {4, 7, 1},
                {4, 5, 7},

                {3, 6, 6},
                {5, 7, 3}
        };

        for (int[] edge : edges) {
            // undirected graph
            prim.insert(edge[0], edge[1], -1, edge[2]);
            prim.insert(edge[1], edge[0], -1, edge[2]);
        }

        prim.prim();
        prim.print();
    }
}
