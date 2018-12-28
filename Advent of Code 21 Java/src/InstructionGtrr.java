public class InstructionGtrr extends Instruction {
    public void Run(final int[] beforeStates, final int[] afterStates, final int A, final int B, final int C) {
        //sets register C to 1 if register A is greater than register B.
        // Otherwise, register C is set to 0.
        afterStates[C] =
                beforeStates[A] > beforeStates[B] ? 1 : 0;
    }
}
