import java.util.ArrayList;

public abstract class Instruction {
    public int OpCode = -1;
    public ArrayList<Sample> MatchingSamples = new ArrayList<>();
    public void Run(Sample s) {}
    public boolean Matches(Sample s) { return false; }
    public String GetName() {return ""; }
}
