public class InstructionSeti extends Instruction {
    public void Run(Sample s, int A, int B, int C) {
        //stores value A into register C. (Input B is ignored.)
        s.AfterRegisterStates[C] = A;
    }
}
