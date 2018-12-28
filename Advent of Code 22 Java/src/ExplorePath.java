import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ExplorePath {
    static int TERRAIN_ROCKY = 0;
    static int TERRAIN_WET = 1;
    static int TERRAIN_NARROW = 2;

    static int TOOL_NOTHING = 0;
    static int TOOL_TORCH = 1;
    static int TOOL_CLIMBING_GEAR = 2;

    int X;
    int Y;
    int Tool;
    int Value;
    HashSet<Integer> PreviousLocations = new HashSet<>();

     public ExplorePath() {

     }

    public ExplorePath(ExplorePath previous) {
        X = previous.X;
        Y = previous.Y;
        Tool = previous.Tool;
        Value = previous.Value;
        PreviousLocations = new HashSet<>(previous.PreviousLocations);
    }

    ArrayList<ExplorePath> goToPath(
            int x,
            int y,
            int[][] grid,
            int width,
            int height,
            int smallestValue
    ) {
        final ArrayList<ExplorePath> paths = new ArrayList<>();

        if (x < 0 || y < 0 || x >= width || y >= height || Value + 1 >= smallestValue ||
            PreviousLocations.contains(x + y * width)) {
            return paths;
        }

        int nextTerrain = grid[x][y];
        int nextValue = Value + 1;
        if (nextTerrain == TERRAIN_ROCKY && Tool == TOOL_NOTHING) {
            nextValue += 7;
            ExplorePath newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_CLIMBING_GEAR;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);

            newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_TORCH;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);
        } else if (nextTerrain == TERRAIN_WET && Tool == TOOL_TORCH) {
            nextValue += 7;
            ExplorePath newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_CLIMBING_GEAR;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);

            newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_NOTHING;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);
        } else if (nextTerrain == TERRAIN_NARROW && Tool == TOOL_CLIMBING_GEAR) {
            nextValue += 7;
            ExplorePath newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_TORCH;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);

            newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.Tool = TOOL_NOTHING;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);
        } else {
            ExplorePath newPath = new ExplorePath(this);
            newPath.X = x;
            newPath.Y = y;
            newPath.Value = nextValue;
            newPath.PreviousLocations.add(x + y * width);
            paths.add(newPath);
        }

        return paths;
    }

    static String GetHash(int x, int y, int tool) {
         return x+","+y+","+tool;
    }
}