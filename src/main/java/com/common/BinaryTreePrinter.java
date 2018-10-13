package com.common;

import com.priorityqueue.LeftistHeap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Created by Wushudeng on 2018/9/14.
 */
public abstract class BinaryTreePrinter extends JFrame {
    public BinaryTreePrintNode root;
    private static int width = 10, height = 10;
    private List<Point> print_points = new ArrayList<>();
    private List<Line> print_lines = new ArrayList<>();
    private int count;
    private static int oval_width = 10, oval_height = 10;
    private static int node_gap = 30;


    public static class BinaryTreePrintNode {
        String info;
        BinaryTreePrintNode left, right;
        int index;

        public BinaryTreePrintNode(String info) {
            this.info = info;
        }
    }

    private static class Point {
        int x;
        int y;
        String info;

        public Point(int x, int y, String info) {
            this.x = x;
            this.y = y;
            this.info = info;
        }
    }

    private static class Line {
        Point start, end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    }

    public BinaryTreePrinter() throws HeadlessException {
    }

//    public BinaryTreePrinter(LeftistHeap.LeftistNode<Integer> node) {
//        setSize(1000, 1000);
//        root = tranfer_node(node);
//        in_order_traversal(root, 0);
//        dfs(root);
//    }

    public void in_order_traversal(BinaryTreePrintNode node, int depth) {
        if (node == null)
            return;
        in_order_traversal(node.left, depth+1);
        node.index = count;
        Point p = new Point(getWidth()/2+count*node_gap, (int)(getHeight()*0.2)+depth*node_gap, node.info);
        count++;
        print_points.add(p);
        in_order_traversal(node.right, depth+1);
    }

    public void dfs(BinaryTreePrintNode node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            print_lines.add(new Line(print_points.get(node.index), print_points.get(node.left.index)));
            dfs(node.left);
        }
        if (node.right != null) {
            print_lines.add(new Line(print_points.get(node.index), print_points.get(node.right.index)));
            dfs(node.right);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Point point:print_points) {
            graphics2D.drawOval(point.x, point.y, oval_width, oval_height);
            graphics2D.drawString(point.info, point.x, point.y);
        }

        for (Line line:print_lines) {
            graphics2D.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        }
    }

    public void print() {
        in_order_traversal(root, 0);
        dfs(root);
        setVisible(true);
    }


    public abstract BinaryTreePrintNode tranfer_node(Object o);
}
