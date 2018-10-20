package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Wushudeng on 2018/10/19.
 */
public class GraghMatrix<V, E> extends AbstractGraph<V, E> {
    private ArrayList<Vertex<V>> vertices = new ArrayList<>();
    private ArrayList<ArrayList<Edge<E>>> edges = new ArrayList<>();
    private int v_num = 0;
    private int e_num = 0;

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
}
