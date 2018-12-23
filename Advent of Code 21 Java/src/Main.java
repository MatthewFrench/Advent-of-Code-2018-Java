import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");
        String input = Input.get();
        String[] split = input.split("\n");

        InstructionAddi instructionAddi = new InstructionAddi();
        InstructionAddr instructionAddr = new InstructionAddr();
        InstructionEqrr instructionEqrr = new InstructionEqrr();
        InstructionGtrr instructionGtrr = new InstructionGtrr();
        InstructionMuli instructionMuli = new InstructionMuli();
        InstructionMulr instructionMulr = new InstructionMulr();
        InstructionSeti instructionSeti = new InstructionSeti();
        InstructionSetr instructionSetr = new InstructionSetr();
        InstructionBani instructionBani = new InstructionBani();
        InstructionEqri instructionEqri = new InstructionEqri();
        InstructionBori instructionBori = new InstructionBori();
        InstructionGtir instructionGtir = new InstructionGtir();
        HashMap<String, Instruction> instructions = new HashMap<>();
        instructions.put("addi", instructionAddi);
        instructions.put("addr", instructionAddr);
        instructions.put("eqrr", instructionEqrr);

        instructions.put("gtrr", instructionGtrr);
        instructions.put("muli", instructionMuli);

        instructions.put("mulr", instructionMulr);
        instructions.put("seti", instructionSeti);
        instructions.put("setr", instructionSetr);

        instructions.put("bani", instructionBani);
        instructions.put("eqri", instructionEqri);
        instructions.put("bori", instructionBori);
        instructions.put("gtir", instructionGtir);

        InstructionInput[] instructionInput = new InstructionInput[split.length - 1];
        boolean first = false;
        int index = 0;
        for (String line : split) {
            if (!first) {
                first = true;
                continue;
            }
            String[] splitLine = line.split(" ");
            Instruction instruction = instructions.get(splitLine[0]);
            int value1 = Integer.parseInt(splitLine[1]);
            int value2 = Integer.parseInt(splitLine[2]);
            int value3 = Integer.parseInt(splitLine[3]);
            InstructionInput i = new InstructionInput();
            i.Instruction = instruction;
            i.Value1 = value1;
            i.Value2 = value2;
            i.Value3 = value3;
            instructionInput[index] = i;
            index++;
        }

        int instructionPointer = Integer.parseInt(split[0].split(" ")[1]);

        boolean hasSuccessfulHalt = false;
        int minValue = 0;
        long maxLoops = 0;

        for (int i = 10721810; i < 10721810+1; i++) {
            Sample sample = new Sample();
            sample.BeforeRegisterStates[instructionPointer] = 0;
            sample.BeforeRegisterStates[0] = i;
            sample.AfterRegisterStates[0] = i;

            boolean executing = true;
            long loops = 0;
            Instant start = Instant.now();
            while (executing) {
                InstructionInput instructionInfo = instructionInput[sample.BeforeRegisterStates[instructionPointer]];
                loops++;

                Instruction instruction = instructionInfo.Instruction;
                instruction.Run(sample, instructionInfo.Value1, instructionInfo.Value2, instructionInfo.Value3);


                if (sample.BeforeRegisterStates[instructionPointer] == 28) {
                    if (sample.AfterRegisterStates[5] == 1) {
                        if (!hasSuccessfulHalt) {
                            System.out.println(i);
                            hasSuccessfulHalt = true;
                            minValue = i;
                            maxLoops = loops;
                        } else {
                            if (loops > maxLoops) {
                                System.out.println(i);
                                minValue = i;
                                maxLoops = loops;
                            } else {
                                System.out.println("Threw away minimum value of : " + i);
                            }
                        }
                    }
                    break;
                }

                sample.AfterRegisterStates[instructionPointer] += 1;
                sample.CopyAfterToBefore();
                if (sample.BeforeRegisterStates[instructionPointer] > split.length-2) {
                    executing = false;
                    if (!hasSuccessfulHalt) {
                        hasSuccessfulHalt = true;
                        minValue = i;
                        maxLoops = loops;
                    } else {
                        if (loops > maxLoops) {
                            minValue = i;
                            maxLoops = loops;
                        } else {
                            System.out.println("Threw away minimum value of : " + i);
                        }
                    }
                }
            }
        }

        System.out.println("Minimum value: " + minValue + " with loops: " + maxLoops);
    }

    public static void printOutput(long loop, Sample s) {
        System.out.println(loop + " [" +
                s.BeforeRegisterStates[0] +", " +
                s.BeforeRegisterStates[1] +", " +
                s.BeforeRegisterStates[2] +", " +
                s.BeforeRegisterStates[3] +", " +
                s.BeforeRegisterStates[4] +", " +
                s.BeforeRegisterStates[5] +"]");
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
