import java.util.HashSet;
import java.util.Set;

public class Node {
    public String Letter = "";
    public Set<Node> FinishNodesAfter = new HashSet<>();
    public Set<Node> FinishNodesBefore = new HashSet<>();
    public boolean IsFinished = false;
    public int Step = 0;
    public Node(String letter) {
        Letter = letter;
    }
    public boolean HasUnfinishedNodesBefore() {
        for (Node n: FinishNodesBefore) {
            if (!n.IsFinished) {
                return true;
            }
        }
        return false;
    }
    public int GetLargestStepNodesBefore() {
        int step = 0;
        for (Node n : FinishNodesBefore) {
            if (n.Step >= step) {
                step = n.Step + 1;
            }
        }
        return step;
    }
}
