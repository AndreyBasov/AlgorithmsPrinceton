import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item>  implements Iterable<Item>{
    private Item [] s;
    private int  head, count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        s[0] = null;
        head = 0;
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }
    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    private void resize(int capacity) {
        Item [] copy = (Item[]) new Object[capacity];
        int i = 0, j = head;
        while (j < count) {
            if (s[j] != null) {
                copy[i++] = s[j];
            }
            j++;
        }
        s = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Incorrect input");
        }
        int length = s.length;
        if (count == length) {
            resize(2 * length);
        }
        s[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("No elements");
        }
        Random rand = new Random();
        int random = rand.nextInt(count);
        count--;
        Item item = s[random];
        s[random] = s[count];
        if (count <= s.length / 4 && count != 0) {
            resize(s.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("No elements");
        }
        Random rand = new Random();
        int random = head + rand.nextInt(count - head + 1);
        return s[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();

    }

    private class RandomIterator implements Iterator <Item> {
        private RandomizedQueue <Item> sNew;
        private int newCount;
        public RandomIterator () {
            newCount = count;
            sNew = new RandomizedQueue <Item>();
            for (int i = 0; i < count; i++) {
                sNew.enqueue(s[i]);
            }
        }
        public boolean hasNext() {

            return newCount > 0;
        }
        public Item next() {
            if (sNew.isEmpty()) {
                throw new NoSuchElementException("No elements");
            }
            newCount--;
            return sNew.dequeue();
        }
        public void remove(Item item) {
            throw new UnsupportedOperationException ("Unsupported operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue <Integer> queue = new RandomizedQueue <>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Object[] obj = queue.s;
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.dequeue());
        }
    }

}
