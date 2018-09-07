import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private final WeightedQuickUnionUF p;
    private final int size;
    private boolean[][] stateOfSites;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("index" + n + "is illegal.");
        }

        this.size = n;
        stateOfSites = new boolean[n][n];

        int totalSites = n * n + 2; // include the virsual top
        this.p = new WeightedQuickUnionUF(totalSites);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stateOfSites[i][j] = false; // true is open, false is closed
            }
        }

    }


    public void open(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException("row index " + row + "out of bounds.");
        }

        if (col <= 0 || col > size) {
            throw new IllegalArgumentException("col index " + col + "out of bounds.");
        }

        if (!isOpen(row, col)) {
            stateOfSites[row - 1][col - 1] = true;
            numberOfOpenSites += 1;
        }
        if (row == 1) {
            p.union(size * size, xyTo1D(row, col));
        }
        if (row == size) {
            p.union(size * size + 1, xyTo1D(row, col));
        }
        connectOpenNeighbor(row, col);


    }

    private void connectOpenNeighbor(int row, int col) {

        if ((row > 1) && isOpen(row - 1, col)) {
            p.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if ((row + 1) <= size && isOpen(row + 1, col)) {
            p.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            p.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
        if ((col + 1) <= size && isOpen(row, col + 1)) {
            p.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }


    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * size + col - 1;
    }


    /** is site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException("row index " + row + "out of bounds.");
        }

        if (col <= 0 || col > size) {
            throw new IllegalArgumentException("col index " + col + "out of bounds.");
        }
        return stateOfSites[row - 1][col - 1];

    }

    /** is site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException("row index " + row + "out of bounds.");
        }

        if (col <= 0 || col > size) {
            throw new IllegalArgumentException("col index " + col + "out of bounds.");
        }

        return p.connected((row - 1) * size + col - 1, size * size);

    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return p.connected(size * size, size * size + 1);
    }

    /** test client */
    public static void main(String[] args) {
        int n = 20;
        int times = 20;
        double fraction = 0.0;

        // randomly open sites
        for (int k = 0; k < times; k++) {
            Percolation PP = new Percolation(n);
            while (!PP.percolates()) {
                int i = StdRandom.uniform(n) + 1;
                int j = StdRandom.uniform(n) + 1;
                PP.open(i, j);
            }
            fraction += (double) PP.numberOfOpenSites / n / n;

        }
        fraction = fraction/times;
        System.out.println(fraction);




    }
}
