import java.util.HashSet;
import java.util.Set;

public class Node2 {
    public String Letter = "";
    public Set<Node2> FinishNodesAfter = new HashSet<>();
    public Set<Node2> FinishNodesBefore = new HashSet<>();
    public boolean IsFinished = false;
    public int Step = 0;
    public int TotalSeconds = 0;
    public int SecondsLeft = 0;
    public Node2(String letter) {
        Letter = letter;
    }
    public boolean HasUnfinishedNodesBefore() {
        for (Node2 n: FinishNodesBefore) {
            if (!n.IsFinished) {
                return true;
            }
        }
        return false;
    }
    public int GetLargestStepNodesBefore() {
        int step = 0;
        for (Node2 n : FinishNodesBefore) {
            if (n.Step >= step) {
                step = n.Step + 1;
            }
        }
        return step;
    }
}
