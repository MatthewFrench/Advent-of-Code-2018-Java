import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

enum Position
{
    Spawn, Sand, Clay, SittingWater, FlowingWater;
}

public class Main {
    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");
        List<Range> ranges = Input.getRanges();

        boolean hasMinimumY = false;
        int minY = 0;
        int maxY = 0;
        boolean hasMinimumX = false;
        int minX = 0;
        int maxX = 0;
        for (Range r : ranges) {
            if (!hasMinimumY) {
                hasMinimumY = true;
                minY = r.StartY;
                minY = Math.min(minY, r.EndY);
                maxY = r.StartY;
                maxY = Math.max(maxY, r.EndY);
            } else {
                minY = Math.min(minY, r.StartY);
                minY = Math.min(minY, r.EndY);
                maxY = Math.max(maxY, r.StartY);
                maxY = Math.max(maxY, r.EndY);
            }

            if (!hasMinimumX) {
                hasMinimumX = true;
                minX = r.StartX;
                minX = Math.min(minX, r.EndX);
                maxX = r.StartX;
                maxX = Math.max(maxX, r.EndX);
            } else {
                minX = Math.min(minX, r.StartX);
                minX = Math.min(minX, r.EndX);
                maxX = Math.max(maxX, r.StartX);
                maxX = Math.max(maxX, r.EndX);
            }
        }
        minX -= 1;
        maxX += 2;
        maxY += 1;
        int actualMinY = minY;
        minY = 0;
        int width = maxX - minX;
        int height = maxY - minY;
        Position[][] grid = new Position[width][height];
        boolean[][] flowGrid = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = Position.Sand;
            }
        }
        for (Range r : ranges) {
            for (int x = r.StartX; x <= r.EndX; x++) {
                for (int y = r.StartY; y <= r.EndY; y++) {
                    grid[getXCoord(minX, x)][getYCoord(minY, y)] = Position.Clay;
                }
            }
        }
        //Set spawn
        grid[getXCoord(minX, 500)][getYCoord(minY, 0)] = Position.Spawn;
        ArrayList<Point> spawnPoints = new ArrayList<>();
        spawnPoints.add(new Point(getXCoord(minX, 500), getYCoord(minY, 0)));

        ArrayList<Point> flowingWater = new ArrayList<>();
        //Do simulation
        int lastAmountOfWater = -1;
        int currentAmountOfWater = 0;
        Instant startTime = Instant.now();
        while (lastAmountOfWater != currentAmountOfWater) {
            lastAmountOfWater = currentAmountOfWater;
            ArrayList<Point> waterToMove = new ArrayList<>(flowingWater);
            //Simulate all flowing water
            boolean waterMoving = true;
            while (waterMoving) {
                //System.out.println("Water moving - Current amount of water: " + currentAmountOfWater);
                //printGrid(grid, width, height);
                waterMoving = false;
                //Sort by water that can move down first
                waterToMove.sort((w1, w2) -> {
                    boolean w1CanMoveDown = false;
                    if (w1.Y >= height - 1 || grid[w1.X][w1.Y+1] == Position.Sand) {
                        w1CanMoveDown = true;
                    }
                    boolean w2CanMoveDown = false;
                    if (w2.Y >= height - 1 || grid[w2.X][w2.Y+1] == Position.Sand) {
                        w2CanMoveDown = true;
                    }
                    if ((!w1CanMoveDown && !w2CanMoveDown)) {
                        //Prioritize moving to the side
                        boolean w1CanMoveSide = false;
                        if (w2.X > 0 && (grid[w1.X+1][w1.Y] == Position.Sand || grid[w1.X-1][w1.Y] == Position.Sand)) {
                            w1CanMoveSide = true;
                        }
                        boolean w2CanMoveSide = false;
                        if (w2.X > 0 && (grid[w2.X+1][w2.Y] == Position.Sand || grid[w2.X-1][w2.Y] == Position.Sand)) {
                            w2CanMoveSide = true;
                        }
                        if ((w1CanMoveSide && w2CanMoveSide) || (!w1CanMoveSide && !w2CanMoveSide)) {
                            return 0;
                        }
                        if (w1CanMoveSide) {
                            return -1;
                        }
                        return 1;
                    }
                    if (w1CanMoveDown && w2CanMoveDown) {
                        return 0;
                    }
                    //Water that falls is first
                    if (w1CanMoveDown) {
                        return -1;
                    }
                    return 1;
                });
                for (int i = 0; i < waterToMove.size(); i++) {
                    Point waterPoint = waterToMove.get(i);
                    if (waterPoint.Y == height - 1) {
                        //Throw water away
                        waterToMove.remove(waterPoint);
                        flowingWater.remove(waterPoint);
                        waterMoving = true;
                        grid[waterPoint.X][waterPoint.Y] = Position.Sand;
                        break;
                    } else {
                        //Move water down or to the side
                        if (grid[waterPoint.X][waterPoint.Y+1] == Position.Sand) {
                            grid[waterPoint.X][waterPoint.Y] = Position.Sand;
                            waterPoint.Y += 1;
                            grid[waterPoint.X][waterPoint.Y] = Position.FlowingWater;
                            waterToMove.remove(waterPoint);
                            waterMoving = true;
                            break;
                        } else if (grid[waterPoint.X][waterPoint.Y+1] != Position.FlowingWater) {
                            //Duplicate water when moving them to the sides
                            boolean movedWater = false;
                            if (grid[waterPoint.X+1][waterPoint.Y] == Position.Sand) {
                                //Create a new water point to side
                                flowingWater.add(new Point(waterPoint.X+1, waterPoint.Y));
                                grid[waterPoint.X+1][waterPoint.Y] = Position.FlowingWater;
                                movedWater = true;
                            }
                            if (grid[waterPoint.X-1][waterPoint.Y] == Position.Sand) {
                                //Create a new water point to side
                                flowingWater.add(new Point(waterPoint.X-1, waterPoint.Y));
                                grid[waterPoint.X-1][waterPoint.Y] = Position.FlowingWater;
                                movedWater = true;
                            }
                            if (movedWater) {
                                //Don't Throw water away as it split
                                //grid[waterPoint.X][waterPoint.Y] = Position.Sand;
                                waterToMove.remove(waterPoint);
                                //flowingWater.remove(waterPoint);
                                waterMoving = true;
                                break;
                            }
                        }
                    }
                }
                if (!waterMoving) {
                    //Detect sitting water, sitting water will be in a pool
                    //Should be trapped on bottom/left/right by clay/sitting water
                    ArrayList<Point> waterToSit = new ArrayList<>();
                    for (Point waterPoint : waterToMove) {
                        if (waterPoint.Y < height - 1) {
                            boolean trappedOnLeft = false;
                            boolean trappedOnRight = false;
                            for (int x = waterPoint.X; x > 0; x--) {
                                if (grid[x][waterPoint.Y+1] != Position.Clay &&
                                        grid[x][waterPoint.Y+1] != Position.SittingWater) {
                                    break;
                                }
                                if (grid[x][waterPoint.Y] != Position.FlowingWater &&
                                        grid[x][waterPoint.Y] != Position.SittingWater) {
                                    break;
                                }
                                if (grid[x-1][waterPoint.Y] == Position.Clay) {
                                    trappedOnLeft = true;
                                    break;
                                }
                            }
                            for (int x = waterPoint.X; x < width-1; x++) {
                                if (grid[x][waterPoint.Y+1] != Position.Clay &&
                                        grid[x][waterPoint.Y+1] != Position.SittingWater) {
                                    break;
                                }
                                if (grid[x][waterPoint.Y] != Position.FlowingWater &&
                                        grid[x][waterPoint.Y] != Position.SittingWater) {
                                    break;
                                }
                                if (grid[x+1][waterPoint.Y] == Position.Clay) {
                                    trappedOnRight = true;
                                    break;
                                }
                            }
                            if (trappedOnLeft && trappedOnRight) {
                                waterToSit.add(waterPoint);
                            }
                        }
                    }
                    for (Point waterPoint : waterToSit) {
                        flowingWater.remove(waterPoint);
                        waterToMove.remove(waterPoint);
                        grid[waterPoint.X][waterPoint.Y] = Position.SittingWater;
                        waterMoving = true;
                    }
                }
            }
            //Spawn water from spawn points
            for (Point spawnPoint : spawnPoints) {
                if (grid[spawnPoint.X][spawnPoint.Y+1] == Position.Sand) {
                    flowingWater.add(new Point(spawnPoint.X, spawnPoint.Y + 1));
                    grid[spawnPoint.X][spawnPoint.Y + 1] = Position.FlowingWater;
                }
            }
            currentAmountOfWater = GetAmountOfWater(grid, width, height, 0);
            System.out.println("Current amount of water: " + currentAmountOfWater);
            if (Duration.between(startTime, Instant.now()).toSeconds() >= 5) {
                printGrid(grid, width, height);
                startTime = Instant.now();
            }
        }

        printGrid(grid, width, height);
        System.out.println("Current amount of water: " + GetAmountOfWater(grid, width, height, actualMinY));
        System.out.println("Current amount of sitting water: " + GetAmountOfStillWater(grid, width, height, actualMinY));
    }

    public static int getXCoord(int minX, int x) {
        return x - minX;
    }
    public static int getYCoord(int minY, int y) {
        return y - minY;
    }

    public static int GetAmountOfWater(Position[][] grid, int width, int height, int startAtY) {
        int amountOfWater = 0;
        for (int y = startAtY; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == Position.FlowingWater || grid[x][y] == Position.SittingWater) {
                    amountOfWater += 1;
                }
            }
        }
        return amountOfWater;
    }

    public static int GetAmountOfStillWater(Position[][] grid, int width, int height, int startAtY) {
        int amountOfWater = 0;
        for (int y = startAtY; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == Position.SittingWater) {
                    amountOfWater += 1;
                }
            }
        }
        return amountOfWater;
    }

    public static void printGrid(Position[][] grid, int width, int height) {
        for (int y = 0; y < height; y++) {
            String s = "";
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == Position.Sand) {
                    s += ".";
                } else if (grid[x][y] == Position.Clay) {
                    s += "#";
                } else if (grid[x][y] == Position.Spawn) {
                    s += "+";
                } else if (grid[x][y] == Position.FlowingWater) {
                    s += "|";
                } else if (grid[x][y] == Position.SittingWater) {
                    s += "~";
                }
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
