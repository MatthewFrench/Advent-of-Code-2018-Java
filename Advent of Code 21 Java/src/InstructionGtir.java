public class InstructionGtir extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //sets register C to 1 if value A is greater than register B.
        // Otherwise, register C is set to 0.
        s.AfterRegisterStates[C] = A > s.BeforeRegisterStates[B] ? 1 : 0;
    }
}
