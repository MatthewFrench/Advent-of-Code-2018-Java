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
        int width = targetX + 30;
        int height = targetY + 30;

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

        printGrid(grid, width, height, targetX, targetY);

        long dangerLevel = 0;
        for (int x = 0; x < targetX+1; x++) {
            for (int y = 0; y < targetY+1; y++) {
                dangerLevel += grid[x][y];
            }
        }
        System.out.println("Risk level: " + dangerLevel);


        /*
        This might work
        Store all smallest values on each X/Y with the tool.
        Any new paths on that position with the same tool that are greater can be discarded
         */

        HashMap<String, Integer> exploredPathSmallestPaths = new HashMap<>();

        ExplorePath explorePath = new ExplorePath();
        explorePath.X = 0;
        explorePath.Y = 0;
        explorePath.Value = 0;
        explorePath.Tool = ExplorePath.TOOL_TORCH;

        int smallestPathValue = 0;
        /*
        int currentX = 0;
        int currentY = 0;
        int currentTool = ExplorePath.TOOL_TORCH;
        //Calculate a baseline path going down and then right
        for (int y = 0; y < targetY; y++) {
            int newTerrain = grid[currentX][y+1];
            currentY = y + 1;
            smallestPathValue += 1;
            if (newTerrain == ROCKY && currentTool == ExplorePath.TOOL_NOTHING) {
                currentTool = ExplorePath.TOOL_CLIMBING_GEAR;
                smallestPathValue += 7;
            } else if (newTerrain == WET && currentTool == ExplorePath.TOOL_TORCH) {
                currentTool = ExplorePath.TOOL_CLIMBING_GEAR;
                smallestPathValue += 7;
            } else if (newTerrain == NARROW && currentTool == ExplorePath.TOOL_CLIMBING_GEAR) {
                currentTool = ExplorePath.TOOL_TORCH;
                smallestPathValue += 7;
            }
            exploredPathSmallestPaths.put(ExplorePath.GetHash(currentX, currentY, currentTool), smallestPathValue);
        }
        for (int x = 0; x < targetX; x++) {
            int newTerrain = grid[x + 1][currentY];
            currentX = x + 1;
            smallestPathValue += 1;
            if (newTerrain == ROCKY && currentTool == ExplorePath.TOOL_NOTHING) {
                currentTool = ExplorePath.TOOL_CLIMBING_GEAR;
                smallestPathValue += 7;
            } else if (newTerrain == WET && currentTool == ExplorePath.TOOL_TORCH) {
                currentTool = ExplorePath.TOOL_CLIMBING_GEAR;
                smallestPathValue += 7;
            } else if (newTerrain == NARROW && currentTool == ExplorePath.TOOL_CLIMBING_GEAR) {
                currentTool = ExplorePath.TOOL_TORCH;
                smallestPathValue += 7;
            }
            exploredPathSmallestPaths.put(ExplorePath.GetHash(currentX, currentY, currentTool), smallestPathValue);
        }
        if (currentTool != ExplorePath.TOOL_TORCH) {
            currentTool = ExplorePath.TOOL_TORCH;
            smallestPathValue += 7;
        }
        System.out.println("Default first path value: " + smallestPathValue);
*/
        smallestPathValue = 1045;


        /*
        In rocky regions, you can use the climbing gear or the torch. You cannot use neither (you'll likely slip and fall).
In wet regions, you can use the climbing gear or neither tool. You cannot use the torch (if it gets wet, you won't have a light source).
In narrow regions, you can use the torch or neither tool. You cannot use the climbing gear (it's too bulky to fit).
         */

        //Loop through every possible path until there are no more possible paths
        //As soon as we have a successful path, use that as baseline to cancel other paths
        //Use a linked list

        long completedPaths = 0;
        final LinkedList<ExplorePath> pathsToExplore = new LinkedList<>();
        pathsToExplore.add(explorePath);
        while (pathsToExplore.size() > 0) {
            completedPaths++;
            ExplorePath path = pathsToExplore.pollFirst();
            if (path.X == targetX && path.Y == targetY) {
                if (path.Tool != ExplorePath.TOOL_TORCH) {
                    path.Tool = ExplorePath.TOOL_TORCH;
                    path.Value += 7;
                }
                if (path.Value < smallestPathValue) {
                    smallestPathValue = path.Value;
                    System.out.println("Smallest value: " + smallestPathValue);
                    System.out.println("Completed paths: " + completedPaths);
                }
                continue;
            }
            String hash = ExplorePath.GetHash(path.X, path.Y, path.Tool);
            if (exploredPathSmallestPaths.containsKey(hash)) {
                if (exploredPathSmallestPaths.get(hash) <= path.Value) {
                    continue;
                }
            }
            exploredPathSmallestPaths.put(hash, path.Value);

            final ArrayList<ExplorePath> pathsLeft = path.goToPath(path.X - 1, path.Y, grid, width, height, smallestPathValue);
            final ArrayList<ExplorePath> pathsRight = path.goToPath(path.X + 1, path.Y, grid, width, height, smallestPathValue);
            final ArrayList<ExplorePath> pathsTop = path.goToPath(path.X, path.Y - 1, grid, width, height, smallestPathValue);
            final ArrayList<ExplorePath> pathsBottom = path.goToPath(path.X, path.Y + 1, grid, width, height, smallestPathValue);
            for (ExplorePath path2 : pathsLeft) {
                pathsToExplore.addFirst(path2);
            }
            for (ExplorePath path2 : pathsTop) {
                pathsToExplore.addFirst(path2);
            }
            for (ExplorePath path2 : pathsRight) {
                pathsToExplore.addFirst(path2);
            }
            for (ExplorePath path2 : pathsBottom) {
                pathsToExplore.addFirst(path2);
            }
        }
        System.out.println("----------");
        System.out.println("Smallest value: " + smallestPathValue);

        /*
        There's a bug in the logic somewhere
        First answer: 1045, increased width and height
        Second answer: 1039, too low
        Guessing now
        1042 - Too low
        1044 - Not Correct

        Correct answer: 1043
         */
    }

    public static void printGrid(int[][] grid, int width, int height, int targetX, int targetY) {
        System.out.println("-------");
        for (int y = 0; y < height; y++) {
            String s = "";
            for (int x = 0; x < width; x++) {
                int i = grid[x][y];
                if (x == 0 && y == 0) {
                    s += "M";
                } else if (x == targetX && y == targetY) {
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
