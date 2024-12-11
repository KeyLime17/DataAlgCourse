import java.util.ArrayList;

public class SortedArray<T extends Comparable> {

    private ArrayList<T> array;

    /**
     * Constructor.
     */
    public SortedArray() {
        array = new ArrayList<>();
    }

    /**
     * The isEmpty method checks to see if the list is empty.
     *
     * @return true if list is empty, false otherwise return true
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * The size method returns the length of the list.
     *
     * @return The number of elements in the list.
     */
    public int size() {
        return array.size();
    }

    /**
     * The add method adds an element at the sorted position in the list.
     *
     * @param element The element to add to the list in sorted order.
     */
    public void add(T element) {
        int Index = 0;
        while (Index < array.size() && array.get(Index).compareTo(element) < 0)
            Index++;
        array.add(Index, element);
    }

    /**
     * Return the item from the list at the given index.
     *
     * @param index the elements index
     * @return the item at the given index
     */
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException(index + " is out of range for list");
        return array.get(index);
    }

    /**
     * The toString method computes the string representation of the list.
     *
     * @return The string form of the list.
     */
    public String toString() {
        return array.toString();
    }

    /**
     * The remove method removes an element.
     *
     * @param element The element to remove.
     * @return true if the remove succeeded, false otherwise.
     */
    public boolean remove(T element) {
        return array.remove(element);
    }
}
