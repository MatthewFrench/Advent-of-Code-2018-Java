public class InstructionAddi extends Instruction {
    public void Run(Sample s) {
        //stores into register C the result of adding register A and value B.
        s.AfterRegisterStates[s.C] = s.BeforeRegisterStates[s.A] + s.B;
    }
    public boolean Matches(Sample s) {
        Sample newSample = new Sample(s);
        Run(newSample);
        if (newSample.OutputMatches(s)) {
            MatchingSamples.add(s);
            return true;
        }
        return false;
    }
    public String GetName() {
        return "InstructionAddi";
    }
}
