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

        ArrayList<Nanobot> nanobots = new ArrayList<>();
        for (String s : split) {
            nanobots.add(new Nanobot(s));
        }

        Nanobot strongestNanobot = null;
        int strongestRadius = 0;
        for (Nanobot n : nanobots) {
            if (strongestNanobot == null || n.Radius > strongestRadius) {
                strongestNanobot = n;
                strongestRadius = n.Radius;
            }
        }

        System.out.println("Strongest nanobot index: " + nanobots.indexOf(strongestNanobot));
        System.out.println("Strongest nanobot radius: " + strongestRadius);

        int nanobotsInRange = 0;

        for (Nanobot n : nanobots) {
            if (Math.abs(n.X - strongestNanobot.X) + Math.abs(n.Y - strongestNanobot.Y) + Math.abs(n.Z - strongestNanobot.Z) <= strongestNanobot.Radius) {
                nanobotsInRange++;
            }
        }
        System.out.println("Nanobots in range: " + nanobotsInRange);
    }


    public static void part2() {
        System.out.println("Part 2 Starting");
        String input = Input.get();
        String[] split = input.split("\n");

        ArrayList<Nanobot> nanobots = new ArrayList<>();
        for (String s : split) {
            nanobots.add(new Nanobot(s));
        }

        int minX = nanobots.get(0).X;
        int maxX = nanobots.get(0).X;
        int minY = nanobots.get(0).Y;
        int maxY = nanobots.get(0).Y;
        int minZ = nanobots.get(0).Z;
        int maxZ = nanobots.get(0).Z;

        for (Nanobot n: nanobots) {
            minX = Math.min(minX, n.X);
            maxX = Math.max(maxX, n.X);
            minY = Math.min(minY, n.Y);
            maxY = Math.max(maxY, n.Y);
            minZ = Math.min(minZ, n.Z);
            maxZ = Math.max(maxZ, n.Z);
        }


        System.out.println("Width: " + (maxX - minX));
        System.out.println("Height: " + (maxY - minY));
        System.out.println("Depth: " + (maxZ - minZ));

        Point bestPoint = null;
        int numberOfNanobots = 0;

        int Reduction = 10000000;

        while (Reduction >= 1) {
            bestPoint = null;
            numberOfNanobots = 0;
            System.out.println("-------------");
            System.out.println("Reduction: " + Reduction);
            System.out.println("Width: " + (maxX - minX) / Reduction);
            System.out.println("Height: " + (maxY - minY) / Reduction);
            System.out.println("Depth: " + (maxZ - minZ) / Reduction);

            long loops = (((maxX - minX) / Reduction) * ((maxY - minY) / Reduction) * ((maxZ - minZ) / Reduction));
            System.out.println("Loops: " + loops);

            for (int x = minX; x < maxX; x += Reduction) {
                for (int y = minY; y < maxY; y += Reduction) {
                    for (int z = minZ; z < maxZ; z += Reduction) {
                        int amount = 0;
                        for (Nanobot n : nanobots) {
                            if (n.IsInRange(x, y, z)) {
                                amount++;
                            }
                        }
                        if (bestPoint == null || amount > numberOfNanobots) {
                            numberOfNanobots = amount;
                            bestPoint = new Point(x, y, z);
                        } else if (amount == numberOfNanobots) {
                            if (Math.abs(x) + Math.abs(y) + Math.abs(z) < Math.abs(bestPoint.X) + Math.abs(bestPoint.Y) + Math.abs(bestPoint.Z)) {
                                bestPoint = new Point(x, y, z);
                            }
                        }
                    }
                }
            }

            if (Reduction == 1) {
                break;
            }
            //Now set the new search area as point +/- 1 Reduction
            minX = bestPoint.X - Reduction*3;
            maxX = bestPoint.X + Reduction*3;
            minY = bestPoint.Y - Reduction*3;
            maxY = bestPoint.Y + Reduction*3;
            minZ = bestPoint.Z - Reduction*3;
            maxZ = bestPoint.Z + Reduction*3;
            Reduction = Reduction / 10;
        }

        System.out.println("Best point: " + bestPoint.X + ", " + bestPoint.Y + ", " + bestPoint.Z);
        System.out.println("Number of nanobots: " + numberOfNanobots);
        System.out.println("Distance from 0,0,0: " + (Math.abs(bestPoint.X) + Math.abs(bestPoint.Y) + Math.abs(bestPoint.Z)));
    }
}
