import java.util.HashSet;

public class ExplorePath {
    static int TOOL_NOTHING = 0;
    static int TOOL_TORCH = 1;
    static int TOOL_CLIMBING_GEAR = 2;

    int X;
    int Y;
    int Tool;
    int Value;
    HashSet<Integer> PreviousLocations = new HashSet<>();
}