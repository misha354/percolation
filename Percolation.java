import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites = 0;
    private WeightedQuickUnionUF connections;
    private boolean[][] grid;
    private int topVirtualIndex;
    private int bottomVirtualIndex;
    private int n;

    private int toLinearIndex(int row, int col) {

        return (n * row) + col;
    }

    private void checkIndexBounds(int row, int col){
     if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        connections = new WeightedQuickUnionUF(n*n + 2);
        grid = new boolean[n][n];
        this.n = n;

        // next to last site is connected to all top cells
        // last site is connected to all bottom cells
        // to check for percolation just check if last two sites are connected
        bottomVirtualIndex = n*n;
        topVirtualIndex = n*n + 1;

        int topIndex;
        int bottomIndex;

        for (int i = 0; i < n; i++) {
            topIndex = toLinearIndex(0, i);
            bottomIndex = toLinearIndex(n-1, i);


	    System.out.println("topVirtualIndex " + topVirtualIndex + " bottomVirtualIndex "+ bottomVirtualIndex);
	    System.out.println("topIndex " + topIndex + " bottomIndex "+ bottomIndex);
	    
            connections.union(topIndex, topVirtualIndex);
            connections.union(bottomIndex, bottomVirtualIndex);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
	checkIndexBounds(row, col);
	
        if (isOpen(row, col)) {
            return;
        }

        openSites++;

        int index = toLinearIndex(row, col);
        grid[row][col] = true;

        // connect to left left neighbor
        if (col != 0 && isOpen(row, col - 1)) {
            connections.union(index, toLinearIndex(row, col - 1));
        }

        // connect to right neighbor
        if (col != n-1 && isOpen(row, col + 1)) {
            connections.union(index, toLinearIndex(row, col + 1));
        }

        // connect to top neighbor
        if (row != 0 && isOpen(row - 1, col)) {
            connections.union(index, toLinearIndex(row - 1, col));
        }

        // connect to bottom neighbor
        if (row != n-1 && isOpen(row + 1, col)) {
            connections.union(index, toLinearIndex(row + 1, col));
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {

	checkIndexBounds(row,col);
	
        return grid[row][col];
    }


    // is site (row, col) full?
    public boolean isFull(int row, int col) {

	checkIndexBounds(row,col);
        System.out.println("row " + row + " col " + col);

        int index = toLinearIndex(row, col);

        return isOpen(row,col) && connections.connected(index, topVirtualIndex);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connections.connected(topVirtualIndex, bottomVirtualIndex);

    }

  /*  public static void main(String[] args) {
        int size = 10;
        Percolation pool = new Percolation(10);

    }*/


}
