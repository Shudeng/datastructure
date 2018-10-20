package com.graph;

import com.common.GraphPrinter;

import javax.swing.border.EtchedBorder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Wushudeng on 2018/10/19.
 */
public class GraphList<V, E> extends AbstractGraph<V, E> {
    public static class Vertex<V, E> {
        V data;
        VStatus status;
        int in_degree, out_degree;
        int dtime, ftime;
        Vertex<V, E> parent;
        int priority;
        LinkedList<Edge<V, E>> edges = new LinkedList<>();

        public Vertex(V data) {
            this.data = data;
            status = VStatus.UNDISCOVERED;
            in_degree = out_degree = 0;
            dtime = ftime = -1;
            parent = null;
            priority = Integer.MAX_VALUE;

        }

        public LinkedList<Edge<V, E>> getEdges() {
            return edges;
        }

        public V getData() {
            return data;
        }
    }

    public static class Edge<V, E> {
        Vertex<V, E> end_vertex;
        E data;
        int weight;
        EType type = EType.UNDETERMINED;

        public Edge(E data) {
            this.data = data;
            weight = 0;
        }

        public Edge(E data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        public Edge(E data, int weight, Vertex<V, E> end_vertex) {
            this.data = data;
            this.weight = weight;
            this.end_vertex = end_vertex;
        }

        public Vertex<V, E> getEnd_vertex() {
            return end_vertex;
        }

        public EType getType() {
            return type;
        }
    }

    public ArrayList<Vertex<V, E>> vertices = new ArrayList<>();
    public int v_num = 0, e_num = 0;

    private int BFS(Vertex<V, E> vertex, int time) {
        ArrayDeque<Vertex<V, E>> queue = new ArrayDeque<>();
        vertex.status = VStatus.DISCOVERED;

        queue.push(vertex);
        while (!queue.isEmpty()) {
             Vertex<V, E> v= queue.pop();
             v.dtime = time++;

             for (Edge<V, E> edge : v.edges) {
                 if (edge.end_vertex.status == VStatus.UNDISCOVERED) {
                     edge.end_vertex.parent = v;
                     edge.end_vertex.status = VStatus.UNDISCOVERED.DISCOVERED;
                     queue.push(edge.end_vertex);
                     edge.type = EType.TREE;
                 } else {
                     edge.type = EType.CROSS;
                 }
             }
             v.status = VStatus.VISITED;
        }
        return time;
    }

    private int DFS(Vertex<V, E> v, int time) {
        v.status = VStatus.DISCOVERED;
        v.dtime = time++;
        for (Edge<V, E> edge : v.edges) {
            Vertex<V, E> end = edge.end_vertex;
            switch (end.status) {
                case UNDISCOVERED:
                    edge.type = EType.TREE;
                    time = DFS(edge.end_vertex, time);
                    break;
                case DISCOVERED:
                    edge.type = EType.BACKWARD;
                    break;
                case VISITED:
                    edge.type = v.dtime < end.dtime && v.ftime > end.ftime ? EType.FORWARD : EType.CROSS;
                    break;
            }
        }

        v.ftime = time++;
        v.status = VStatus.VISITED;
        return time;
    }

    /**
     * reset the status of vertices and edges.
     */
    private void reset() {
        for (Vertex<V,E> vertex : vertices) {
            vertex.status = VStatus.UNDISCOVERED;
            vertex.dtime = -1;
            vertex.ftime = -1;
            vertex.parent = null;
            LinkedList<Edge<V, E>> edges = vertex.edges;
            for (Edge<V, E> edge : edges) {
                edge.type = EType.UNDETERMINED;
            }
        }
    }

    // Operations of vertex.

    @Override
    public int insert(V data) {
        vertices.add(new Vertex<>(data));
        v_num++;
        return v_num-1;
    }

    @Override
    public V remove(int i) {
        LinkedList<Edge<V, E>> edges = vertices.get(i).edges;
        for (Edge<V, E> edge : edges) {
            edge.end_vertex.in_degree--;
        }

        for (Vertex<V, E> vertex : vertices) {
            edges = vertex.edges;
            for (Edge<V, E> edge : edges) {
                if (edge.end_vertex == vertices.get(i)) {
                    vertex.out_degree--;
                    edges.remove(edge);
                    break;
                }
            }
        }
        V data = vertices.get(i).data;
        vertices.remove(i);
        return data;
    }

    // Operations of edges.

    /**
     * cost time is the number of edges of vertices[i].
     * in the worst case, it is O(e_num).
     * @param i
     * @param j
     * @return
     */
    @Override
    public boolean exist(int i, int j) {
        if (!(i>0 && i<v_num && j>0 && j<v_num))
            return false;

        LinkedList<Edge<V, E>> edges = vertices.get(i).edges;
        for (Edge<V, E> edge : edges) {
            if (edge.end_vertex == vertices.get(j))
                return true;
        }
        return false;
    }

    @Override
    public EType type(int i, int j) {
        LinkedList<Edge<V, E>> edges = vertices.get(i).edges;
        for (Edge<V, E> edge : edges) {
            if (edge.end_vertex == vertices.get(j))
                return edge.type;
        }
        // if edge does not exist.
        return null;
    }

    @Override
    public void insert(int i, int j, E data, int weight) {
        Vertex<V, E> start_vertex = vertices.get(i);
        start_vertex.edges.add(new Edge<V, E>(data, weight, vertices.get(j)));
        start_vertex.out_degree++;
        vertices.get(j).in_degree++;
        e_num++;
    }

    @Override
    public E remove(int i, int j) {
        LinkedList<Edge<V, E>> edges = vertices.get(i).edges;
        E data = null;
        for (Edge<V, E> edge : edges) {
            if (edge.end_vertex == vertices.get(j)) {
                data = edge.data;
                edges.remove(edge);
            }
        }
        vertices.get(i).out_degree--;
        vertices.get(j).in_degree--;
        e_num--;
        return data;
    }

    @Override
    public void bfs() {
        reset();
        int time = 0;
        for (int i=0; i<vertices.size(); i++) {
            if (vertices.get(i).status == VStatus.UNDISCOVERED)
                time = BFS(vertices.get(i), time);
        }
    }

    @Override
    public void dfs() {
        int time = 0;
        for (Vertex<V, E> vertex : vertices) {
            if (vertex.status == VStatus.UNDISCOVERED)
                time = DFS(vertex, time);
        }
    }

    public static void main(String[] args) {
        GraphList<Integer, Integer> graph = new GraphList<>();
        for (int i=0; i<10; i++) {
            graph.insert(i);
        }

        int[][] edges = {
                {0, 9},
                {0, 5},
                {0, 8},
                {1, 3},
                {1, 4},
                {1, 5},
                {1, 8},
                {1, 9},
                {2, 3},
                {2, 5},
                {2, 6},
                {2, 8},
                {4, 7},
                {4, 9},
                {4, 3},
                {4, 1},
                {5, 8},
                {5, 6},
                {5, 2},
                {5, 1},
                {7, 8},
                {7, 9},
                {8, 3},
                {8, 5},
                {8, 1},
                {9, 1},
                {9, 5},
                {9, 7},
                {9, 2}

        };

        for (int[] edge : edges) {
            graph.insert(edge[0], edge[1], 0, 0);
        }

        graph.dfs();
        GraphPrinter<Integer, Integer> printer = new GraphPrinter<>(graph);
        printer.setSize(1000, 1000);
        printer.setVisible(true);
    }
}
