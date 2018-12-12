import java.util.ArrayList;

public class GridPosition {
    ArrayList<Integer> IDs;
    int Step;
    int X;
    int Y;
    boolean IsEdge;
    public GridPosition(int step, int x, int y, boolean isEdge) {
        IDs = new ArrayList<>();
        Step = step;
        X = x;
        Y = y;
        IsEdge = isEdge;
    }
}
