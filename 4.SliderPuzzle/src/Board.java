import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Board {
    private int[][] Tiles;
    private int N;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        N = tiles[0].length;
        Tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Tiles[i][j] = tiles[i][j];
            }
        }
    }

    private int[][] getTiles() {
        return Tiles;
    }

    // string representation of this board
    public String toString() {
        String s = "";
        s += N;
        s += '\n';
        for (int i = 0; i < N; i++) {
            s += ' ';
            for (int j = 0; j < N; j++) {
                s += Tiles[i][j];
                s += ' ';
            }
            s += '\n';
        }
        return s;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Tiles[i][j] != 1 + j + i * N && Tiles[i][j] != 0) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int numInside, ans = 0, iRight, jRight;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Tiles[i][j] != 1 + j + i * N && Tiles[i][j] != 0) {
                    numInside = Tiles[i][j] - 1;
                    iRight = numInside / N;
                    jRight = numInside - N * iRight ;
                    ans += Math.abs(j - jRight) + Math.abs(i - iRight);
                }
            }
        }
        return ans;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean ans = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Tiles[i][j] != 1 + j + i * N && Tiles[i][j] != 0) {
                    ans = false;
                    break;
                }
            }
        }
        return ans;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board other = (Board) y;
        boolean ans = true;
        int[][] t = other.getTiles();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Tiles[i][j] != t[i][j]) {
                    ans = false;
                    break;
                }
            }
        }
        return ans;
    }

    //swap elements at (i1, j1) and (i2, j2)
    private void swap(int i1, int j1, int i2, int j2) {
        int tmp;
        tmp = Tiles[i1][j1];
        Tiles[i1][j1] = Tiles[i2][j2];
        Tiles[i2][j2] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList <Board> Neighbors = new ArrayList <Board>();
        Board newBoard;
        int iRight = 2, jRight = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Tiles[i][j] == 0) {
                    iRight = i;
                    jRight = j;
                    break;
                }
            }
        }
        if (iRight > 0) {
            swap(iRight - 1, jRight, iRight, jRight);
            Neighbors.add(new Board(Tiles));
            swap(iRight - 1, jRight, iRight, jRight);
        }
        if (iRight < N - 1) {
            swap(iRight + 1, jRight, iRight, jRight);
            Neighbors.add(new Board(Tiles));
            swap(iRight + 1, jRight, iRight, jRight);
        }
        if (jRight > 0) {
            swap(iRight, jRight - 1, iRight, jRight);
            Neighbors.add(new Board(Tiles));
            swap(iRight, jRight - 1, iRight, jRight);
        }
        if (jRight < N - 1) {
            swap(iRight, jRight + 1, iRight, jRight);
            Neighbors.add(new Board(Tiles));
            swap(iRight, jRight + 1, iRight, jRight);
        }
        return Neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newBoard;
        if (Tiles[0][0] != 0 && Tiles[1][0] != 0) {
            swap(0, 0, 1, 0);
            newBoard = new Board(Tiles);
            swap(0, 0, 1, 0);
        } else {
            swap(0, 1, 1, 1);
            newBoard = new Board(Tiles);
            swap(0, 1, 1, 1);
        }
        return newBoard;
    }

    // unit testing 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.twin());
       /* // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }*/
    }
}
