/**
 *  COMP-10205 - Starting code for Assignment # 2
 *
 * // statement of authorship goes here ...
 *
 *
 * // Answers for Part 3 goes here...
 *
 */

import java.util.Random;

/**
 * Assignment # 2
 *
 * I, Makar Nestsiarenka 000839688, certify that this work is my own work and that I did not consult external resources including artificial intelligence software to complete this assignment without due acknowledgement.
 * I further certify that I did not provide my solution to any other students, nor will I provide it to future students taking this course at a later date.
 *
 * Part 3:
 *
 * a. fastest to slowest for 20 elements:
 * The ms are so small I had used ops to measure
 *    1. eSort (Quick Sort)
 *    2. bSort (Counting Sort)
 *    3. cSort (Merge Sort)
 *    3. dSort (Insertion Sort)
 *    4. aSort (Selection Sort)
 *    6. fSort (Bubble Sort)
 *
 *
 * b. fastest to slowest for 8000 elements:
 *    1. eSort (Quick Sort)
 *    2. bSort (Counting Sort)
 *    3. cSort (Merge Sort)
 *    4. dSort (Insertion Sort)
 *    5. aSort (Selection Sort)
 *    6. fSort (Bubble Sort)
 *
 * c.
 * The fasted sorting algorithms are bsort and esort,
 * because counting sort would use direct counting rather than comparisons,
 * and quick sort is extremely fast in practice, since it is able to perform better with large datasets compared to other algorithms
 *
 * d. Sort Algorithm Identification:
 *
 * ==================================================================
 *  Sort    Sort Algorithm     Big O (time)       Big O (space)
 * -----------------------------------------------------------------
 *  aSort   Selection Sort     O(n^2)             O(1)
 *  bSort   Counting Sort      O(n + k)           O(k)
 *  cSort   Merge Sort         O(n log n)         O(n)
 *  dSort   Insertion Sort     O(n^2)             O(1)
 *  eSort   Quick Sort         O(n log n)         O(log n)           (best sort)
 *  fSort   Bubble Sort        O(n^2)             O(1)
 *  -----------------------------------------------------------------
 */

/**
 * All methods are static to the class - functional style
 *
 * @author mark.yendt
 */
public class Assignment2_Start_W25 {
    /**
     * The swap method swaps the contents of two elements in an int array.
     *
     * @param array where elements are to be swapped.
     * @param a The subscript of the first element.
     * @param b The subscript of the second element.
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
        long comparisons = 0;

        int startScan;   // Starting position of the scan
        int index;       // To hold a subscript value
        int minIndex;    // Element with smallest value in the scan
        int minValue;    // The smallest value found in the scan

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
        long comparisons = 0;
        int count = 0;

        int min = array[0];
        int max = array[0];
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
    /** Merge
     * ---------------------------- c Sort ---------------------------------------
     */

    public static long cSort(int inputArray[]) {
        int length = inputArray.length;
        // Create temporary array only once
        int[] workingArray = new int[inputArray.length];
        return doCSort(inputArray, workingArray, 0, inputArray.length - 1);
    }

    private static long doCSort(int[] inputArray, int[] workingArray, int lowerIndex, int higherIndex) {
        long comparisons = 0;
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            comparisons += doCSort(inputArray, workingArray, lowerIndex, middle);
            comparisons += doCSort(inputArray, workingArray, middle + 1, higherIndex);
            comparisons += part2(inputArray, workingArray, lowerIndex, middle, higherIndex);
        }
        return comparisons;
    }

    private static long part2(int[] inputArray, int[] workingArray, int lowerIndex, int middle, int higherIndex) {
        long comparisons = 0;
        for (int i = lowerIndex; i <= higherIndex; i++) {
            workingArray[i] = inputArray[i];
        }
        int i1 = lowerIndex;
        int i2 = middle + 1;
        int newIndex = lowerIndex;
        while (i1 <= middle && i2 <= higherIndex) {
            comparisons++;
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
        return comparisons;
    }

    /** Insertion
     * ---------------------------- d Sort ---------------------------------------
     */

    public static long dSort(int[] array) {
        long comparisons = 0;
        int unsortedValue;  // The first unsorted value
        int scan;           // Used to scan the array

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

            // Insert the unsorted value in its proper position
            // within the sorted subset.
            array[scan] = unsortedValue;
        }
        return comparisons;
    }

    /** Quick
     * ---------------------------- e Sort ---------------------------------------
     */

    public static long eSort(int array[]) {
        return doESort(array, 0, array.length - 1);
    }

    /**
     * The doESort method uses the quicksort algorithm to sort an int array.
     *
     * @param array The array to sort.
     * @param start The starting subscript of the list to sort
     * @param end The ending subscript of the list to sort
     */
    private static long doESort(int array[], int start, int end) {
        long comparisons = 0;
        int pivotPoint;

        if (start < end) {
            // Get the pivot point.
            pivotPoint = part1(array, start, end);
            comparisons += (end - start);
            // Sort the first sub list
            doESort(array, start, pivotPoint - 1);
            // Sort the second sub list.
            doESort(array, pivotPoint + 1, end);
        }
        return comparisons;
    }

    /**
     * The part1 method selects a pivot value in an array and arranges the
     * array into two sub lists. All the values less than the pivot will be
     * stored in the left sub list and all the values greater than or equal to
     * the pivot will be stored in the right sub list.
     *
     * @param array The array to partition.
     * @param start The starting subscript of the area to partition.
     * @param end The ending subscript of the area to partition.
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

    /** Bubble
     * ---------------------------- f Sort ---------------------------------------
     */

    public static long fSort(int[] array) {
        long comparisons = 0;
        int lastPos;     // Position of last element to compare
        int index;       // Index of an element to compare

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

    public static boolean linearSearch(int[] array, int searchItem) {
        for (int value : array) {
            if (value == searchItem) {
                return true;  // Found
            }
        }
        return false;  // Not Found
    }

    /**
     * A demonstration of recursive counting in a Binary Search
     * @param array - array to search
     * @param low - the low index - set to 0 to search whiole array
     * @param high - set to length of array to search whole array
     * @param value - item to search for
     * @param count - Used in recursion to accumulate the number of comparisons made (set to 0 on initial call)
     *
     * @return a pair - indicating the index of the match, and the # of steps to find it.
     */
    private static int[] binarySearchR(int[] array, int low, int high, int value, int count)
    {
        int middle;     // Mid point of search

        // Test for the base case where the value is not found.
        if (low > high)
            return new int[] {-1,count};

        // Calculate the middle position.
        middle = (low + high) / 2;

        // Search for the value.
        if (array[middle] == value)
            // Found match return the index
            return new int[] {middle, count};

        else if (value > array[middle])
            // Recursive method call here (Upper half of remaining data)
            return binarySearchR(array, middle + 1, high, value, count+1);
        else
            // Recursive method call here (Lower half of remaining data)
            return binarySearchR(array, low, middle - 1, value, count+1);
    }


    /**
     * The main method will run through all of the Sorts for the prescribed sizes and produce output for parts A and B
     *
     * Part C should be answered at the VERY TOP of the code in a comment
     *
     */
    public static void main(String[] args) {
        int arraySize = 400;
        long myStudentID = 839688; // "Integer number too large" had to cut the 3 0's out
        Random rand = new Random(myStudentID);
        int[] array = new int[arraySize];
        for(int i =0; i < array.length; i++) {
            array[i] = rand.nextInt(1,arraySize);
        }

        //Create a unsorted array
        int[] unsorted = new int[arraySize];
        for (int i = 0; i < unsorted.length; i++) {
            unsorted[i] = rand.nextInt(1, arraySize);
        }
        int total_runs = (1000_000 / arraySize);

        //Text print
        System.out.printf("\nComparison of sorts, Array size = %,d  total runs = %,d\n",
                arraySize, total_runs);
        System.out.println("==============================================================");
        System.out.println(
                "Algorithm      Run time     # of compares         ms / compares    checksum");


        //Run each sorting algorithm
        sort("aSort", unsorted, total_runs, Assignment2_Start_W25::aSort);
        sort("bSort", unsorted, total_runs, Assignment2_Start_W25::bSort);
        sort("cSort", unsorted, total_runs, Assignment2_Start_W25::cSort);
        sort("dSort", unsorted, total_runs, Assignment2_Start_W25::dSort);
        sort("eSort", unsorted, total_runs, Assignment2_Start_W25::eSort);
        sort("fSort", unsorted, total_runs, Assignment2_Start_W25::fSort);

        //Part 2
        System.out.println("\nPart 2: Linear Search vs. Sort + Binary Search");
        System.out.println("==============================================================");

        //Array up to 100k elements
        int arraySizePart2 = 100000;
        Random randPart2 = new Random();
        int[] largeArray = new int[arraySizePart2];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = randPart2.nextInt(1000000) + 1;
        }

        //Linear Search
        int searchValue = -1;
        long startTime = System.currentTimeMillis();
        boolean found = linearSearch(largeArray, searchValue);
        long endTime = System.currentTimeMillis();
        long linearSearchTime = endTime - startTime;

        System.out.println("Linear Search took: " + linearSearchTime + " ms");

        //Sort array with quicksort (bestsort) and binary search
        int[] sortedArray = largeArray.clone();
        startTime = System.currentTimeMillis();
        eSort(sortedArray);  // Using QuickSort for sorting

        binarySearchR(sortedArray, 0, sortedArray.length - 1, searchValue, 0);
        endTime = System.currentTimeMillis();
        long binarySearchTime = endTime - startTime;

        System.out.println("Binary Search took: " + binarySearchTime + " ms");

        long linearSearchesRequired = binarySearchTime / linearSearchTime;
        System.out.println("Number of linear searches required to justify sorting first: " + linearSearchesRequired);

        System.out.println("\nPart 3: Sorting Speed Analysis");
        System.out.println("==============================================================");

        // Define sizes to test sorting speeds
        int[] sizes = {20, 8000};

        for (int size : sizes) {
            int[] testArray = new int[size];
            Random randTest = new Random();
            for (int i = 0; i < size; i++) {
                testArray[i] = randTest.nextInt(size);
            }

            System.out.printf("\nSorting performance for array size: %,d\n", size);
            System.out.println("--------------------------------------------------------------");
            sort("aSort", testArray.clone(), 1, Assignment2_Start_W25::aSort);
            sort("bSort", testArray.clone(), 1, Assignment2_Start_W25::bSort);
            sort("cSort", testArray.clone(), 1, Assignment2_Start_W25::cSort);
            sort("dSort", testArray.clone(), 1, Assignment2_Start_W25::dSort);
            sort("eSort", testArray.clone(), 1, Assignment2_Start_W25::eSort);
            sort("fSort", testArray.clone(), 1, Assignment2_Start_W25::fSort);
            }
    }

    /**
     * Method to test every sorting algorithm by running multiple times
     *
     * @param sorter The name of the sorting algorithm (used in the output)
     * @param unsorted The array to be sorted
     * @param totalRuns The number of times the sort should be run to get reliable results
     * @param sortMethod A functional interface representing the sorting algorithm
     */
    private static void sort(String sorter, int[] unsorted, int totalRuns, SortFunction sortMethod) {
        long totalComparisons = 0;
        long totalTime = 0;
        int checksum = 0;

        for (int i = 0; i < totalRuns; i++) {
            int[] arrayCopy = unsorted.clone();
            shuffle(arrayCopy);

            long startTime = System.currentTimeMillis();
            totalComparisons += sortMethod.sort(arrayCopy);
            long endTime = System.currentTimeMillis();

            totalTime += (endTime - startTime);
            if (i == 0) checksum = ckSumSorted(arrayCopy);
        }

        double avgTimeMs = totalTime / (double) totalRuns;
        double msPerCompare = totalComparisons == 0 ? 0 : totalTime / (double) totalComparisons;

        System.out.printf("%-10s", sorter);
        System.out.printf("%,10.7f ms", avgTimeMs);
        System.out.printf("%,14d ops", totalComparisons);
        System.out.printf("%,14.7f ms / op", msPerCompare);
        System.out.printf("%,12d \n", checksum);
    }

    @FunctionalInterface
    interface SortFunction {
        long sort(int[] array);
    }
}
