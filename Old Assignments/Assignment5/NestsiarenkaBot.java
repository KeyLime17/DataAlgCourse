import battleship.BattleShip3;
import battleship.BattleShipBot;

import java.awt.*;
import java.util.*;

/**
 * The Better version of ExampleBot
 */
public class NestsiarenkaBot implements BattleShipBot {
    private int gameSize;
    private BattleShip3 battleShip;
    private Random random;

    //Track fire shots to not hit the same spot
    //and set up shots for next nearby targets
    private Set<Point> firedShots;
    private Queue<Point> nextTarget;
    //Track the last shot, or use in finding ship orientation
    private Point lastShot = null;
    //Track number of ships sunk
    private int sunkCount = 0;

    /**
     * Constructor keeps a copy of the BattleShip instance
     * Create instances of any Data Structures and initialize any variables here
     * @param b previously created battleship instance - should be a new game
     */

    @Override
    public void initialize(BattleShip3 b) {
        battleShip = b;
        gameSize = b.BOARD_SIZE;


        // Need to use a Seed if you want the same results to occur from run to run
        // This is needed if you are trying to improve the performance of your code
        random = new Random(0xAAAAAAAA);   // Needed for random shooter - not required for more systematic approaches

        firedShots = new HashSet<>();
        nextTarget = new LinkedList<>();
    }


    /*
------------------------------------------------------------
B A T T L E S H I P - Version 3.0 [November 13th, 2024]
BattleShip 3 - Results for NestsiarenkaBot
Author : Makar Nestsiarenka
------------------------------------------------------------
The shot performance 10000 games    = 106.15
Time required to complete 10000 games = 533 ms
------------------------------------------------------------
Initial implementation

for later
- Checkerboard pattern to reduce shots amount? (in fireshot method)
- rn if a ship is hit, it shoots at neighboring hits instead of pathing in the direction the ship is in, (in validation method)
- numberOfShipsSunk()?

After adding pathing for shit direction
BattleShip 3 - Results for NestsiarenkaBot
Author : Makar Nestsiarenka
------------------------------------------------------------
The shot performance 10000 games    = 99.76
Time required to complete 10000 games = 483 ms
------------------------------------------------------------
     */

    /**
     * Create a random shot and calls the battleship shoot method
     * Put all logic here (or in other methods called from here)
     * The BattleShip API will call your code until all ships are sunk
     */
    @Override
    public void fireShot() {
        Point shot;

        // Check if we have known targets
        if (!nextTarget.isEmpty()) {
            // Target mode: Shoot queued targets
            shot = nextTarget.poll();
        } else {
            // Hunt mode: Generate a random shot
            do {
                int x = random.nextInt(gameSize);
                int y = random.nextInt(gameSize);
                shot = new Point(x, y);
            } while (firedShots.contains(shot)); // Ensure no duplicate shots
        }

        // Add the shot to fired shots
        firedShots.add(shot);
        // Fire the shot and check if it hits
        boolean hit = battleShip.shoot(shot);
        /*
        // Check if a ship has been sunk
        int currentSunkCount = battleShip.numberOfShipsSunk();
        // To make sure it clears the queue once sunk
        if (currentSunkCount > sunkCount) {
            nextTarget.removeIf(target -> isAdjascent(target));
            lastShot = null;
            sunkCount = currentSunkCount;
        }

         */

        if (hit) {
            // First hit or no orientation determined yet
            if (lastShot == null) {
                addNeighbor(shot); // Add all neighbors
            } else {
                // Determine orientation and prioritize neighbors
                if (lastShot.x == shot.x) { // Vertical orientation
                    neighborValidation(shot.x, shot.y + 1); // Down
                    neighborValidation(shot.x, shot.y - 1); // Up
                } else if (lastShot.y == shot.y) { // Horizontal orientation
                    neighborValidation(shot.x + 1, shot.y); // Right
                    neighborValidation(shot.x - 1, shot.y); // Left
                } else {
                    addNeighbor(shot); // Uncertain orientation
                }
            }
            lastShot = shot; // Update last hit point only on a hit
        }
    }
    /**
     * Adds neighboring cells from the last hit to a queue
     * it landed on a ship
     */

    private void addNeighbor(Point hit) {
        int x = hit.x;
        int y = hit.y;

        //all possible neighbors with validation, and makes sure its within the bound and wasnt shot at already
        neighborValidation(x + 1, y); //Right
        neighborValidation(x - 1, y); //Left
        neighborValidation(x, y + 1); //Down
        neighborValidation(x, y - 1); //Up

    }
    /**
     * Validates that the addNeighbor method actually queues a neighboring cell
     */
    private void neighborValidation(int x, int y) {
        Point point = new Point(x, y);
        //Looks dumb, i know
        if (x >= 0 &&
                x < gameSize &&
                y >= 0 &&
                y < gameSize &&
                !firedShots.contains(point))
        {nextTarget.add(point);}
    }
    //Check if shot is agjacent to any cell of the sunk ship
    /*
    Useless, actually ended up increasing amount of time and shots it takes, couldnt figure it out
    private boolean isAdjascent(Point hit) {
        for (int x = hit.x -1; x <= hit.x +1; x++) {
            for (int y = hit.y - 1; y <= hit.y + 1; y++)
                if (x >= 0 && x < gameSize && y >= 0 && y < gameSize && battleShip.shoot(new Point(x, y)))
                    return true;
        }
        return false;
    }

     */

    /**
     * Authorship of the solution - must return names of all students that contributed to
     * the solution
     * @return names of the authors of the solution
     */

    @Override
    public String getAuthors() {
        return "Makar Nestsiarenka";
    }
}
