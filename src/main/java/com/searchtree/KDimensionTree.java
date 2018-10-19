package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/15.
 */

import com.common.KdTreePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 2-Dimension tree.
 * suppose first and second variable are both int;
 */

public class KDimensionTree {
    private KdTreeNode root = null;
    enum Direction {
        Vertical,
        Horizonal;
    }

    private static int RANGE_NO_INTERSECTION = 0;
    private static int RANGE_SUBSET = 1;
    private static int RANGE_INTERSECTION = 2;

    public static class KdTreeNode {
        Range range;
        Direction direction;
        int split_line;
        KdTreeNode left = null, right=null;

        public KdTreeNode(Range range, Direction direction, int split_line) {
            this.range = range;
            this.direction = direction;
            this.split_line = split_line;
        }

        public String get_info() {
            if (left == null && right == null) {
                // the node is leaf
                return range.x1+","+range.y1;
            }
            // the node is not leaf
            return (direction == Direction.Horizonal ? "-" : "|") +","+split_line;
        }

        public KdTreeNode getLeft() {
            return left;
        }

        public KdTreeNode getRight() {
            return right;
        }
    }

    private static class Range {
        int x1, x2, y1, y2;//denotes the range

        public Range(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * cost time of this algorithm for finding median is O(nlogn).
     * the best algorithm for finding median cost O(n).
     * @param points
     * @param direction
     * @return
     */
    private static Point find_median(Point[] points, Direction direction) {
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (direction == Direction.Horizonal) {
                    return o1.x == o2.x ? 0 : (o1.x > o2.x ? 1 : -1);
                }
                // else if direction is vertical
                return o1.y == o2.y ? 0 : (o1.y > o2.y ? 1 : -1);
            }
        });

        return points[points.length/2];
    }

    private KdTreeNode create_leaf(Point point) {
        return new KdTreeNode(new Range(point.x, point.x, point.y, point.y), null, 0);
    }

    private boolean is_leaf(KdTreeNode node) {
        return node.range.x1 == node.range.x2 && node.range.y1 == node.range.y2;
    }

    private int get_range_relation(Range range1, Range range2) {
        // ranges have no intersection
        if (range1.x1 > range2.x2 || range2.x1 > range1.x2 || range1.y1 > range2.y2 || range2.y1>range2.y2)
            return RANGE_NO_INTERSECTION;
        // range1 belong to range2
        if (range1.x1>=range2.x1 && range1.x2<=range2.x2 && range1.y1 >= range2.y1 && range1.y2 <= range2.y2)
            return RANGE_SUBSET;

        return RANGE_INTERSECTION;
    }

    private static Point[] transfer_list_to_vector(ArrayList<Point> list) {
        Point[] points = new Point[list.size()];
        for (int i=0; i<list.size(); points[i] = list.get(i), i++);
        return points;
    }

    private Point[] report_nodes(KdTreeNode node) {
        ArrayList<Point> list = new ArrayList<>();
        Point point = new Point(node.range.x1, node.range.y1);
        if (is_leaf(node)) {
            list.add(point);
            return transfer_list_to_vector(list);
        }

        list.addAll(Arrays.asList(report_nodes(node.left)));
        list.add(point);
        list.addAll(Arrays.asList(report_nodes(node.right)));

        return transfer_list_to_vector(list);
    }


    public KdTreeNode build_kd(Point[] points, int depth, Range range) {
        if (points.length == 1)
            return create_leaf(points[0]);
        Direction direction = depth%2==0 ? Direction.Horizonal:Direction.Vertical;
        Point median = find_median(points, direction);
        Point[] left = Arrays.copyOfRange(points, 0, (points.length+1)/2);
        Point[] right = Arrays.copyOfRange(points, (points.length+1)/2, points.length);

        KdTreeNode node;
        if (direction == Direction.Horizonal) {
            node= new KdTreeNode(range, direction, median.x);
            node.left = build_kd(left, depth+1, new Range(range.x1, median.x, range.y1, range.y2));
            node.right = build_kd(right, depth+1, new Range(median.x, range.x2, range.y1, range.y2));
        } else {
            // direction is vertical
            node = new KdTreeNode(range, direction, median.y);
            node.left = build_kd(left, depth+1, new Range(range.x1, range.x2, range.y1, median.y));
            node.right = build_kd(right, depth+1, new Range(range.x1, range.x2, median.y, range.y2));
        }


        return node;
    }


    public KdTreeNode build_kd(Point[] points) {
        root = build_kd(points, 0, new Range(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
        return root;
    }


    public Point[] search(KdTreeNode node, Range range) {
        if (node == null) {
            return new Point[0];
        }

        ArrayList<Point> point_list = new ArrayList<>();

        if (get_range_relation(node.range, range) == RANGE_SUBSET) {
            point_list.addAll(Arrays.asList(report_nodes(node)));
            return transfer_list_to_vector(point_list);
        }

        if (get_range_relation(node.range, range) == RANGE_INTERSECTION) {
            Point[] left_points = search(node.left, range), right_points = search(node.right, range);

            if (left_points.length != 0)
                point_list.addAll(Arrays.asList(left_points));
            if (right_points.length != 0)
                point_list.addAll(Arrays.asList(right_points));
            return transfer_list_to_vector(point_list);
        }
        return new Point[0];
    }

    public Point[] search(Range range) {
        if (root == null)
            return null;
        return search(root, range);
    }



    public static void main(String[] args) {
        int [][] p = {{1, 2}, {3, 4}, {8,5}, {1, 7}, {3, 9}, {6, 2}, {7,3}, {9, 2},
                {19, 16}, {18, 20}, {32, 19}, {18, 28}, {23, 91}, {88, 33}, {100,21}
        };

        Point[] points = new Point[p.length];
        for (int i=0; i<p.length; i++) {
            points[i] = new Point(p[i][0], p[i][1]);
        }



        KDimensionTree tree = new KDimensionTree();
        tree.build_kd(points);
        Range range = new Range(5, 20, 5, 20);
        points = tree.search(range);
        for (Point point:points) {
            System.out.println(point.x+","+point.y);
        }

        KdTreePrinter printer = new KdTreePrinter(tree.root);

    }
}
