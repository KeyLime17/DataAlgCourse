
/**
 * Generic Linked List class that always keeps the elements in order
 *
 * @author mark.yendt
 */
class SortedLinkedList<T extends Comparable> {
    /**
     * The Node class stores a list element and a reference to the next node.
     */
    private final class Node<T extends Comparable> {
        T value;
        Node next;

        /**
         * Constructor.
         *
         * @param val The element to store in the node.
         * @param n   The reference to the successor node.
         */
        Node(T val, Node n) {
            value = val;
            next = n;
        }

        /**
         * Constructor.
         *
         * @param val The element to store in the node.
         */
        Node(T val) {
            // Call the other (sister) constructor.
            this(val, null);
        }
    }

    private Node first;  // list head

    /**
     * Constructor.
     */
    public SortedLinkedList() {
        first = null;
    }

    /**
     * The isEmpty method checks to see if the list is empty.
     *
     * @return true if list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Return the item from the list at the given index.
	 *
     * @param index the elements index
     * @return the item at the given index
     */
    /*
    public T get(int index) {
        if (index < 0 || index >= count)
            throw new IndexOutOfBoundsException(index + " is out of range for list");
        Node<T> current = first;
        int i=0;
        while (i++ < index) {
            current = current.next;
        }
        return current.value;
    }
     */
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException(index + " is out of range for list");
        Node<T> current = first;
        int i=0;
        while (i++ < index) {
            current = current.next;
        }
        return current.value;
    }

    /**
     * The size method returns the length of the list.
     *
     * @return The number of elements in the list.
     */
    public int size() {
        int count = 0;
        Node p = first;
        while (p != null) {
            // There is an element at p
            count++;
            p = p.next;
        }
        return count;
    }

    /**
     * The add method adds an element at a position.
     *
     * @param element The element to add to the list in sorted order.
     */
    public void add(T element) {
        Node<T> temp = new Node<>(element);

        /*
        if (isEmpty()) {
            first = new Node(element, null);
            last = first;
        } else {
            last.next = new Node(element, null);
            last = last.next;
        }
        count++;
        */

        // If list empty or new element goes first
        if (isEmpty() || element.compareTo(first.value) < 0) {
            temp.next = first;
            first = temp;
        } else {
            // Insert in correct position
            Node<T> current = first;
            while (current.next != null && current.next.value.compareTo(element) < 0)
                current = current.next;

            // Insert temp after current into the list
            temp.next = current.next;
            current.next = temp;
        }
    }

    /**
     * The toString method computes the string representation of the list.
     *
     * @return The string form of the list.
     */
    public String toString()
    {
        String str = "[";

        // Use p to walk down the linked list
        SortedLinkedList.Node p = first;
        while (p != null) {
            str += p.value + ",";
            p = p.next;
        }

        return str.substring(0,str.length()-1) + "]";
    }

    /**
     * The remove method removes an element.
     *
     * @param element The element to remove.
     * @return true if the remove succeeded, false otherwise.
     */
    public boolean remove(T element) {
        // If empty
        if (isEmpty())
            return false;
        // Remove first node by reassigning first
        if (first.value.equals(element)) {
            first = first.next;
            return true;
        }
        Node<T> current = first.next;
        Node<T> previous = first;
        // Go through list to find node with element
        while (current != null) {
            if (current.value.equals(element)) {
                previous.next = current.next;
                return true;
            }
            previous = current;
            current = current.next;
        }

        return false;
    }
}