package com.graph;

import java.util.ArrayList;

/**
 * Created by Wushudeng on 2018/10/19.
 */
abstract public class AbstractGraph<V, E> {
    public enum VStatus {
        UNDISCOVERED, DISCOVERED, VISITED
    }

    public enum EType {
        UNDETERMINED, TREE, CROSS, FORWARD, BACKWARD
    }

    // Operations of vertex.
    abstract public int insert(V data);
    abstract public V remove(int i);

    //Operations of edges
    abstract public boolean exist(int i, int j);
    abstract public EType type(int i, int j);
    abstract public void insert(int i, int j, E data, int weight);
    abstract public E remove(int i, int j);

    // graph traversal.
    abstract public void bfs();
    abstract public void dfs();




}
