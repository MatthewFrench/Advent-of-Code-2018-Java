public class InstructionSetr extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //copies the contents of register A into register C. (Input B is ignored.)
        s.AfterRegisterStates[C] = s.BeforeRegisterStates[A];
    }
}
