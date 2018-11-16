package com.stack;

/**
 * Created by Wushudeng on 2018/11/15.
 */
public class EightQueen {
    private static void print(int[][] A) {
        for (int i=0; i<8; System.out.println(), i++)
            for (int j=0; j<8; System.out.print(A[i][j]+"\t"), j++);
    }

    private static int[][] copy_array(int[][] A) {
        int[][] B = new int[A.length][A[0].length];
        for (int i=0; i<A.length; i++)
            for (int j=0; j<A[0].length; B[i][j] = A[i][j], j++);

        return B;
    }


    public void solution(int[][] A, int row) {
        int[][] B;
        for (int i=0; i<8; i++) {
            if (safe(A, row, i)) {
                B = copy_array(A);
                B[row][i] = 1;
                // move to the last row
                if (row==7) {
                    print(B);
                    System.out.println("*************delimiter***********");
                } else {
                    solution(B, row+1);
                }
            }
        }
    }


    public void solution() {
        int[][] A = new int[8][8];
        for (int i=0; i<A.length; i++)
            for (int j=0; j<A[0].length; A[i][j]=0, j++);
        solution(A, 0);
    }

    public boolean safe(int[][] A, int row, int col) {
        // there is only one element in the same col
        for (int i=0; i<row; i++) {
            if (A[i][col] == 1)
                return false;
        }

        // there is only one element in the diagonal
        for (int j=1; row-j>=0 && col-j>=0; j++) {
            if (A[row-j][col-j] == 1)
                return false;

        }
        for (int j=1; row-j>=0 && col+j<8; j++) {
            if (A[row-j][col+j] == 1)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        EightQueen e = new EightQueen();
        e.solution();
    }
}
