package com.common;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

import com.priorityqueue.BinomiaHeap;
import com.priorityqueue.BinomiaHeap.BinomialNode;
/**
 * Created by Wushudeng on 2018/9/8.
 */
public class BinomialHeapPrinter extends JFrame{
    int height = 50;
    int width = 50;
    int point_radius = 3;
    BinomiaHeap<Integer> heap;
    List<Shape> shapes = new ArrayList<>();
    private static class StringShapes {
        String s;
        int x;
        int y;

        public StringShapes(String s, int x, int y) {
            this.s = s;
            this.x = x;
            this.y = y;
        }
    }

    List<StringShapes> string_shapes = new ArrayList<>();


    public BinomialHeapPrinter(BinomiaHeap<Integer> heap) throws HeadlessException {
        this.heap = heap;
        setSize(600, 1000);
    }

    /**
     * The maximum width of tree whose degree is n is M_n = C(n/2, n).
     * For example, M_2=C(1, 2)=2, M_3=C(1, 3)=3,
     * @param degree
     * @return return the maximum width of one binomial tree. (Minus 1 from the maximum number of nodes in certain level of tree)
     */
    public static int getWidthBydegree(int degree) {
        if (degree == 0 || degree == 1) {
            return 0;
        }
        int[] dp = new int[degree+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        int i = 3;

        while (i <= degree) {
            dp[i] = 2*dp[i-1];
            i++;
        }
        return dp[degree]-1;
    }

//    public static int calculateCombinations(int n, int m) {
//        int dp_n, dp_m=1, dp_n_m=1;
//        int i = 1, product=1;
//        while (i<=n) {
//            product*=i;
//            if (i==m) {
//                dp_m = product;
//            }
//            if (i== n-m) {
//                dp_n_m = product;
//            }
//            i++;
//        }
//        dp_n = product;
//
//        return dp_n/(dp_m*dp_n_m);
//    }

    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D graphics2D = (Graphics2D) g;
        for (Shape shape:shapes) {
            graphics2D.draw(shape);
        }
        for (StringShapes string_shape:string_shapes) {
            graphics2D.drawString(string_shape.s, string_shape.x, string_shape.y);
        }
    }

    public void paintTree(int root_x, int root_y, BinomialNode<Integer> root) {
        if (root == null) {
            return;
        }
        Shape oval = new Ellipse2D.Float(root_x, root_y, 10, 10);
        shapes.add(oval);
        string_shapes.add(new StringShapes(root.value.toString(), root_x, root_y));
        if (root.child != null) {
            BinomialNode<Integer> child = root.child;
            int child_y = root_y + height;
            int child_x = root_x - ((root.degree == 1) ? 0 : (getWidthBydegree(root.degree-1)+1)*width);
            Shape line = new Line2D.Float(root_x+5, root_y+5, child_x, child_y);
            shapes.add(line);

            paintTree(child_x, child_y, child);
            BinomialNode<Integer> sibling_next, sibling_pre=child;
            int sibling_next_x, sibling_pre_x=child_x;

            while (sibling_pre.sibling != null) {
                sibling_next = sibling_pre.sibling;
                sibling_next_x = sibling_pre_x+(getWidthBydegree(sibling_next.degree)+1)*width;
                Shape l = new Line2D.Float(root_x+5, root_y+5, sibling_next_x+5, child_y+5);
                shapes.add(l);
                paintTree(sibling_next_x, child_y, sibling_next);
                sibling_pre = sibling_next;
                sibling_pre_x = sibling_next_x;
            }
        }

        BinomialNode<Integer> sibling = root.sibling;
        int sibling_x = root_x,
            sibling_y = root_y;

        while (sibling != null) {
            sibling_x = sibling_x + (getWidthBydegree(sibling.degree)+1)*width;
            paintTree(sibling_x, sibling_y, sibling);
            sibling = sibling.sibling;
        }

    }
    public static void main(String[] args) {
        BinomialHeapPrinter binomialHeapPrinter = new BinomialHeapPrinter(null);
        binomialHeapPrinter.setSize(1000, 1000);
        binomialHeapPrinter.setVisible(true);
//        getWidthBydegree(2);
    }


}
