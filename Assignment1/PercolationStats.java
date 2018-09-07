
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    /** The private instance (or static) variable 'P' can be made 'final';
     * it is initialized only in the declaration or constructor. [ImmutableField]*/
    private final double[] fraction;
    private final int count;
    private double mean;
    private double stddev;
    /** perform trials independent experiments on an n-by-n grid */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        fraction = new double[trials];
        count = trials;
        int i, j;
        for (int k = 0; k < trials;  k++) {
            Percolation PP = new Percolation(n);
            while (!PP.percolates()) {
                i = StdRandom.uniform(n) + 1;
                j = StdRandom.uniform(n) + 1;
                PP.open(i, j);
            }
            fraction[k] = (double) PP.numberOfOpenSites()/(n*n);
        }

    }

    /** sample mean of percolation threshold */
    public double mean() {
        this.mean = StdStats.mean(fraction);
        return this.mean;
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        this.stddev = StdStats.stddev(fraction);
        return this.stddev;
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLo() {
        return this.mean-1.96 * this.stddev/Math.sqrt(count);
    }

    /** high endpoint of 95% confidentce interval */
    public double confidenceHi() {
        return this.mean+1.96 * this.stddev/Math.sqrt(count);
    }

    /** test client (described below) */
    public static void main(String[] args) {


        int n =  Integer.parseInt(args[0]);
        int trials =  Integer.parseInt(args[1]);

        Stopwatch timer = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, trials);
        double time = timer.elapsedTime();

        StdOut.printf(" (%.2f seconds)\n",  time);
        String confidence = "["+ps.confidenceLo() + ", " + ps.confidenceHi()+"]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
     }
}
