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
        int originalInput = Input.get();
        System.out.println("Part 1 Starting");

        int width = 300;
        int height = 300;
        int[][] gridPowerLevels = new int[width][height];
        for (int x = 1; x < width-1; x++) {
            for (int y = 1; y < height-1; y++) {
                long rackID = x + 10;
                long powerLevel = rackID * y;
                powerLevel = powerLevel + originalInput;
                powerLevel *= rackID;
                String powerLevelString = powerLevel + "";
                String hundredsDigit = powerLevelString.substring(powerLevelString.length() - 3, powerLevelString.length() - 2);
                int newPowerLevel = Integer.parseInt(hundredsDigit) - 5;
                gridPowerLevels[x-1][y-1] = newPowerLevel;
            }
        }

        final AtomicInteger maxX = new AtomicInteger(0);
        final AtomicInteger maxY = new AtomicInteger(0);
        final AtomicInteger maxPowerLevel = new AtomicInteger(-10000);
        final AtomicInteger maxSize = new AtomicInteger(0);
        final Object lock1 = new Object();

        List<CompletableFuture> futures = new ArrayList<>();

        for (int s = 0; s < 300; s++) {
            final int size = s;
            futures.add(CompletableFuture.runAsync(() -> {
                System.out.println("Size: " + size);
                for (int x = 1; x < width-size; x++) {
                    for (int y = 1; y < height-size; y++) {
                        int combinedPowerLevel = 0;
                        for (int y2 = y; y2 < y + size; y2++) {
                            for (int x2 = x; x2 < x + size; x2++) {
                                combinedPowerLevel += gridPowerLevels[x2][y2];
                            }
                        }
                        synchronized (lock1)
                        {
                            if (combinedPowerLevel > maxPowerLevel.get()) {
                                maxPowerLevel.set(combinedPowerLevel);
                                maxX.set(x);
                                maxY.set(y);
                                maxSize.set(size);
                            }
                        }
                    }
                }
            }));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("Part 1 Answer: max X: " + (maxX.get() + 1) + ", y: " + (maxY.get() + 1) + ", max size: " + maxSize.get() + ", amount: " + maxPowerLevel.get());
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
