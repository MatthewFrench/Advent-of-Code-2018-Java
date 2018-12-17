import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        String state = "....." + Input.getState() + ".....";
        String pattern = Input.getPatterns();
        System.out.println("Part 1 Starting");

        String[] lines = pattern.split("\n");

        Pot currentPot = null;
        Pot startingPot = null;
        for (char c : state.toCharArray()) {
            Pot newPot = new Pot();
            if (currentPot == null) {
                startingPot = newPot;
                currentPot = newPot;
                newPot.Position = -5;
            } else {
                newPot.Position = currentPot.Position + 1;
                currentPot.RightPot = newPot;
                newPot.LeftPot = currentPot;
                currentPot = newPot;
            }
            if (c == '#') {
                newPot.HasPlant = true;
            }
        }

        List<Pattern> patterns = new ArrayList<>();
        for (String l : lines) {
            patterns.add(new Pattern(l));
        }

        Instant start = Instant.now();
        for (long i = 0; i < 50000000000L; i++) {
            Instant end = Instant.now();
            if (i % 100000 == 0) {
                System.out.println("Index: " + i + ", percent: " + (i / 50000000000L * 100));
                printPots(startingPot);
                start = Instant.now();

                //Count number of plants
                Pot currentPot2 = startingPot;
                int numberOfPlants = 0;
                while(currentPot2 != null) {
                    if (currentPot2.HasPlant) {
                        numberOfPlants += currentPot2.Position;
                    }
                    currentPot2 = currentPot2.RightPot;
                }

                System.out.println("Part 1 Answer: Number of plants: " + numberOfPlants);
            }

            currentPot = startingPot;
            while (currentPot != null) {
                for (Pattern p : patterns) {
                    p.SetPotOutput(currentPot);
                }

                currentPot = currentPot.RightPot;
            }
            //Update plant generations
            Pot genPot = startingPot;
            while (genPot != null) {
                genPot.HasPlant = genPot.NextGeneration;
                genPot.NextGeneration = false;
                genPot = genPot.RightPot;
            }
            //Move starting pot
            while (startingPot.LeftPot != null) {
                startingPot = startingPot.LeftPot;
            }
            //Trim dead space from left
            Pot trimPot = startingPot;
            boolean trimming = true;
            for (int e = 0; e < 5; e++) {
                trimPot = trimPot.RightPot;
                if (trimPot == null || trimPot.HasPlant) {
                    trimming = false;
                    break;
                }
            }
            while (trimming) {
                trimPot = trimPot.RightPot;
                if (trimPot == null || trimPot.HasPlant) {
                    break;
                }
                Pot oldStartingPot = startingPot;
                startingPot = oldStartingPot.RightPot;
                oldStartingPot.RightPot = null;
                startingPot.LeftPot = null;
            }
        }
        printPots(startingPot);

        //Count number of plants
        currentPot = startingPot;
        int numberOfPlants = 0;
        while(currentPot != null) {
            if (currentPot.HasPlant) {
                numberOfPlants += currentPot.Position;
            }
            currentPot = currentPot.RightPot;
        }

        System.out.println("Part 1 Answer: Number of plants: " + numberOfPlants);
    }

    public static void printPots(Pot startingPot) {
        String s = "";
        while (startingPot != null) {
            if (startingPot.HasPlant) {
                s += "#";
            } else {
                s += ".";
            }
            startingPot = startingPot.RightPot;
        }
        System.out.println(s);
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
