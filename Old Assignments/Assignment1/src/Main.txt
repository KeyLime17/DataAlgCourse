import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Thin Ice of Barsoom
 *
 * I, Makar Nestsiarenka 000839688, certify that this work is my original work, and I did not consult external resources
 * including artificial intelligence software to complete this assignment without due acknowledgement, I further
 * certify I did not provide my solution to other students, no will I provide it to future students taking this course
 * at a later date.
 */

/**
 * PART C: Discussion
 *
 * 1:
 * Right now, the program only passes the total number of weak spots from Part A to Part B,
 * but it doesn’t make full use of the data.
 * A better approach would be to save the exact coordinates of the weak spots found in Part A and then reuse those in Part B.
 * This would make the process more efficient by avoiding redundant checks.
 *
 * 2:
 * Right now the algorithm uses an offset array to check in its 8 neighbors, up, down, left, right, and diagonals.
 * For each neighbor the algorithm checks if the value is divisible by 10. if any neighbor meets this condition, the weakspot is classified as a crack
 */
public class Main {

    public static void main(String[] args) {
        String filename = "src/ICESHEETS_F24.TXT";

        try {
            int[][][] sheets = loadIceSheets(filename);
            int[] partAResults = PartA(sheets);
            PartB(sheets, partAResults[0]);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     *  Load from ICESHEETS_F24.txt into a 3D Array
     */
    public static int[][][] loadIceSheets(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));
        int numberOfSheets = scanner.nextInt();
        int[][][] sheets = new int[numberOfSheets][][];

        for (int i = 0; i < numberOfSheets; i++) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            sheets[i] = new int[rows][cols];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    sheets[i][r][c] = scanner.nextInt();
                }
            }
        }

        scanner.close();
        return sheets;
    }

    /**
     * Processes Part A: Finds and summarizes weak spots on each sheet.
     * PART A
     * finds weakspots from each sheet, and provides final summary
     */
    public static int[] PartA(int[][][] sheets) {
        int totalWeakSpots = 0;
        int maxWeakSpots = 0;
        int sheetWithMostWeakSpots = 0;

        System.out.println("PART A:");

        for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
            int weakSpotsOnCurrent = countWeakSpots(sheets[sheetIndex]);
            totalWeakSpots += weakSpotsOnCurrent;

            if (weakSpotsOnCurrent > maxWeakSpots) {
                maxWeakSpots = weakSpotsOnCurrent;
                sheetWithMostWeakSpots = sheetIndex;
            }

            System.out.println("Sheet " + sheetIndex + " has " + weakSpotsOnCurrent + " weak spots");
        }

        System.out.println("Total weak Spots on all Sheets = " + totalWeakSpots);
        System.out.println("Sheet " + sheetWithMostWeakSpots + " has the highest number of weak Spots = " + maxWeakSpots);
        return new int[]{totalWeakSpots, sheetWithMostWeakSpots};
    }

    /**
     * PART B
     * finds cracks and calculates the fraction of weak spots
     */
    public static void PartB(int[][][] sheets, int totalWeakSpots) {
        int totalCracks = 0;

        System.out.println("\nPART B:");

        for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
            int[][] sheet = sheets[sheetIndex];

            for (int r = 0; r < sheet.length; r++) {
                for (int c = 0; c < sheet[0].length; c++) {
                    if (sheet[r][c] <= 200 && sheet[r][c] % 50 == 0) {
                        if (isCrack(sheet, r, c)) {
                            totalCracks++;
                           // System.out.println("CRACK DETECTED @ [Sheet " + sheetIndex + "](" + r + ", " + c + ")");
                        }
                    }
                }
            }
        }

        System.out.println("SUMMARY");
        System.out.println("The total number of weak spots that have cracked = " + totalCracks);
        double crackFraction = (double) totalCracks / totalWeakSpots;
        System.out.printf("The fraction of weak spots that are also cracks is %.3f\n", crackFraction);
    }

    /**
     * Counts all weak spots in a sheet
     */
    public static int countWeakSpots(int[][] sheet) {
        int weakSpots = 0;

        for (int r = 0; r < sheet.length; r++) {
            for (int c = 0; c < sheet[0].length; c++) {
                if (sheet[r][c] <= 200 && sheet[r][c] % 50 == 0) {
                    weakSpots++;
                }
            }
        }

        return weakSpots;
    }

    /**
     * Checks if a weak spot is a crack by looking at its neighbors with
     * a row and column offset
     */
    public static boolean isCrack(int[][] sheet, int row, int col) {
        int rows = sheet.length;
        int cols = sheet[0].length;

        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                if (sheet[newRow][newCol] % 10 == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}


