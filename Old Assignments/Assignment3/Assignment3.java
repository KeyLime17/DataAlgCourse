import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Assignment3 {
    /**
     * Main method to perform word counting and spell-checking analysis
     * on a large text file. Measures the efficiency of different search
     * techniques for dictionary lookups: linear search, binary search, and hash set search.
     *
     * @param args command-line arguments (not used)
     * @throws FileNotFoundException if the input file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {

        //Part A
        String filename = "src/WarAndPeace.txt";
        String dictionaryFilename = "src/US.txt";
            // Timer
        long startTotalTimeA = System.currentTimeMillis();

        List<BookWord> wordsList = new ArrayList<>();
        int totalWordCount = countWords(filename, wordsList);

        ArrayList<BookWord> dictionaryList = loadDictionaryArray(dictionaryFilename);
        Collections.sort(dictionaryList);
        SimpleHashSet<BookWord> dictionaryHashSet = loadDictionaryHashSet(dictionaryFilename);

        int uniqueWordCount = wordsList.size();

        int[] notFoundCounts = new int[3];
        long[] times = new long[3];

        // Stop timer
        long endTotalTimeA = System.currentTimeMillis();

        System.out.println("Assignment #3, analysis of " + filename);
        System.out.println("PART A\n======");
        System.out.printf("There are a total of %,d words in %s.\n", totalWordCount, filename);
        System.out.printf("There are a total of %,d unique words in %s\n", uniqueWordCount, filename);

        // Print timing and results for each search method
        System.out.println("Dictionary Search, timing for 3 methods:");
        // Perform a linear search using ArrayList.contains and record the time and count of words not found
        times[0] = System.currentTimeMillis();
        notFoundCounts[0] = countNotInDictionaryList(wordsList, dictionaryList);
        times[0] = System.currentTimeMillis() - times[0];
        System.out.printf("LINEAR SEARCH - %,d words not found in dictionary - time = %,d ms\n", notFoundCounts[0], times[0]);

        // Perform a binary search using Collections.binarySearch on the sorted ArrayList dictionary
        times[1] = System.currentTimeMillis();
        notFoundCounts[1] = countNotInDictionaryBinarySearch(wordsList, dictionaryList);
        times[1] = System.currentTimeMillis() - times[1];
        System.out.printf("BINARY SEARCH - %,d words not found in dictionary - time = %,d ms\n", notFoundCounts[1], times[1]);

        // Perform a hash-based search using SimpleHashSet.contains and record the time and count
        times[2] = System.currentTimeMillis();
        notFoundCounts[2] = countNotInDictionaryHashSet(wordsList, dictionaryHashSet);
        times[2] = System.currentTimeMillis() - times[2];
        System.out.printf("HASHSET SEARCH - %,d words not found in dictionary - time = %,d ms\n", notFoundCounts[2], times[2]);

        long PartATime = endTotalTimeA - startTotalTimeA;
        System.out.printf("TOTAL TIME for all of PART A = %,d ms\n", (PartATime));

        long startTotalTimeB = System.currentTimeMillis();
        // Part B
        double[] proximityResults = findProximity(wordsList);
        System.out.println("\nPART B\n======");
        System.out.printf("The total sum of distances between the matched pairs 'war' and 'peace' = %.0f\n", proximityResults[0]);
        System.out.printf("The average distance between the matched pairs 'war' and 'peace' = %.2f\n", proximityResults[1]);
        long endTotalTimeB = System.currentTimeMillis();
        long PartBTime = endTotalTimeB - startTotalTimeB;
        System.out.printf("TOTAL TIME for all of PART B = %,d ms\n", (PartBTime));

        // Total time
        System.out.printf("TOTAL RUNTIME (Part A + Part B) = %,d ms\n", PartATime + PartBTime);
    }

    // ================== Part B methods ================

    private static double[] findProximity(List<BookWord> wordList) {
        LinkedList<Integer> warPos = new LinkedList<>();
        LinkedList<Integer> peacePos = new LinkedList<>();

        int position = 1;
        for (BookWord word : wordList) {
            if (word.getText().equals("war")) {
                warPos.add(position);
            } else if (word.getText().equals("peace")) {
                peacePos.add(position);
            }
            position++;
        }

        int totalDistance = 0;
        int matchedPairs = 0;

        for (int peace : peacePos) {
            Integer closestWar = null;
            int closestDistance = Integer.MAX_VALUE;

            for (int war : warPos) {
                int distance = Math.abs(war - peace);

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestWar = war;
                }
            }
            if (closestWar != null) {
                totalDistance += closestDistance;
                matchedPairs++;
                warPos.remove(closestWar);
            }
        }
        double averageDistance = matchedPairs > 0 ? (double) totalDistance / matchedPairs : 0;

        return  new double[]{totalDistance, averageDistance};
    }



    // =============== Part A methods ========================

    /**
     * Reads words from a file, normalizes them to lowercase, and adds each unique word
     * to a list of BookWord objects while counting total word occurrences.
     *
     * @param filename the path to the text file
     * @param wordsList the list to store unique BookWord objects
     * @return the total word count
     * @throws FileNotFoundException if the file cannot be found
     */
    private static int countWords(String filename, List<BookWord> wordsList) throws FileNotFoundException {
        String regEx = "\\.|\\?|\\!|\\s|\"|\\(|\\)|\\,|\\_|\\-|\\:|\\;|\\n";

        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(regEx);

        Map<String, BookWord> wordMap = new HashMap<>();
        int totalWords = 0;

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase().trim();
            if (!word.isEmpty()) {
                totalWords++;
                wordMap.putIfAbsent(word, new BookWord(word));
                wordMap.get(word).incrementCount();
            }
        }
        scanner.close();
        wordsList.addAll(wordMap.values());
        return totalWords;
    }

    /**
     * Loads words from the dictionary file into an ArrayList, normalizing each word to lowercase
     * for case-insensitive lookups.
     *
     * @param dictionaryFilename the path to the dictionary file
     * @return an ArrayList of BookWord objects for all dictionary entries
     * @throws FileNotFoundException if the file cannot be found
     */
    private static ArrayList<BookWord> loadDictionaryArray(String dictionaryFilename) throws FileNotFoundException {
        ArrayList<BookWord> dictionaryList = new ArrayList<>();
        File file = new File(dictionaryFilename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase().trim();
            if (!word.isEmpty()) {
                dictionaryList.add(new BookWord(word));
            }
        }
        scanner.close();
        return dictionaryList;
    }

    /**
     * Loads words from the dictionary file into a SimpleHashSet, normalizing each word to lowercase
     * for case-insensitive lookups.
     *
     * @param dictionaryFilename the path to the dictionary file
     * @return a SimpleHashSet of BookWord objects for all dictionary entries
     * @throws FileNotFoundException if the file cannot be found
     */
    private static SimpleHashSet<BookWord> loadDictionaryHashSet(String dictionaryFilename) throws FileNotFoundException {
        SimpleHashSet<BookWord> dictionaryHashSet = new SimpleHashSet<>();
        File file = new File(dictionaryFilename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase().trim();
            if (!word.isEmpty()) {
                dictionaryHashSet.insert(new BookWord(word));
            }
        }
        scanner.close();
        return dictionaryHashSet;
    }

    /**
     * Counts words in the book that are not found in the dictionary using linear search.
     *
     * @param wordsList list of BookWord objects representing words in the book
     * @param dictionaryList ArrayList dictionary to search against
     * @return the count of words not found in the dictionary
     */
    private static int countNotInDictionaryList(List<BookWord> wordsList, ArrayList<BookWord> dictionaryList) {
        int notFoundCount = 0;
        for (BookWord word : wordsList) {
            if (!dictionaryList.contains(word)) {
                notFoundCount++;
            }
        }
        return notFoundCount;
    }

    /**
     * Counts words in the book that are not found in the dictionary using binary search on a sorted ArrayList.
     *
     * @param wordsList list of BookWord objects representing words in the book
     * @param dictionaryList sorted ArrayList dictionary to search against
     * @return the count of words not found in the dictionary
     */
    private static int countNotInDictionaryBinarySearch(List<BookWord> wordsList, ArrayList<BookWord> dictionaryList) {
        int notFoundCount = 0;
        for (BookWord word : wordsList) {
            int index = Collections.binarySearch(dictionaryList, word, Comparator.comparing(BookWord::getText));
            if (index < 0) {
                notFoundCount++;
            }
        }
        return notFoundCount;
    }

    /**
     * Counts words in the book that are not found in the dictionary using a hash set for fast lookup.
     *
     * @param wordsList list of BookWord objects representing words in the book
     * @param dictionaryHashSet hash set dictionary to search against
     * @return the count of words not found in the dictionary
     */
    private static int countNotInDictionaryHashSet(List<BookWord> wordsList, SimpleHashSet<BookWord> dictionaryHashSet) {
        int notFoundCount = 0;
        for (BookWord word : wordsList) {
            if (!dictionaryHashSet.contains(word)) {
                notFoundCount++;
            }
        }
        return notFoundCount;
    }
}
