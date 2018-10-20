package com.common;

import com.graph.GraphList;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Wushudeng on 2018/10/19.
 */
public class GraphPrinter<V, E> extends JFrame{
    private GraphList<V, E> graph;
    HashMap<GraphList.Vertex<V, E>, Point> points = new HashMap<>();
    private int oval_radius = 10;

    Color TREE_COLOR = Color.GREEN, BACKWARD_COLOR = Color.BLUE, FORWARD_COLOR = Color.YELLOW,
            CROSS_COLOR = Color.RED, UNDETERMINED_COLOR = Color.BLACK;

    public GraphPrinter(GraphList<V, E> graph) {
        this.graph = graph;

        Random random = new Random();
        for (GraphList.Vertex<V, E> v : graph.vertices) {
            points.put(v, new Point(random.nextInt(1000), random.nextInt(700)));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (GraphList.Vertex<V, E> v : graph.vertices) {
            Point point = points.get(v);
            graphics2D.drawString(v.getData().toString(), point.x, point.y);
            graphics2D.drawOval(point.x, point.y, oval_radius, oval_radius);
            Point point1;
            for (GraphList.Edge<V, E> edge : v.getEdges()) {
                point1 = points.get(edge.getEnd_vertex());
                Color color = Color.BLACK;
                switch (edge.getType()){
                    case TREE:
                        color = TREE_COLOR;break;
                    case CROSS:
                        color = CROSS_COLOR;break;
                    case FORWARD:
                        color = FORWARD_COLOR;break;
                    case BACKWARD:
                        color = BACKWARD_COLOR;break;
                    case UNDETERMINED:
                        color = UNDETERMINED_COLOR;break;
                }
                graphics2D.setColor(color);
                graphics2D.drawLine(point.x, point.y, point1.x, point1.y);
            }
        }
    }

}
