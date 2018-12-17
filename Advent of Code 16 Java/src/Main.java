import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");
        String[] lines = Input.get().split("\n");

        InstructionAddi instructionAddi = new InstructionAddi();
        InstructionAddr instructionAddr = new InstructionAddr();
        InstructionBani instructionBani = new InstructionBani();
        InstructionBanr instructionBanr = new InstructionBanr();
        InstructionBori instructionBori = new InstructionBori();
        InstructionBorr instructionBorr = new InstructionBorr();
        InstructionEqir instructionEqir = new InstructionEqir();
        InstructionEqri instructionEqri = new InstructionEqri();
        InstructionEqrr instructionEqrr = new InstructionEqrr();
        InstructionGtir instructionGtir = new InstructionGtir();
        InstructionGtri instructionGtri = new InstructionGtri();
        InstructionGtrr instructionGtrr = new InstructionGtrr();
        InstructionMuli instructionMuli = new InstructionMuli();
        InstructionMulr instructionMulr = new InstructionMulr();
        InstructionSeti instructionSeti = new InstructionSeti();
        InstructionSetr instructionSetr = new InstructionSetr();
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(instructionAddi);
        instructions.add(instructionAddr);
        instructions.add(instructionBani);
        instructions.add(instructionBanr);
        instructions.add(instructionBori);
        instructions.add(instructionBorr);
        instructions.add(instructionEqir);
        instructions.add(instructionEqri);
        instructions.add(instructionEqrr);
        instructions.add(instructionGtir);
        instructions.add(instructionGtri);
        instructions.add(instructionGtrr);
        instructions.add(instructionMuli);
        instructions.add(instructionMulr);
        instructions.add(instructionSeti);
        instructions.add(instructionSetr);

        // 4 registers, 0 - 3
        // instructions containing 1 of 16 opcodes
        // instruction consists of opcode, 2 inputs(A and B), and output C

        List<Sample> samples = Input.getSamples();
        int numberWith3OrMoreOpCodes = 0;
        for (Sample s : samples) {
            int count = 0;
            for (Instruction i : instructions) {
                if (i.Matches(s)) {
                    count++;
                }
            }
            if (count >= 3) {
                numberWith3OrMoreOpCodes += 1;
            }
        }

        System.out.println("Part 1 Answer: " + numberWith3OrMoreOpCodes);

        //Determine the opcodes
        //For every instruction
        //If that instruction has a sample that is not in any other
        //That is the opcode for that instruction
        boolean solving = true;
        while (solving) {
            solving = false;
            for (Instruction instruction : instructions) {
                if (instruction.OpCode == -1) {
                    for (Sample s : instruction.MatchingSamples) {
                        if (IsUniqueSample(instruction, s, instructions)) {
                            instruction.OpCode = s.OpCode;
                            break;
                        }
                    }
                    if (instruction.OpCode != -1) {
                        instruction.MatchingSamples.clear();
                        solving = true;
                    }
                }
            }
        }
        for (Instruction instruction : instructions) {
            System.out.println(instruction.GetName() + ": " + instruction.OpCode);
        }

        //Execute program
        ArrayList<ArrayList<Integer>> program = Input.getProgram();
        Sample s = new Sample();
        s.BeforeRegisterStates[0] = 0;
        s.BeforeRegisterStates[1] = 0;
        s.BeforeRegisterStates[2] = 0;
        s.BeforeRegisterStates[3] = 0;
        s.AfterRegisterStates[0] = 0;
        s.AfterRegisterStates[1] = 0;
        s.AfterRegisterStates[2] = 0;
        s.AfterRegisterStates[3] = 0;
        s.A = 0;
        s.B = 0;
        s.C = 0;
        for (ArrayList<Integer> execute : program) {
            Instruction instruction = GetInstruction(execute.get(0), instructions);
            s.A = execute.get(1);
            s.B = execute.get(2);
            s.C = execute.get(3);
            instruction.Run(s);
            s.BeforeRegisterStates[0] = s.AfterRegisterStates[0];
            s.BeforeRegisterStates[1] = s.AfterRegisterStates[1];
            s.BeforeRegisterStates[2] = s.AfterRegisterStates[2];
            s.BeforeRegisterStates[3] = s.AfterRegisterStates[3];

        }
        System.out.println("Value in register 0: " + s.AfterRegisterStates[0]);
    }

    public static Instruction GetInstruction(int opCode, ArrayList<Instruction> instructions) {
        for (Instruction i: instructions) {
            if (i.OpCode == opCode) {
                return i;
            }
        }
        return null;
    }

    public static boolean IsUniqueSample(Instruction originalInstruction, Sample sample, ArrayList<Instruction> instructions) {
        for (Instruction i : instructions) {
            if (i != originalInstruction) {
                if (i.MatchingSamples.contains(sample)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void part2() {
        /*
        String originalInput = Input.get();
        System.out.println("Part 2 Starting");

        Marble n = new Marble();
        n.Generate(
                Arrays.stream(originalInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList()).toArray(new Integer[0])
                ,0);

        System.out.println("Part 2 Answer: Sum of metadata: " + n.SumOfMetadataByChildNodes());
        */
    }
}
