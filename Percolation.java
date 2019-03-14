import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites = 0;
    private WeightedQuickUnionUF connections;
    private boolean[][] grid;
    private int topVirtualIndex;
    private int bottomVirtualIndex;
    private int n;

    private int toLinearIndex(int row, int col) {
        row--;
        col--;

        return (n * row) + col;
    }

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        connections = new WeightedQuickUnionUF(n + 2);
        grid = new boolean[n][n];
        this.n = n;

        // next to last site is connected to all top cells
        // last site is connected to all bottom cells
        // to check for percolation just check if last two sites are connected
        bottomVirtualIndex = n;
        topVirtualIndex = n + 1;

        int topIndex;
        int bottomIndex;

        for (int i = 1; i <= n; i++) {
            topIndex = toLinearIndex(1, i);
            bottomIndex = toLinearIndex(1, i);

            connections.union(topIndex, topVirtualIndex);
            connections.union(bottomIndex, bottomVirtualIndex);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row >= n || col < 1 || col >= n) {
            throw new java.lang.IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        openSites++;

        int index = toLinearIndex(row, col);
        grid[row - 1][col - 1] = true;

        // connect to left left neighbor
        if (col != 1 && isOpen(row, col - 1)) {
            connections.union(index, toLinearIndex(row, col - 1));
        }

        // connect to right neighbor
        if (col != n && isOpen(row, col + 1)) {
            connections.union(index, toLinearIndex(row, col + 1));
        }

        // connect to top neighbor
        if (row != 1 && isOpen(row - 1, col)) {
            connections.union(index, toLinearIndex(row - 1, col));
        }

        // connect to bottom neighbor
        if (row != n && isOpen(row + 1, col)) {
            connections.union(index, toLinearIndex(row + 1, col));
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row >= n || col < 1 || col >= n) {
            throw new java.lang.IllegalArgumentException();
        }


        return grid[row - 1][col - 1];
    }


    // is site (row, col) full?
    public boolean isFull(int row, int col) {

        System.out.println("row " + row + " col " + col);

        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }


        int index = toLinearIndex(row, col);

        return connections.connected(index, topVirtualIndex);
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
