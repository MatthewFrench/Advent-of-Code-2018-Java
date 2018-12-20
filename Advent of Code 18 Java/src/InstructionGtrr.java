public class InstructionGtrr extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //sets register C to 1 if register A is greater than register B.
        // Otherwise, register C is set to 0.
        s.AfterRegisterStates[C] =
                s.BeforeRegisterStates[A] > s.BeforeRegisterStates[B] ? 1 : 0;
    }
}
