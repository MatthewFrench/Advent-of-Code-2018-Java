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
        int height = split.length;
        int width = split[0].length();
        char[][] beforeGrid = new char[width][height];
        char[][] afterGrid = new char[width][height];
        int y = 0;
        for (String line : split) {
            int x = 0;
            for (char c : line.toCharArray()) {
                beforeGrid[x][y] = c;
                x++;
            }
            y++;
        }
        HashMap<Integer, ArrayList<Integer>> resources = new HashMap<>();


        printGrid(beforeGrid, width, height);
        //Mutate each minute
        Instant start = Instant.now();
        int minutes = 50000;
        for (int i = 0; i < minutes; i++) {
            for (int x = 0; x < width; x++) {
                for (y = 0; y < height; y++) {
                    char currentChar = beforeGrid[x][y];
                    //Open to trees
                    int countTrees = 0;
                    int countlumber = 0;
                    for (int x2 = Math.max(0, x - 1); x2 <= Math.min(x + 1, width - 1); x2++) {
                        for (int y2 = Math.max(0, y - 1); y2 <= Math.min(y + 1, height - 1); y2++) {
                            if (x2 != x || y2 != y) {
                                char c = beforeGrid[x2][y2];
                                if (c == '|') {
                                    countTrees++;
                                } else if (c == '#') {
                                    countlumber++;
                                }
                            }
                        }
                    }
                    if (currentChar == '.') {
                        if (countTrees >= 3) {
                            afterGrid[x][y] = '|';
                        } else {
                            afterGrid[x][y] = currentChar;
                        }
                    } else if (currentChar == '|') {
                        //Trees to lumberyard
                        if (countlumber >= 3) {
                            afterGrid[x][y] = '#';
                        } else {
                            afterGrid[x][y] = currentChar;
                        }
                    } else if (currentChar == '#') {
                        //Lumberyard to lumberyard
                        if (countlumber > 0 && countTrees > 0) {
                            afterGrid[x][y] = '#';
                        } else {
                            afterGrid[x][y] = '.';
                        }
                    }
                }
            }

            /*
            Instant end = Instant.now();
            if (Duration.between(start, end).toSeconds() > 1) {
                printGrid(afterGrid, width, height);
                System.out.println("Minute: " + (i + 1) + " / " + minutes + ": " + (i * 100.0 / minutes) + "%");
                System.out.println("Current amount of resource: " + GetAmountOfResource(afterGrid, width, height));
                start = Instant.now();
            }
            */

            int amount = GetAmountOfResource(afterGrid, width, height);
            if (resources.containsKey(amount)) {
                resources.get(amount).add(i+1);
            } else {
                ArrayList<Integer> a = new ArrayList<>();
                a.add(i+1);
                resources.put(amount, a);
            }
            //printGrid(afterGrid, width, height);
            System.out.println("Minute: " + (i + 1) + " / " + minutes + ": " + (i * 100.0 / minutes) + "%");
            System.out.println("Current amount of resource: " + amount);
            System.out.println("Keys: " + resources.keySet().size());

            if (i != minutes-1) {
                char[][] swap = beforeGrid;
                beforeGrid = afterGrid;
                afterGrid = swap;
            }
        }


        printGrid(afterGrid, width, height);
        System.out.println("Current amount of resource: " + GetAmountOfResource(afterGrid, width, height));

        //Remove all the low level keys
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<Integer> startingNumbers = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : resources.entrySet()) {
            if (entry.getValue().size() >= 100) {
                keys.add(entry.getKey());
                startingNumbers.add(entry.getValue().get(entry.getValue().size()-1));
            }
        }

        int target = 1000000000;
        System.out.println("Worthy numbers: " + keys.size());
        for (int i = 0; i < keys.size(); i++) {
            int key = keys.get(i);
            System.out.println("Processing: " + key);
            int startingNumber = startingNumbers.get(i);
            while (startingNumber < target) {
                startingNumber += 28;
            }
            System.out.println("Got to: " + startingNumber);
            System.out.println(" off by: " + (startingNumber - target));
            if (startingNumber == target) {
                System.out.println("Answer is " + key);
                break;
            }
        }
    }

    public static int GetAmountOfResource(char[][] grid, int width, int height) {
        int lumberyard = 0;
        int tree = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == '#') {
                    lumberyard++;
                } else if (grid[x][y] == '|') {
                    tree++;
                }
            }
        }
        return lumberyard * tree;
    }

    public static void printGrid(char[][] grid, int width, int height) {
        for (int y = 0; y < height; y++) {
            String s = "";
            for (int x = 0; x < width; x++) {
                s += grid[x][y];
            }
            System.out.println(s);
        }
        System.out.println("-------------\n");
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
