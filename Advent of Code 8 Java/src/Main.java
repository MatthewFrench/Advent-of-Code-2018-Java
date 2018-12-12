import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        String originalInput = Input.get();
        System.out.println("Part 1 Starting");

        Node n = new Node();
        n.Generate(
                Arrays.stream(originalInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList()).toArray(new Integer[0])
        ,0);

        System.out.println("Part 1 Answer: Sum of metadata: " + n.SumOfMetadata());
    }

    public static void part2() {
        String originalInput = Input.get();
        System.out.println("Part 2 Starting");

        Node n = new Node();
        n.Generate(
                Arrays.stream(originalInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList()).toArray(new Integer[0])
                ,0);

        System.out.println("Part 2 Answer: Sum of metadata: " + n.SumOfMetadataByChildNodes());
    }
}
