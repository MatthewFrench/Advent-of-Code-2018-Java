public class InstructionEqrr extends Instruction {
    public void Run(Sample s) {
        // sets register C to 1 if register A is equal to register B.
        // Otherwise, register C is set to 0.
        s.AfterRegisterStates[s.C] = s.BeforeRegisterStates[s.A] == s.BeforeRegisterStates[s.B] ? 1 : 0;
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
        return "InstructionEqrr";
    }
}
