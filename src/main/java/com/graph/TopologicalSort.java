package com.graph;

import java.util.Stack;

/**
 * Created by Wushudeng on 2018/10/20.
 */
public class TopologicalSort<V, E> extends GraphList<V, E> {
    public static class IntegerObject {
        public int i;

        public IntegerObject(int i) {
            this.i = i;
        }
    }

    public boolean dfs_topo_sort(Vertex<V, E> vertex, IntegerObject time, Stack<Vertex<V, E>> stack) {
        vertex.status = VStatus.DISCOVERED;
        vertex.dtime = time.i++;
        for (Edge<V, E> edge : vertex.edges) {
            if (edge.end_vertex.status == VStatus.UNDISCOVERED) {
                edge.end_vertex.parent = vertex;
                edge.type = EType.TREE;
                if (!dfs_topo_sort(edge.end_vertex, time, stack))
                    return false;
            } else if (edge.end_vertex.status == VStatus.DISCOVERED) {
                edge.type = EType.BACKWARD;
                return false;
            } else if (edge.end_vertex.status == VStatus.VISITED) {
                edge.type = vertex.dtime < edge.end_vertex.dtime ? EType.FORWARD : EType.CROSS;
            }
        }
        stack.push(vertex);
        vertex.status = VStatus.VISITED;
        vertex.ftime=time.i++;
        return true;
    }

    public static void main(String[] args) {
        IntegerObject time = new IntegerObject(0);
        Stack<Vertex<String, String>> stack = new Stack<>();
        TopologicalSort<String, String> topologicalSort = new TopologicalSort<>();

        String[] vertices = {"A", "B", "C", "D", "E", "F"};
        String[][] edges = {
                {"A", "C"},
                {"A", "D"},
                {"B", "C"},
                {"C", "D"},
                {"C", "F"},
                {"C", "E"},
                {"E", "F"}
        };

        for (String vertex : vertices) {
            topologicalSort.insert(vertex);
        }
        for (String[] edge : edges) {
            topologicalSort.insert(edge[0].charAt(0) - 'A', edge[1].charAt(0) - 'A', "-1", -1);
        }
        for (Vertex<String, String > vertex : topologicalSort.vertices) {
            if (vertex.status == VStatus.UNDISCOVERED && !topologicalSort.dfs_topo_sort(vertex, time, stack)) {
                System.out.println("It can not exist topological sorting");
                break;
            }
        }
        while (!stack.empty())
            System.out.print(stack.pop().data+"->");

    }


}
