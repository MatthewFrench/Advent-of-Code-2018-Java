public class InstructionSetr extends Instruction {
    public void Run(final int[] beforeStates, final int[] afterStates, final int A, final int B, final int C) {
        //copies the contents of register A into register C. (Input B is ignored.)
        afterStates[C] = beforeStates[A];
    }
}
