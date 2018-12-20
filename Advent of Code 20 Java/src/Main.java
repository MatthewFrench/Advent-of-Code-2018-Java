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
        //Convert into array of strings
        ArrayList<String> inputs = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            inputs.add(input.substring(i, i+1));
        }

        //Parse the input into branches
        Path path = new Path(inputs);

        System.out.println("Finished parsing path");

        Room startingRoom = new Room();
        //Execute the input into connected nodes
        path.executeRoom(startingRoom);

        System.out.println("Finished creating rooms");

        //Flood fill to find the furthest area
        int value = startingRoom.GetLargestRoomValue(new HashSet<>());
        System.out.println("Furthest room: " + value);
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
