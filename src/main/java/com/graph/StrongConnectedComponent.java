package com.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Wushudeng on 2018/10/20.
 */
public class StrongConnectedComponent<V, E> extends GraphList<V, E> {

    public void reverse_edges() {
        HashMap<Vertex<V, E>, LinkedList<Edge<V, E>>> hashMap = new HashMap<>();

        // time cost: O(v)
        // space cost: O(v)
        for (Vertex<V, E> vertex : vertices) {
            hashMap.put(vertex, new LinkedList<>());
        }

        // time cost: O(v+e)
        for (Vertex<V, E> vertex : vertices) {
            for (Edge<V, E> edge : vertex.getEdges()) {
                hashMap.get(edge.end_vertex).add(new Edge<V, E>(null, -1, vertex));
            }
        }

        //time cost: O(v)
        for (Vertex<V, E> vertex : vertices) {
            vertex.edges = hashMap.get(vertex);
        }
    }

    /**
     * dfs the origin graph
     */
    private void scc_dfs(Vertex<V, E> vertex, TopologicalSort.IntegerObject time, Stack<Vertex<V, E>> stack) {
        vertex.dtime = time.i++;
        vertex.status = VStatus.DISCOVERED;
        for (Edge<V, E> edge : vertex.getEdges()) {
            switch (edge.end_vertex.status) {
                case UNDISCOVERED:
                    edge.end_vertex.parent = vertex;
                    edge.type = EType.TREE;
                    scc_dfs(edge.end_vertex, time, stack);
                    break;
                case DISCOVERED:
                    edge.type = EType.BACKWARD;
                    break;
                case VISITED:
                    edge.type = vertex.dtime < edge.end_vertex.dtime ? EType.FORWARD : EType.CROSS;
                    break;
            }
        }
        stack.push(vertex);
        vertex.status = VStatus.VISITED;
        vertex.ftime = time.i++;
    }

    private void reverse_dfs(Vertex<V, E> vertex, TopologicalSort.IntegerObject time, ArrayList<Vertex<V, E>> scc) {
        vertex.dtime = time.i++;
        vertex.status = VStatus.DISCOVERED;
        for (Edge<V, E> edge : vertex.getEdges()) {
            switch (edge.end_vertex.status) {
                case UNDISCOVERED:
                    edge.end_vertex.parent = vertex;
                    edge.type = EType.TREE;
                    reverse_dfs(edge.end_vertex, time, scc);
                    break;
                case DISCOVERED:
                    edge.type = EType.BACKWARD;
                    break;
                case VISITED:
                    edge.type = vertex.dtime < edge.end_vertex.dtime ? EType.FORWARD : EType.CROSS;
                    break;
            }
        }
        scc.add(vertex);
        vertex.status = VStatus.VISITED;
        vertex.ftime = time.i++;
    }

    public void scc_dfs(Stack<Vertex<V, E>> stack) {
        reset();
        TopologicalSort.IntegerObject time = new TopologicalSort.IntegerObject(0);
        for (Vertex<V, E> vertex : vertices) {
            if (vertex.status == VStatus.UNDISCOVERED)
                scc_dfs(vertex, time, stack);
        }
    }


    /**
     * dfs the reversed graph
     * @return the strong connected components
     * space cost: O(v)
     * time cost: O(v+e)
     */
    public ArrayList<ArrayList<Vertex<V, E>>> reverse_dfs(Stack<Vertex<V, E>> stack) {
        reset();
        ArrayList<ArrayList<Vertex<V, E>>> sccs = new ArrayList<>();
        TopologicalSort.IntegerObject time = new TopologicalSort.IntegerObject(0);
        while (!stack.empty()) {
            Vertex<V, E> vertex = stack.pop();
            ArrayList<Vertex<V, E>> scc = new ArrayList<>();
            if (vertex.status == VStatus.UNDISCOVERED) {
                reverse_dfs(vertex, time, scc);
                sccs.add(scc);
            }
        }
        return sccs;
    }

    public static void main(String[] args) {
        StrongConnectedComponent<Integer, Integer> strongConnectedComponent = new StrongConnectedComponent<>();
        for (int i=0; i<12; i++) {
            strongConnectedComponent.insert(i);
        }

        Integer[][] edges = {
                {3, 0},
                {1, 2},
                {2, 1},

                {3, 2},
                {5, 2},
                {5, 3},

                {5, 4},
                {4, 6},
                {6, 5},

                {8, 6},
                {8, 7},
                {7, 9},

                {9, 8},
                {10, 7},
                {10, 9},

                {11, 10}
        };

        for (Integer[] edge : edges) {
            strongConnectedComponent.insert(edge[0], edge[1], -1, -1);
        }

        Stack<Vertex<Integer, Integer>> stack = new Stack<>();
        strongConnectedComponent.scc_dfs(stack);
        strongConnectedComponent.reverse_edges();
        ArrayList<ArrayList<Vertex<Integer, Integer>>> sccs = strongConnectedComponent.reverse_dfs(stack);
        for (ArrayList<Vertex<Integer, Integer>> scc : sccs) {
            for (Vertex<Integer, Integer> v : scc) {
                System.out.print(v.data+"\t");
            }
            System.out.println();
        }
    }



}
