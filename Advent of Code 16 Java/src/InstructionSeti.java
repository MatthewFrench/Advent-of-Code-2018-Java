public class InstructionSeti extends Instruction {
    public void Run(Sample s) {
        //stores value A into register C. (Input B is ignored.)
        s.AfterRegisterStates[s.C] = s.A;
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
        return "InstructionSeti";
    }
}
