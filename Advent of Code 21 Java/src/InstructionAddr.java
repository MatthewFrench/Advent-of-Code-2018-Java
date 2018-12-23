public class InstructionAddr extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //stores into register C the result of adding register A and register B.
        s.AfterRegisterStates[C] = s.BeforeRegisterStates[B] + s.BeforeRegisterStates[A];
    }
}
