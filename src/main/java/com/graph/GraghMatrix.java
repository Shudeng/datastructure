package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by Wushudeng on 2018/10/19.
 */
public class GraghMatrix<V, E> extends AbstractGraph<V, E> {
    private ArrayList<Vertex<V>> vertices = new ArrayList<>();
    private ArrayList<ArrayList<Edge<E>>> edges = new ArrayList<>();
    private int v_num = 0;
    private int e_num = 0;

    public abstract class PriorityUpdater {
        abstract public void update(int i, GraghMatrix<V,E> graph, PriorityQueue<Integer> queue);
    }

    public static class Vertex<V> {
        V data;
        VStatus status;
        int in_degree, out_degree;
        int dtime, ftime;
        int parent;
        int priority;

        public Vertex(V data) {
            this.data = data;
            status = VStatus.UNDISCOVERED;
            in_degree = out_degree = 0;
            dtime = ftime = -1;
            parent = -1;
            priority = Integer.MAX_VALUE;

        }
    }

    private int next_neighbor(int i, int j) {
        while (j>-1 && !exist(i, --j));
        return j;
    }

    private int first_neighbor(int i) {
        return next_neighbor(i, v_num);
    }

    private void set_type(int i, int j, EType type) {
        edges.get(i).get(j).type = type;
    }

    public static class Edge<E> {
        E data;
        int weight;
        EType type;

        public Edge(E data) {
            this.data = data;
            weight = 0;
            type = EType.UNDETERMINED;
        }

        public Edge(E data, int weight) {
            this.data = data;
            this.weight = weight;
            type = EType.UNDETERMINED;
        }
    }

    //Operations of vertex

    public int insert(V data) {
        for (int i=0; i<v_num; edges.get(i).add(null),i++);
        v_num++;

        ArrayList<Edge<E>> edges = new ArrayList<>();
        for (int i=0; i<v_num; edges.add(null), i++);

        vertices.add(new Vertex<>(data));
        return vertices.size()-1;// the index of new created vertex which start from 0.
    }

    public V remove(int i) {
        for (int j=0; j<v_num; j++) {
            if (edges.get(i).get(j) != null) {
                vertices.get(j).in_degree--;
            }
            if (edges.get(j).get(i) != null) {
                vertices.get(j).out_degree--;
            }
            edges.get(j).remove(i);
        }
        edges.remove(i);
        v_num--;
        Vertex vertex = vertices.get(i);
        vertices.remove(i);
        return (V) vertex.data;
    }

    //Operation of edges

    /**
     * if the edge (i, j) exists.
     * @param i
     * @param j
     * @return
     */
    public boolean exist(int i, int j) {
        if (!(i>0 && i<v_num && j>0 && j<v_num))
            return false;
        return edges.get(i).get(j) != null;
    }

    public EType type(int i, int j) {
        return edges.get(i).get(j).type;
    }

    public void insert(int i, int j, E data, int weight) {
        if (!exist(i, j)) return;
        edges.get(i).set(j, new Edge<>(data, weight));
        e_num++;
        vertices.get(i).out_degree++;
        vertices.get(j).in_degree++;
    }

    public E remove(int i, int j) {
        E data = edges.get(i).get(j).data;
        edges.get(i).set(j, null);
        e_num--;
        vertices.get(i).out_degree--;
        vertices.get(j).in_degree--;
        return data;
    }

    @Override
    public void bfs() {

    }

    @Override
    public void dfs() {

    }


    // unchecked.
    public void pfs(int current, PriorityQueue<Integer> queue, PriorityUpdater updater) {
        Vertex<V> vertex = vertices.get(current);
        vertex.priority = 0;
        vertex.status = VStatus.VISITED;
        vertex.parent = -1;
        while (true) {
            for (int neigh = first_neighbor(current); neigh>-1; neigh = next_neighbor(current, neigh)) {
                updater.update(neigh, this, queue);
            }

            while (!queue.isEmpty()) {
                current = queue.poll();
                if (vertices.get(current).status == VStatus.UNDISCOVERED)
                    break;
            }

            if (vertices.get(current).status == VStatus.VISITED)
                break;

            vertices.get(current).status = VStatus.VISITED;
            set_type(vertices.get(current).parent, current, EType.TREE);
        }
    }


}
