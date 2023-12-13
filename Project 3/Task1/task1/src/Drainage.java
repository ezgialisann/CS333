import java.util.Scanner;

public class Drainage {
    public static int computeP(int i, int j, int[][] grid, int[][] memoization){
        int left= 0;
        int right = 0;
        int up = 0;
        int down = 0;
        int row= grid.length;
        int column = grid[0].length;

        if(memoization[i][j] <= 0){  // if it is not computed at the previous steps
            if(i > 0 && (grid[i][j] > grid[i-1][j])) { // is greater than the previous column
                left = 1 + computeP(i-1,j, grid,memoization);    // calculate the left side
                memoization[i][j] = left;
            }
            if(i < column-1 && (grid[i][j] > grid[i+1][j])){  // is greater than the next column
                right = 1 + computeP(i+1,j,grid,memoization);    // calculate the right side
                memoization[i][j] = right;
            }
            if(j > 0 && (grid[i][j] > grid[i][j-1])) { // is greater than the previous row
                up = 1 + computeP(i,j-1, grid,memoization);      // calculate the up
                memoization[i][j] = up;
            }
            if(j < row-1 && (grid[i][j] > grid[i][j+1])){ // is greater than the next row
                down = 1 + computeP(i,j+1, grid, memoization);   // calculate the down
                memoization[i][j] = down;
            }
            int max1 = Math.max(left,right);          // took the max of all the values
            int max2 = Math.max(up, down);
            int max = Math.max(max1,max2);
            memoization[i][j] = max;                  // update the memoization table
            return max;
        }else{  // if it is computed, return the same value
            return memoization[i][j];
        }
    }
    public static int[][] longestPath(int grid[][]){
        int r = grid.length;
        int c = grid[0].length;

        int[][] memoizationTable = new int[r][c];      // for top-down dynamic programming the table used for memoizing old values
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                memoizationTable[i][j] = Integer.MIN_VALUE;  // initially -infinity

        int[][] p = new int[r][c];
        for(int i = 0; i < r;i++){
            for(int j =0; j< c; j++){
                if(memoizationTable[i][j]<0){   // if it is not calculated in the previous steps
                    p[i][j] = computeP(i,j,grid,memoizationTable);
                }
            }
        }
      /*  for(int i = 0; i < l;i++){
            for(int j =0; j< l; j++){
                System.out.print(p[i][j] + "-");
            }
            System.out.println();
        }*/
        return p;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int r = s.nextInt();
        int c = s.nextInt();
        int[][] grid = new int[c][r];
        for(int i = 0; i < r ; i++) {
            for (int j = 0; j < c; j++) {
                grid[i][j] = s.nextInt();
             //   System.out.print("(" +grid[i][j] +") - ");
            }
          //  System.out.println();
        }
        int maxFlow = 0;
        int length = grid.length;
        int width = grid[0].length;
        int [][] newGrid = longestPath(grid);
        for(int i = 0; i < length; i++){    // finding the max value in the matrix
            for(int j = 0; j < width; j++){
                if(newGrid[i][j]>maxFlow){
                    maxFlow = newGrid[i][j];
                }
            }
        }
        maxFlow = maxFlow+1;          // including the cell itself
        System.out.println(maxFlow);
    }
}