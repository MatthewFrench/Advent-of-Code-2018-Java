import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    static int ROCKY = 0;
    static int WET = 1;
    static int NARROW = 2;

    public static void main(String[] args) throws Exception {
        part1();
    }

    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");
        int depth = Input.getDepth();
        int targetX = Input.getTargetX();
        int targetY = Input.getTargetY();
        int width = targetX + 20;
        int height = targetY + 20;

        long[][] geologicalIndexGrid = new long[width][height];
        long[][] erosionLevelGrid = new long[width][height];
        int[][] grid = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x == 0 && y == 0) || (x == targetX && y == targetY)) {
                    geologicalIndexGrid[x][y] = 0;
                } else {
                    long geologicalIndex;
                    if (x == 0) {
                        geologicalIndex = y * 48271;
                    } else if (y == 0) {
                        geologicalIndex = x * 16807;
                    } else {
                        geologicalIndex = erosionLevelGrid[x - 1][y] * erosionLevelGrid[x][y - 1];
                    }
                    geologicalIndexGrid[x][y] = geologicalIndex;
                }
                erosionLevelGrid[x][y] = (geologicalIndexGrid[x][y] + depth) % 20183;
                grid[x][y] = (int)(erosionLevelGrid[x][y] % 3);
            }
        }

        printGrid(grid, width, height);

        long dangerLevel = 0;
        for (int x = 0; x < targetX+1; x++) {
            for (int y = 0; y < targetY+1; y++) {
                dangerLevel += grid[x][y];
            }
        }
        System.out.println("Risk level: " + dangerLevel);


        /*
        Find the fastest path from M(0,0) to the Target

        Option 1:
        Grow the fastest path from M to each tile until we eventually solve down to T
        Throw away any paths that are slower into a tile

        Option 2:
        Pick the first path manually, straight down from M then right to T. Mark that as the minutes required to travel
        Queue all future paths in an array to sift through
        Any path that get
         */
        ExplorePath explorePath = new ExplorePath();
        explorePath.X = 0;
        explorePath.Y = 0;
        explorePath.Value = 0;
        explorePath.Tool = ExplorePath.TOOL_TORCH;

        int smallestPathValue = 0;
        int currentX = 0;
        int currentY = 0;
        int currentTool = ExplorePath.TOOL_TORCH;
        //Calculate a baseline path going down and then right
        for (int y = 0; y < targetY; y++) {
            for (int x = 0; x < targetX; x++) {

            }
        }

        //Loop through every possible path until there are no more possible paths
        //As soon as we have a successful path, use that as baseline to cancel other paths
        //Use a linked list
        LinkedList<ExplorePath> pathsToExplore = new LinkedList<>();
        pathsToExplore.add(explorePath);
        while (pathsToExplore.size() > 0) {

        }

    }

    public static void printGrid(int[][] grid, int width, int height) {
        System.out.println("-------");
        for (int y = 0; y < height; y++) {
            String s = "";
            for (int x = 0; x < width; x++) {
                int i = grid[x][y];
                if (x == 0 && y == 0) {
                    s += "M";
                } else if (x == width - 1 && y == height - 1) {
                    s += "T";
                } else if (i == ROCKY) {
                    s += ".";
                } else if (i == WET) {
                    s += "=";
                } else if (i == NARROW) {
                    s += "|";
                }
            }
            System.out.println(s);
        }
    }
}
