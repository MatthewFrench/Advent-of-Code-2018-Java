public class InstructionMulr extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //stores into register C the result of multiplying register A and register B.
        s.AfterRegisterStates[C] =
                s.BeforeRegisterStates[A] * s.BeforeRegisterStates[B];
    }
}
