import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
        //Path path = new Path(inputs);
        Path path = new Path(inputs, null);

        AtomicLong completedPathCount = new AtomicLong(0);

        System.out.println("Finished parsing path");
        System.out.println("Number of paths: " + NumberFormat.getInstance().format(path.GetNumberOfPaths()));

        Room startingRoom = new Room();
        path.executeRoom(startingRoom, completedPathCount);

        System.out.println("End Finished paths: " + completedPathCount.get());

        System.out.println("Finished building rooms");

        //Room startingRoom = new Room();
        //Execute the input into connected nodes
        //path.executeRoom(startingRoom);

        //System.out.println("Finished creating rooms");

        //Flood fill to find the furthest area
        int value = startingRoom.GetLargestRoomValue(new HashSet<>());
        System.out.println("Furthest room: " + value);

        System.out.println("Number of rooms >= 1000: " + startingRoom.GetNumberOfRooms(new HashSet<>(), 0, 1000));
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
