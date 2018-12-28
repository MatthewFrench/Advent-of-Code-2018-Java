public class InstructionSeti extends Instruction {
    public void Run(final int[] beforeStates, final int[] afterStates, final int A, final int B, final int C) {
        //stores value A into register C. (Input B is ignored.)
        afterStates[C] = A;
    }
}
