import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        System.out.println("Part 1 Starting");

        BigInteger minStepArea = new BigInteger("-1");
        long minStepNumber = -1;
        List<Point> minStepPoints = new ArrayList<>();

        for (int step = 0; step < 100000; step++) {
        //int step = 5927;
            List<Point> points = Input.getPoints();
            int minX = 0;
            int maxX = 0;
            int minY = 0;
            int maxY = 0;
            for (Point p : points) {
                p.X = p.VelX * step + p.X;
                p.Y = p.VelY * step + p.Y;
                minX = Math.min(p.X, minX);
                maxX = Math.max(p.X, maxX);
                minY = Math.min(p.Y, minY);
                maxY = Math.max(p.Y, maxY);
            }

            BigInteger area = new BigInteger("1");
            area = area.multiply(new BigInteger(Math.abs(maxX - minX) + ""));
            area = area.multiply(new BigInteger(Math.abs(maxY - minY) + ""));
            if (minStepNumber == -1) {
                minStepArea = area;
                minStepNumber = step;
                minStepPoints = points;
            } else if (minStepArea.compareTo(area) > 0) {
                minStepArea = area;
                minStepNumber = step;
                minStepPoints = points;
            }

        }

        System.out.println("Step: " + minStepNumber + " with area: " + minStepArea.toString());

        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        for (Point p : minStepPoints) {
            minX = Math.min(p.X, minX);
            maxX = Math.max(p.X, maxX);
            minY = Math.min(p.Y, minY);
            maxY = Math.max(p.Y, maxY);
        }

            System.out.println("Min X: " + minX);
            System.out.println("Max X: " + maxX);
            System.out.println("Min Y: " + minY);
            System.out.println("Max Y: " + maxY);
            //Draw
            for (int y = minY; y <= maxY; y++) {
                String lineOutput = "";
                for (int x = minX; x <= maxX; x++) {
                    final int x1 = x;
                    final int y1 = y;
                    if (minStepPoints.stream().anyMatch(point->point.X == x1 && point.Y == y1)) {
                        lineOutput += "#";
                    } else {
                        lineOutput += " ";
                    }
                }
                System.out.println(lineOutput);
            }

            System.out.println("--------");


        System.out.println("Part 1 Answer: highest score: ");
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


    /*
    Instant startInstant = Instant.now();

            Instant endInstant = Instant.now();
            if (Duration.between(startInstant, endInstant).getSeconds() >= 1) {
                startInstant = Instant.now();
                }
     */
}
