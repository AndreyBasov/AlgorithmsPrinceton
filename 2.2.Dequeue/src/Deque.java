import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int count;
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Incorrect input");
        }
        count++;
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (count == 1) {
            last = first;
            last.prev = null;
            last.next = null;
        }
        else {
            oldFirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Incorrect input");
        }
        count++;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (count == 1) {
            first = last;
            first.prev = null;
            first.next = null;
        }
        else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No elements");
        }
        count--;
        Item item = first.item;
        first = first.next;
        if (count != 0)
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No elements");
        }
        count--;
        Item item = last.item;
        if (count == 0) {
            first = null;
        }
        last = last.prev;
        if (last != null)
             last.next = null;
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new itemIterator();
    }

    private class itemIterator implements Iterator<Item> {
        Node node;
        public itemIterator() {
            node = new Node();
            node.next = first;
        }
        public boolean hasNext() {
            return node.next != null;
        }
        public Item next() {
            if (node == null) {
                throw new NoSuchElementException("No such element");
            }
            node = node.next;
            Item item = node.item;
            return item;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException ("Unsupported operation");
        }
    }


    // unit testing 
    public static void main(String[] args) {
	    Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeFirst();
        deque.removeFirst();
        System.out.println(deque.isEmpty());
        for (int i: deque)
            System.out.println(i);
    }
}
