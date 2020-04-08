import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;


public class Solver {
    private MinPQ<Node> Queue;  // main priority queue   
    private MinPQ<Node> Twin; // a twin priority queue in case the board is unsolvable
    private Node curNode, twinCurNode;  // current nodes

    // a node to put on PQ
    private class Node {
        public Board board;
        public int moves;
        public int movesBefore;
        public Node previous;
        private Node(Board newBoard, int MovesBefore, int Moves, Node Previous) {
            board = newBoard;
            movesBefore = MovesBefore;
            moves = Moves;
            previous = Previous;
        }
    }

    private class sorting implements Comparator<Node> {  //a comparator for nodes
        @Override
        public int compare(Node o1, Node o2) {
            return o1.moves - o2.moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null");
        }
        boolean allow = true;
        Node initialNode = new Node(initial, 0, 0, null);
        Node twinNode = new Node(initial.twin(), 0, 0, null);
        Queue = new MinPQ<Node>(new sorting());
        Twin = new MinPQ<Node>(new sorting());
        Queue.insert(initialNode);
        Twin.insert(twinNode);
        while (((curNode = Queue.delMin()).board.hamming()) != 0) {
            if ((twinCurNode = Twin.delMin()).board.hamming() == 0) {
                break;
            }
            for (Board s: curNode.board.neighbors()) {
                if (curNode.previous == null || !(curNode.previous.board.equals(s))) {
                    Queue.insert(new Node(s, curNode.movesBefore + 1, curNode.movesBefore + 1 + s.manhattan(), curNode));
                }
            }
            for (Board k : twinCurNode.board.neighbors()) {
                if (twinCurNode.previous == null || !(twinCurNode.previous.board.equals(k))) {
                    Twin.insert(new Node(k, twinCurNode.movesBefore + 1, twinCurNode.movesBefore + 1 + k.manhattan(), twinCurNode));
                }
            }
        }
    }

    // is the initial board solvable? 
    public boolean isSolvable() {
        if (twinCurNode.board.isGoal()) {
            return false;
        }
        return true;
    }

    // min number of moves to solve initial board
    public int moves() {
        return curNode.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Deque<Board> ans = new ArrayDeque<Board>();
        Node cur = curNode;
        while (cur != null) {
            ans.push(cur.board);
            cur = cur.previous;
        }
        return ans;
    }

    // test client 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
