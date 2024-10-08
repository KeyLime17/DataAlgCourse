/**
 *  COMP-10205 - Starting code for Assignment # 2
 *
 * // statement of authorship goes here ... | Cant actually remember if we were given a specific format for authorship
 * Makar Nestsiarenka, 000839688. This is my work alone, No other people assisted in direct creation of it, unless we include lecture examples and recordings
 *
 *
 *
 *
 * // Answers for Part 3 goes here...
 * a: Fastest -> slowest @20 elements
 * bsort -> dsort -> asort -> esort -> fsort -> csort
 * b: Fastest -> slowest @8000 elements
 * bsort -> esort -> csort -> dsort -> asort -> fsort
 * c:
 * The slowest would be fsort, at ~65.35... ms, it was second last at 20 elements and last at 8000 elements, yes it impacts my selection, so far Counting Sorting Algorithm seems to be
 * best case scenario for this.
 * d:
 * ASort | Selection sort
 * O(n^2) | O(1)
 *
 * BSort | Counting Sort
 * O(n+k) | O(k)
 *
 * CSort | Merge Sort
 * O(n log n) | O(n)
 *
 * DSort | Insertion Sort
 * O(n^2) | O(1)
 *
 * ESort | Quick Sort
 * O(n log n) | O(log n)
 *
 * FSort | Bubble Sort
 * O(n^2) | O(1)
 */

import java.util.Random;



/**
 * All methods are static to the class - functional style
 *
 * @author mark.yendt
 */
public class Assignment2_Start_F24 {

    /**
     * The swap method swaps the contents of two elements in an int array.
     *
     * @param array where elements are to be swapped.
     * @param a     The subscript of the first element.
     * @param b     The subscript of the second element.
     */
    private static void swap(int[] array, int a, int b) {
        int temp;
        temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * Validate that an array is sorted,
     *
     * @param array array that might or might not be sorted
     * @return a 6 digit checksum if sorted, -1 if not sorted.
     */
    public static int ckSumSorted(int[] array) {
        int sum = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return -1;
            }
            sum += array[i] * i;
        }
        return Math.abs(sum % 1000_000);
    }

    /**
     * Randomly re-arrange any array, if sorted will unsort the array, or shuffle it
     *
     * @param array array that might or might not be sorted
     */
    public static void shuffle(int[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int j = rand.nextInt(array.length);
            swap(array, i, j);
        }
    }

    /** Selection
     * ---------------------------- a Sort ---------------------------------------
     */


    public static long aSort(int[] array) {
        int startScan;   // Starting position of the scan
        int index;       // To hold a subscript value
        int minIndex;    // Element with smallest value in the scan
        int minValue;    // The smallest value found in the scan

        long comparisons = 0;

        // The outer loop iterates once for each element in the
        // array. The startScan variable marks the position where
        // the scan should begin.
        for (startScan = 0; startScan < (array.length - 1); startScan++) {
            // Assume the first element in the scannable area
            // is the smallest value.
            minIndex = startScan;
            minValue = array[startScan];

            // Scan the array, starting at the 2nd element in
            // the scannable area. We are looking for the smallest
            // value in the scannable area.
            for (index = startScan + 1; index < array.length; index++) {
                comparisons++;
                if (array[index] < minValue) {
                    minValue = array[index];
                    minIndex = index;
                }
            }

            // Swap the element with the smallest value
            // with the first element in the scannable area.
            array[minIndex] = array[startScan];
            array[startScan] = minValue;
        }
        return comparisons;
    }

    /** Counting
     * ---------------------------- b Sort ---------------------------------------
     */

    public static long bSort(int array[]) {
        int count = 0;

        int min = array[0];
        int max = array[0];
        long comparisons = 0;

        for (int i = 1; i < array.length; i++) {
            comparisons++;
            if (array[i] < min)
                min = array[i];
            else if (array[i] > max)
                max = array[i];
        }
        int b[] = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            comparisons++;
            b[array[i] - min]++;
        }

        for (int i = 0; i < b.length; i++)
            for (int j = 0; j < b[i]; j++) {
                comparisons++;
                array[count++] = i + min;
            }

        return comparisons;
    }

    /** Merge sort
     * ---------------------------- c Sort ---------------------------------------
     */

    public static long cSort(int inputArray[]) {
        int length = inputArray.length;
        // Create array only once for merging
        int[] workingArray = new int[inputArray.length];
        long count = 0;
        count = doCSort(inputArray, workingArray, 0, length - 1, count);
        return count;
    }

    private static long doCSort(int[] inputArray, int[] workingArray, int lowerIndex, int higherIndex, long count) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doCSort(inputArray, workingArray, lowerIndex, middle, count);
            // Below step sorts the right side of the array
            doCSort(inputArray, workingArray, middle + 1, higherIndex, count);
            // Now merge both sides
            part2(inputArray, workingArray, lowerIndex, middle, higherIndex);
        }
        return count;
    }

    private static long part2(int[] inputArray, int[] workingArray, int lowerIndex, int middle, int higherIndex) {

        long count = 0;
        for (int i = lowerIndex; i <= higherIndex; i++) {
            workingArray[i] = inputArray[i];
        }
        int i1 = lowerIndex;
        int i2 = middle + 1;
        int newIndex = lowerIndex;
        while (i1 <= middle && i2 <= higherIndex) {
            count++;
            if (workingArray[i1] <= workingArray[i2]) {
                inputArray[newIndex] = workingArray[i1];
                i1++;
            } else {
                inputArray[newIndex] = workingArray[i2];
                i2++;
            }
            newIndex++;
        }
        while (i1 <= middle) {
            inputArray[newIndex] = workingArray[i1];
            newIndex++;
            i1++;
        }
        return count; //Why does it not return amount of comparisons? idk
    }

    /** Insertion
     * ---------------------------- d Sort ---------------------------------------
     */

    public static long dSort(int[] array) {
        int unsortedValue;  // The first unsorted value
        int scan;           // Used to scan the array
        long comparisons = 0;

        // The outer loop steps the index variable through
        // each subscript in the array, starting at 1. The portion of
        // the array containing element 0  by itself is already sorted.
        for (int index = 1; index < array.length; index++) {
            // The first element outside the sorted portion is
            // array[index]. Store the value of this element
            // in unsortedValue.
            unsortedValue = array[index];

            // Start scan at the subscript of the first element
            // outside the sorted part.
            scan = index;

            // Move the first element in the still unsorted part
            // into its proper position within the sorted part.
            while (scan > 0 && array[scan - 1] > unsortedValue) {
                comparisons++;
                array[scan] = array[scan - 1];
                scan--;
            }
            comparisons++;

            // Insert the unsorted value in its proper position
            // within the sorted subset.
            array[scan] = unsortedValue;
        }
        return comparisons;
    }

    /** Quick Sort
     * ---------------------------- e Sort ---------------------------------------
     */

    public static long eSort(int array[]) {

        return doESort(array, 0, array.length - 1, 0);
    }

    /**
     * The doESort method uses the Quick Sort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @param start The starting subscript of the list to sort
     * @param end   The ending subscript of the list to sort
     */
    private static long doESort(int array[], int start, int end, long numberOfCompares) {
        int pivotPoint;

        if (start < end) {
            // Get the pivot point.
            pivotPoint = part1(array, start, end);
            // Note - only one +/=
            numberOfCompares += (end - start);
            // Sort the first sub list.
            numberOfCompares = doESort(array, start, pivotPoint - 1, numberOfCompares);

            // Sort the second sub list.
            numberOfCompares = doESort(array, pivotPoint + 1, end, numberOfCompares);
        }
        return numberOfCompares;
    }

    /**
     * The partition method selects a pivot value in an array and arranges the
     * array into two sub lists. All the values less than the pivot will be
     * stored in the left sub list and all the values greater than or equal to
     * the pivot will be stored in the right sub list.
     *
     * @param array The array to partition.
     * @param start The starting subscript of the area to partition.
     * @param end   The ending subscript of the area to partition.
     * @return The subscript of the pivot value.
     */
    private static int part1(int array[], int start, int end) {
        int pivotValue;    // To hold the pivot value
        int endOfLeftList; // Last element in the left sub list.
        int mid;           // To hold the mid-point subscript

        // Find the subscript of the middle element.
        // This will be our pivot value.
        mid = (start + end) / 2;

        // Swap the middle element with the first element.
        // This moves the pivot value to the start of
        // the list.
        swap(array, start, mid);

        // Save the pivot value for comparisons.
        pivotValue = array[start];

        // For now, the end of the left sub list is
        // the first element.
        endOfLeftList = start;

        // Scan the entire list and move any values that
        // are less than the pivot value to the left
        // sub list.
        for (int scan = start + 1; scan <= end; scan++) {
            if (array[scan] < pivotValue) {
                endOfLeftList++;
                swap(array, endOfLeftList, scan);
            }
        }

        // Move the pivot value to end of the
        // left sub list.
        swap(array, start, endOfLeftList);

        // Return the subscript of the pivot value.
        return endOfLeftList;
    }

    /** Bubble Sort
     * ---------------------------- f Sort ---------------------------------------
     */

    public static long fSort(int[] array) {
        int lastPos;     // Position of last element to compare
        int index;       // Index of an element to compare
        long comparisons = 0;
        // The outer loop positions lastPos at the last element
        // to compare during each pass through the array. Initially
        // lastPos is the index of the last element in the array.
        // During each iteration, it is decreased by one.
        for (lastPos = array.length - 1; lastPos >= 0; lastPos--) {
            // The inner loop steps through the array, comparing
            // each element with its neighbor. All of the elements
            // from index 0 thrugh lastPos are involved in the
            // comparison. If two elements are out of order, they
            // are swapped.
            for (index = 0; index <= lastPos - 1; index++) {
                comparisons++;
                // Compare an element with its neighbor.
                if (array[index] > array[index + 1]) {
                    // Swap the two elements.

                    swap(array, index, index + 1);
                }
            }
        }
        return comparisons;
    }

    /**
     * A demonstration of recursive counting in a Binary Search
     *
     * @param array - array to search
     * @param low   - the low index - set to 0 to search whiole array
     * @param high  - set to length of array to search whole array
     * @param value - item to search for
     * @param count - Used in recursion to accumulate the number of comparisons made (set to 0 on initial call)
     * @return
     */
    private static int[] binarySearchR(int[] array, int low, int high, int value, int count) {
        int middle;     // Mid point of search

        // Test for the base case where the value is not found.
        if (low > high)
            return new int[]{-1, count};

        // Calculate the middle position.
        middle = (low + high) / 2;

        // Search for the value.
        if (array[middle] == value)
            // Found match return the index
            return new int[]{middle, count};

        else if (value > array[middle])
            // Recursive method call here (Upper half of remaining data)
            return binarySearchR(array, middle + 1, high, value, count + 1);
        else
            // Recursive method call here (Lower half of remaining data)
            return binarySearchR(array, low, middle - 1, value, count + 1);
    }


    /**
     * The main method will run through all of the Sorts for the prescribed sizes and produce output for parts A and B
     * <p>
     * Part C should be answered at the VERY TOP of the code in a comment
     */
    public static void main(String[] args) {
        int[] arraySizes = {20, 400, 8000};

        // Part 1: Run sorting algorithms on different sizes
        System.out.println("Part 1:");
        System.out.println("==============================================================");
        for (int arraySizeIndex = 0; arraySizeIndex < arraySizes.length; arraySizeIndex++) {
            int arraySize = arraySizes[arraySizeIndex];
            long myStudentID = 839688; // Use your student ID
            Random rand = new Random(myStudentID);

            int[] unsortedArray = new int[arraySize];
            for (int i = 0; i < unsortedArray.length; i++) {
                unsortedArray[i] = rand.nextInt(1, arraySize);
            }

            // provided header
            int total_runs = (1000_000 / arraySize);
            System.out.printf("\nComparison of sorts, Array size = %,d  total runs = %,d\n", arraySize, total_runs);
            System.out.println("==============================================================");
            System.out.println("Algorithm      Run time     # of compares         ms / compares    checksum");
            testSort("aSort", unsortedArray, total_runs, Assignment2_Start_F24::aSort);
            testSort("bSort", unsortedArray, total_runs, Assignment2_Start_F24::bSort);
            testSort("cSort", unsortedArray, total_runs, Assignment2_Start_F24::cSort);
            testSort("dSort", unsortedArray, total_runs, Assignment2_Start_F24::dSort);
            testSort("eSort", unsortedArray, total_runs, Assignment2_Start_F24::eSort);
            testSort("fSort", unsortedArray, total_runs, Assignment2_Start_F24::fSort);
        }

        // Part 2: Linear search vs Sort + Binary Search
        System.out.println("\nPart 2:");
        System.out.println("==============================================================");

        // Generate random array of 100,000 positive integers
        int arraySizePart2 = 100_000;
        Random rand = new Random();
        int[] largeArray = new int[arraySizePart2];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(1_000_000) + 1;
        }

        // Target value to search for (not in the array)
        int target = -1;

        // Linear search
        long startTime = System.currentTimeMillis();
        boolean linearResult = linearSearch(largeArray, target);
        long endTime = System.currentTimeMillis();
        long linearSearchTime = endTime - startTime;

        System.out.println("Linear search took: " + linearSearchTime + " ms");

        // Sort the array for binary search (use one of your sorts, e.g., eSort)
        int[] sortedArray = largeArray.clone();
        startTime = System.currentTimeMillis();
        Assignment2_Start_F24.eSort(sortedArray);  // Use Quick Sort (eSort) or any other sort method
        endTime = System.currentTimeMillis();
        long sortingTime = endTime - startTime;

        // Binary search after sorting
        startTime = System.currentTimeMillis();
        boolean binaryResult = binarySearch(sortedArray, target);
        endTime = System.currentTimeMillis();
        long binarySearchTime = endTime - startTime;

        System.out.println("Sorting took: " + sortingTime + " ms");
        System.out.println("Binary search took: " + binarySearchTime + " ms");

        // Total time for Sort + Binary Search
        long totalSortBinaryTime = sortingTime + binarySearchTime;
        System.out.println("Total time for Sort + Binary search: " + totalSortBinaryTime + " ms");

        // Calculate how many linear searches are needed to justify sorting
        long linearSearchesRequired = totalSortBinaryTime / linearSearchTime;
        System.out.println("Number of linear searches required to justify sorting: " + linearSearchesRequired);
    }


    private static void testSort(String sorter, int[] unsortedArray, int totalRuns, SortFunction sortMethod) {
        long totalComparisons = 0;
        long totalTime = 0;
        int checksum = 0;

        // Run sort multiple times
        for (int i = 0; i < totalRuns; i++) {
            // Copy the original unsorted array
            int[] arrayCopy = unsortedArray.clone();

            // Shuffle the array before sorting (to randomize for each run)
            shuffle(arrayCopy);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Run sort and get number of comparisons
            long comparisons = sortMethod.sort(arrayCopy);

            // Stop timing
            long endTime = System.currentTimeMillis();

            // Accumulate time and comparisons
            totalTime += (endTime - startTime);
            totalComparisons += comparisons;

            // Validate sorted array and get checksum
            if (i == 0) {  // Only need to check checksum once
                checksum = ckSumSorted(arrayCopy);
            }
        }


        // Calculate averages
        double avgTimeMs = totalTime / (double) totalRuns;
        double msPerCompare = totalTime / (double) totalComparisons;

        // Print formatted result
        System.out.printf("%-10s", sorter);
        System.out.printf("%,10.7f ms", avgTimeMs);
        System.out.printf("%,14d ops", totalComparisons);
        System.out.printf("%,14.7f ms / op", msPerCompare);
        System.out.printf("%,12d \n", checksum);
    }

    // Part 2
    // Linear search
    public static boolean linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    // Binary search
    public static boolean binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (array[middle] == value) {
                return true;
            } else if (array[middle] < value) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return false;
    }


}

// Functional interface for the sorting methods
@FunctionalInterface
interface SortFunction {
    long sort(int[] array);
}

