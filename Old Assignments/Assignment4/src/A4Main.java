import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * I Makar Nestsiarenka 000839688 certify that this is my original work. I have not
 * used any uncited exernal resources and I have not shared my work with anyone else.
 * I did not use ChatGPT or any other AI based tool.
 */

/**
 * Write answers to assignment questions here:
 * 1) Summarize your timing results for the 4 different methods in a simple and concise table
 * at the top of the assignment.
 *
 *  | Method              | Items  | Checksum | Time ms |
 *  | Arraylist, built in | 18,756 | 933,405  | 3,177   |
 *  | iSort, primitive    | 18,756 | 933,405  | 4,184   |
 *  | SortedArray         | 18,756 | 933,405  | 311     |
 *  | SortedLinkedList    | 18,756 | 933,405  | 691    |
 *
 * 2) Do you notice any significant performance difference between the
 * SortedLinkedList<T> and the SortedArray<T> classes when adding items?
 *
 * SortedArray is much faster compared to SortedLinkedList, pretty much double when adding
 *
 * 3) When would you choose to use a SortedLinkedList over an SortedArray
 * based on the results of this assignment? Is speed the only factor?
 *
 * SortedArray should be used when random access to elements is needed, or when frequent searches
 * are needed, because its timecomplexity to access is O(1). SortedLinkedList is a better
 * choice when memory efficiency is important or when frequent insertions and
 * deletions at arbitrary positions are needed.
 *
 * 4) ArrayList uses the built-in sorting method. Look up the documentation for it. This
 * method has a very good time complexity. You should notice that sorting after adding
 * each item is slower than using the SortedLinkedList. The SortedLinkedList
 * class is essentially the same as insertion sort when a collection of items are added
 * (inserted) into the correct spot. Why is it that SortedLinkedList is able to do
 * better than the built-in sort which uses a much faster method?
 *
 * The ArrayList with built-in sort repeatedly calls the sorting algorithm after every insertion,
 * resulting in a time complexity of O(n log n) for each sort operation. This adds up to
 * O(n^2 log n) over all insertions.
 * SortedLinkedList inserts each item into its correct position directly, making it similar to
 * insertion sort with an overall time complexity of O(n^2).
 *
 * 5) Our method iSort() should be orders of magnitude slower than the built-in sorting
 * method. Is it? If not why not? SortedLinkedList uses insertion sort. Is it faster?
 * Why?
 *
 * This is because iSort sorts incrementally, similar to SortedLinkedList, while the built-in sort in ArrayList sorts the
 * entire collection multiple times.
 * Both iSort and SortedLinkedList use insertion sort internally, so their time complexities are
 * comparable (O(n^2)). SortedLinkedList is slightly faster than iSort due to fewer array-copying operations; iSort
 * requires shifting elements within an array, which is more costly than pointer manipulation in a linked list.
 */

public class A4Main {
    /**
     *
     * @param args (unused)
     */
    public static void main(String[] args) {
        runPartA();
        runPartB();
    }

    /**
     * Validate that an array of Strings is sorted
     *
     * @param array array that might or might not be sorted
     * @return a 6 digit checksum if sorted, -1 if not sorted.
     */
    public static int ckSumSorted(String[] array) {
        if (array.length == 0)
            return 1;
        int sum = 0;
        int i =0;
        String prev = array[0];
        for(String str: array) {
            if (str.compareTo(prev) < 0)
                return -1;
            i++;
            sum += str.hashCode() * i;
            prev = str;
        }
        return Math.abs(sum % 1000_000);
    }

    /**
     * Simple validation of data structures
     */
    public static void runPartA() {
        System.out.println("\n\nPart A --- \n");
        SortedLinkedList<String> linkedList = new SortedLinkedList<>();
        SortedArray<String> sortedArray = new SortedArray<>();

        String[] names = {"Bob", "Carol", "Aaron", "Alex", "Zaphod"};

        System.out.println("Initial Array = " + Arrays.toString(names));
        System.out.printf("Initial Array sorted = %,d\n", ckSumSorted(names));

        for (String name : names) {
            linkedList.add(name);
            sortedArray.add(name);
        }

        System.out.println("LinkedList: " + linkedList);
        String[] sl = {linkedList.get(0), linkedList.get(1), linkedList.get(2), linkedList.get(3), linkedList.get(4)};
        System.out.println(Arrays.toString(sl));
        System.out.printf("LinkedList ckSumSorted = %,d\n\n", ckSumSorted(sl));
        System.out.println("SortedArray: " + sortedArray);

        String[] sa = {sortedArray.get(0), sortedArray.get(1), sortedArray.get(2), sortedArray.get(3), sortedArray.get(4)};
        System.out.println(Arrays.toString(sa));
        System.out.printf("SortedArray ckSumSorted = %,d\n", ckSumSorted(sa));

        // Remove all names from linked list
        for (String name : names) {
            linkedList.remove(name);
        }
        linkedList.remove("Karen");
        System.out.println("LinkedList after removals: " + linkedList);

        // Additional data type test <Integer> for example
        SortedLinkedList<Integer> integerSortedLinkedList = new SortedLinkedList<>();
        integerSortedLinkedList.add(1);
        integerSortedLinkedList.add(3);
        integerSortedLinkedList.add(5);
        integerSortedLinkedList.add(7);
        integerSortedLinkedList.add(100001);

        System.out.println("Integer LinkedList: " + integerSortedLinkedList);
        integerSortedLinkedList.remove(7);
        System.out.println("Integer LinkedList w/out 7: " + integerSortedLinkedList);

    }

    /**
     * Insertion sort
     * @param array - an array of strings.
     * @param number - added 'number' to number of elements to sort, so it wont go through the entire array each time
     */
    public static void iSort(String[] array, int number) {
        String unsortedValue;
        int scan;
        for (int index = 1; index < number; index++) {
            unsortedValue = array[index];
            scan = index;
            while (scan > 0 && array[scan - 1].compareTo(unsortedValue) > 0) {
                array[scan] = array[scan - 1];
                scan--;
            }
            array[scan] = unsortedValue;
        }
    }

    /**
     * Part B
     * Performance testing for ArrayList, LinkedList and SortedArray
     */
    public static void runPartB() {
        ArrayList<String> bnames = new ArrayList<>();
        long startTime;

        System.out.println("\n\nPart B --- \n");
        // read from file - do not change
        startTime = System.currentTimeMillis();
        try (Scanner scanner = new Scanner(new File("src/bnames-2.txt"))) {
            while (scanner.hasNextLine()) {
                bnames.add(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return;
        }
        long endTime = System.currentTimeMillis();
        String[] sa = new String[bnames.size()];
        for (int i = 0; i < bnames.size(); i++)
            sa[i] = bnames.get(i);
        System.out.printf("bnames.txt ckSumSorted = %,d\n", ckSumSorted(sa));
        System.out.printf("Read file bnames.txt: %,d items read in %,d ms\n\n",
                bnames.size(), (endTime - startTime));

        // ArrayList Add - 
        ArrayList<String> sortedAL = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for (String name : bnames) {
            sortedAL.add(name);
            // for this test we mimic the sorted Linked List insert
            // by sorting the array after each add operation.
            // Probably this isn't the best approach.
            sortedAL.sort((a, b) -> (a.compareTo(b)));
        }
        endTime = System.currentTimeMillis();
        System.out.printf("ArrayList added %,d items\n", sortedAL.size());
        for (int i = 0; i < sortedAL.size(); i++)
            sa[i] = sortedAL.get(i);
        System.out.printf("ArrayList ckSumSorted = %,d\n", ckSumSorted(sa));
        System.out.printf("Time to build sorted ArrayList, quicksort after each .add() %,d ms\n\n", endTime - startTime);

        /** Additional Tests go here **/
        // iSort
        String[] primitiveArray = new String[bnames.size()];
        startTime = System.currentTimeMillis();
        int count = 0;
        for (String name : bnames) {
            primitiveArray[count] = name;
            count++;
            iSort(primitiveArray, count);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("Primitive array sorted with iSort\n");
        System.out.printf("Primitive Array ckSumSorted = %,d\n", ckSumSorted(primitiveArray));
        System.out.printf("Time to sort primitive array: %,d ms\n\n", endTime - startTime);

        // SortedArray
        SortedArray<String> sortedArray = new SortedArray<>();
        startTime = System.currentTimeMillis();
        for (String name : bnames) {
            sortedArray.add(name);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("SortedArray added %,d items\n", sortedArray.size());
        // convert to checksum
        String[] checksum = new String[sortedArray.size()];
        for (int i = 0; i < sortedArray.size(); i++) {
            checksum[i] = sortedArray.get(i);
        }
        System.out.printf("SortedLinkedList ckSumSorted = %,d\n", ckSumSorted(checksum));
        System.out.printf("Time to sort SortedLinkedList: %,d ms\n\n", endTime - startTime);

        // SortedLinkedList
        SortedLinkedList<String> sortedLinkedList = new SortedLinkedList<>();
        startTime = System.currentTimeMillis();
        for (String name : bnames) {
            sortedLinkedList.add(name);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("SortedLinkedList added %,d items\n", sortedLinkedList.size());
        // convert to checksum
        String[] checksum1 = new String[sortedLinkedList.size()];
        for (int i = 0; i < sortedLinkedList.size(); i++) {
            checksum1[i] = sortedLinkedList.get(i);
        }
        System.out.printf("SortedLinkedList ckSumSorted = %,d\n", ckSumSorted(checksum1));
        System.out.printf("Time to sort SortedLinkedList: %,d ms\n\n", endTime - startTime);





    }
}