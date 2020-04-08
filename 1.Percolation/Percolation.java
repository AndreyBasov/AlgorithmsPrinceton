public class Percolation {
    private int[] id;
    private int[] sz;
    private int num;
    private int N;
    private int openSites = 0;
    private int rootOfTop;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        N = n;
        num = n * n + 2;
        id = new int[num];
        sz = new int[num];
        id[0] = 0;
        id[num - 1] = num - 1;
        rootOfTop = 0;
        for (int i = 1; i < num - 1; i++) id[i] = -1;
    }

    //finding a root
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    //union
    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] <= sz[j]) {
            if (i == rootOfTop) {
                rootOfTop = j;
            }
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            if (j == rootOfTop) {
                rootOfTop = i;
            }
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    private boolean connected(int p, int q) {
        if (id[p] == -1 || id[q] == -1)
            return false;
        return root(p) == root(q);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > N || col > N) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        int index = N * (row - 1) + col;
        if (id[index] != -1) {
            return;
        }
        openSites++;
        id[index] = index;
        sz[index] = 1;
        if (row == 1) {
            union(index, 0);
        }
        if (row >= 2) {
            if (id[index - N] != -1)
                union(index - N, index);
        }
        if (row <= N - 1) {
            if (id[index + N] != -1)
                union(index + N, index);
        }
        if (col >= 2) {
            if (id[index - 1] != -1)
                union(index - 1, index);
        }
        if (col <= N - 1) {
            if (id[index + 1] != -1)
                union(index + 1, index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > N || col > N) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        int index = N * (row - 1) + col;
        if (id[index] == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > N || col > N) {
            throw new IllegalArgumentException("Incorrect argument");
        }
        int index = N * (row - 1) + col;
        if (id[index] == -1)
            return false;
        return root(index) == rootOfTop;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean ans;
        int index = N * (N - 1);
        for (int i = 1; i <= N; i++) {
            if (connected(index + i, 0))
                return true;
        }
        return false;
    }
}

