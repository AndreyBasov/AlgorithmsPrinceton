/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] x;
    private int N;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        x = new double[trials];
        N = n;
        T = trials;
        int randRow, randCol, ans;
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                randRow = StdRandom.uniform(1, n + 1);
                randCol = StdRandom.uniform(1, n + 1);
                perc.open(randRow, randCol);
                perc.open(randRow, randCol);
            }
            x[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        double ans = 0;
        for (int i = 0; i < T; i++) {
            ans += x[i];
        }
        ans /= T;
        return ans;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double ans = 0;
        for (int i = 0; i < T; i++) {
            ans += (x[i] - mean()) * (x[i] - mean());
        }
        ans /= (T - 1);
        ans = Math.sqrt(ans);
        return ans;

    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    // test client 
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(n, T);
        System.out.println("mean = " + perc.mean());
        System.out.println("stddev = " + perc.stddev());
        System.out.println("95% confidence interval = [" +
                                   perc.confidenceLo() + " " + perc.confidenceHi() + "]");
    }
}
