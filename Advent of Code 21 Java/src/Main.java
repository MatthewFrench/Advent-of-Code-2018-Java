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

        LinkedHashSet<Integer> uniqueValues = new LinkedHashSet<>();

        final int startNumber = 0;
        Sample sample = new Sample();
        sample.BeforeRegisterStates[instructionPointer] = 0;
        sample.BeforeRegisterStates[0] = startNumber;
        sample.AfterRegisterStates[0] = startNumber;

        int[] beforeRegisterStates = sample.BeforeRegisterStates;
        int[] afterRegisterStates = sample.AfterRegisterStates;

        long loops = 0;
        while (true) {
            final int instructionIndex = beforeRegisterStates[instructionPointer];
            final InstructionInput instructionInfo = instructionInput[instructionIndex];
            loops++;

            instructionInfo.Instruction.Run(beforeRegisterStates, afterRegisterStates, instructionInfo.Value1, instructionInfo.Value2, instructionInfo.Value3);

            if (instructionIndex == 28) {
                final int value = afterRegisterStates[3];
                if (uniqueValues.contains(value)) {
                    System.out.println("Contains duplicate value: " + value);
                    break;
                } else {
                    uniqueValues.add(value);
                }
            }

            afterRegisterStates[instructionPointer] += 1;
            sample.CopyAfterToBefore();
        }

        Integer[] valueArray = uniqueValues.toArray(new Integer[0]);
        System.out.println("Total loops: " + loops);
        System.out.println("Minimum value: " + valueArray[0]);
        System.out.println("Maximum value: " + valueArray[valueArray.length-1]);
    }

    public static void printOutput(long loop, Sample s) {
        System.out.println(loop + " [" +
                s.BeforeRegisterStates[0] + ", " +
                s.BeforeRegisterStates[1] + ", " +
                s.BeforeRegisterStates[2] + ", " +
                s.BeforeRegisterStates[3] + ", " +
                s.BeforeRegisterStates[4] + ", " +
                s.BeforeRegisterStates[5] + "]");
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
