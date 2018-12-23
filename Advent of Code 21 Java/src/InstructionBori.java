public class InstructionBori extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //stores into register C the result of the bitwise OR of register A and value B.
        s.AfterRegisterStates[C] = s.BeforeRegisterStates[A] | B;
    }
}
