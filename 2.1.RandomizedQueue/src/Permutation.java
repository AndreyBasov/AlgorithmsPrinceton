import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String s;
        RandomizedQueue <String> queue = new RandomizedQueue <>();
        for (int i = 0; i < k; i++) {
            s = StdIn.readString();
            queue.enqueue(s);
            System.out.println(s);
        }
        for (String a: queue) {
            System.out.println(a);
        }
    }
}
