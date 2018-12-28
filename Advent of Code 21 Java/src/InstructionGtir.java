public class InstructionGtir extends Instruction {
    public void Run(final int[] beforeStates, final int[] afterStates, final int A, final int B, final int C) {
        //sets register C to 1 if value A is greater than register B.
        // Otherwise, register C is set to 0.
        afterStates[C] = A > beforeStates[B] ? 1 : 0;
    }
}
