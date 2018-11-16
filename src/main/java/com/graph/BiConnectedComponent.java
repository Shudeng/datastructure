package com.graph;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Wushudeng on 2018/10/20.
 */
public class BiConnectedComponent<V, E> extends GraphList<V,E> {

    private void bcc_dfs(Vertex<V, E> vertex, TopologicalSort.IntegerObject time,
                         Stack<Vertex<V, E>> stack, ArrayList<ArrayList<Vertex<V, E>>> bcc_list) {
        vertex.status = VStatus.DISCOVERED;
        vertex.dtime = time.i;
        vertex.ftime = time.i;// use ftime as the discovered time of vertex's highest common ancestor of vertex and edge.end_vertex.
        time.i++;
        stack.push(vertex);
        for (Edge<V, E> edge : vertex.edges) {
            switch (edge.end_vertex.status) {
                case UNDISCOVERED:
                    edge.end_vertex.parent = vertex;
                    bcc_dfs(edge.end_vertex, time, stack, bcc_list);

                    // here is the core of the algorithm
                    // use ftime as the discovered time of vertex's highest common ancestor of vertex and edge.end_vertex.
                    if (edge.end_vertex.ftime < vertex.dtime) {
                        vertex.ftime = Math.min(vertex.ftime, edge.end_vertex.ftime);
                    } else {
                        ArrayList<Vertex<V, E>> bcc = new ArrayList<>();
                        while (vertex != stack.peek()) {
                            bcc.add(stack.pop());
                        };
                        bcc_list.add(bcc);
                    }


                    break;
                case DISCOVERED:
                    // use ftime as the discovered time of vertex's highest common ancestor of vertex and edge.end_vertex.
                    vertex.ftime = Math.min(edge.end_vertex.dtime, vertex.ftime);
                    break;
                case VISITED:
                    //do nothing.
                    break;
            }
        }
    }

    private ArrayList<ArrayList<Vertex<V, E>>> bbc_dfs() {
        Stack<Vertex<V, E>> stack = new Stack<>();
        TopologicalSort.IntegerObject time = new TopologicalSort.IntegerObject(0);
        ArrayList<ArrayList<Vertex<V, E>>> bcc_list = new ArrayList<>();
        for (Vertex<V, E> vertex : vertices) {
            if (vertex.status == VStatus.UNDISCOVERED)
                bcc_dfs(vertex, time, stack, bcc_list);
        }
        return bcc_list;
    }

    public static void main(String[] args) {
        BiConnectedComponent<Integer, Integer> biConnectedComponent = new BiConnectedComponent<>();
        for (int i=0; i<10; i++) {
            biConnectedComponent.insert(i);
        }
        Integer[][] edges = {
                {0, 9},
                {0, 8},
                {0, 1},
                {0, 7},
                {1, 2},
                {2, 3},
                {2, 7},
                {3, 4},
                {3, 6},
                {4, 6},
                {6, 5},
                {8, 9}
        };
        for (Integer[] edge : edges) {
            biConnectedComponent.insert(edge[0], edge[1], -1, -1);
            biConnectedComponent.insert(edge[1], edge[0], -1, -1);
        }
        ArrayList<ArrayList<Vertex<Integer, Integer>>> lists = biConnectedComponent.bbc_dfs();
        for (ArrayList<Vertex<Integer, Integer>> list : lists) {
            for (Vertex<Integer, Integer> v : list) {
                System.out.print(v.data+"\t");
            }
            System.out.println();
        }
    }
}
