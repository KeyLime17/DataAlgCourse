import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Makar Nestsiarenka, 000839688
     * The Thin Ice of Barsoom, detects weak spots and cracks in ice sheets.
     */
    public static void main(String[] args) {
        String filename = "src/ICESHEETS_F24.TXT";

        try {
            int[][][] sheets = loadIceSheets(filename);
            processIceSheets(sheets);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Loads the ice sheet data from a file.
     *
     * @param filename the file to read ice sheet data from
     * @return a 3D array representing ice sheets
     * @throws IOException if the file cannot be read
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
     * Processes ice sheets, finding weak spots and cracks, and prints the results.
     *
     * @param sheets a 3D array of ice sheet data
     */
    public static void processIceSheets(int[][][] sheets) {
        int totalWeakSpots = 0;
        int maxWeakSpots = 0;
        int sheetWithMostWeakSpots = 0;
        int totalCracks = 0;

        System.out.println("=== PART A ===");

        // Traverse each sheet to find weak spots and cracks
        for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
            int weakSpotsOnCurrent = 0;
            int[][] sheet = sheets[sheetIndex];

            for (int r = 0; r < sheet.length; r++) {
                for (int c = 0; c < sheet[0].length; c++) {
                    if (sheet[r][c] <= 200 && sheet[r][c] % 50 == 0) {
                        weakSpotsOnCurrent++;
                        // Check if this weak spot is a crack
                        if (isCrack(sheet, r, c)) {
                            System.out.println("CRACK DETECTED @ [Sheet[" + sheetIndex + "](" + r + "," + c + ")]");
                            totalCracks++; // Increment total cracks here
                        }
                    }
                }
            }

            totalWeakSpots += weakSpotsOnCurrent;

            if (weakSpotsOnCurrent > maxWeakSpots) {
                maxWeakSpots = weakSpotsOnCurrent;
                sheetWithMostWeakSpots = sheetIndex;
            }

            System.out.println("Sheet " + sheetIndex + " has " + weakSpotsOnCurrent + " weak spots");
        }

        // Part A summary
        System.out.println("Total weak spots on all sheets = " + totalWeakSpots);
        System.out.println("Sheet " + sheetWithMostWeakSpots + " has the highest number of weak spots = " + maxWeakSpots);

        // Part B summary
        System.out.println("The total number of weak spots that have cracked = " + totalCracks);
        double crackFraction = (double) totalCracks / totalWeakSpots;
        System.out.printf("The fraction of weak spots that are also cracks is %.3f\n", crackFraction);
    }

    /**
     * Determines if a weak spot has a neighboring value divisible by 10 (i.e., if it's a crack).
     *
     * @param sheet the 2D array of the current ice sheet
     * @param row the row of the weak spot
     * @param col the column of the weak spot
     * @return true if the weak spot is a crack, false otherwise
     */
    public static boolean isCrack(int[][] sheet, int row, int col) {
        int rows = sheet.length;
        int cols = sheet[0].length;

        // Check all 8 neighbors
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newrow = row + rowOffsets[i];
            int newcol = col + colOffsets[i];

            // Ensure newRow and newCol are within bounds
            if (newrow >= 0 && newrow < rows && newrow >= 0 && newrow < cols) {
                if (sheet[newrow][newrow] % 10 == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
