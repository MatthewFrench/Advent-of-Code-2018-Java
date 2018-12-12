import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part2() {
        String originalInput = Input.get();
        System.out.println("Starting");
        String[] splitInput = originalInput.split("\n");

        int lowestX = 0;
        int lowestY = 0;
        int highestX = 0;
        int highestY = 0;

        List<Part2Coordinate> coordinates = new ArrayList<>();
        for (String piece: splitInput) {
            int x = Integer.parseInt(piece.substring(0, piece.indexOf(",")));
            int y = Integer.parseInt(piece.substring(piece.indexOf(" ")+1));
            coordinates.add(new Part2Coordinate(x, y));

            if (coordinates.size() == 1) {
                lowestX = x - 1;
                lowestY = y - 1;
                highestX = x + 1;
                highestY = y + 1;
            } else {
                lowestX = Math.min(x - 1, lowestX);
                lowestY = Math.min(y - 1, lowestY);
                highestX = Math.max(x + 1, highestX);
                highestY = Math.max(y + 1, highestY);
            }
        }

        int regionsBelow10000 = 0;

        //Set the field
        for (int x = lowestX; x <= highestX; x++) {
            for (int y = lowestY; y <= highestY; y++) {
                int value = 0;
                for (Part2Coordinate coordinate : coordinates) {
                    value += Math.abs(coordinate.X - x) + Math.abs(coordinate.Y - y);
                }
                if (value < 10000) {
                    regionsBelow10000++;
                }
            }
        }

        System.out.println("Number of regions: " + regionsBelow10000);


    }

    public static void part1() {
        //Part 1
        String originalInput = Input.get();
        System.out.println("Starting");
        String[] splitInput = originalInput.split("\n");
        //132, 308

        // id, x, y
        ArrayList<GridSeed> seeds = new ArrayList<>();
        // id, area(-1 = infinite)
        HashMap<Integer, Integer> gridArea = new HashMap<>();

        int lowestX = 0;
        int lowestY = 0;
        int highestX = 0;
        int highestY = 0;

        //id of 0 is a stalemate tile
        int idGenerator = 1;

        for (String piece: splitInput) {
            int x = Integer.parseInt(piece.substring(0, piece.indexOf(",")));
            int y = Integer.parseInt(piece.substring(piece.indexOf(" ")+1));
            seeds.add(new GridSeed(idGenerator, x, y));
            idGenerator++;
            if (seeds.size() == 1) {
                lowestX = x - 1;
                lowestY = y - 1;
                highestX = x + 1;
                highestY = y + 1;
            } else {
                lowestX = Math.min(x - 1, lowestX);
                lowestY = Math.min(y - 1, lowestY);
                highestX = Math.max(x + 1, highestX);
                highestY = Math.max(y + 1, highestY);
            }
        }

        //X, Y, [ID, Step]
        HashMap<Integer, HashMap<Integer, GridPosition>> grid = new HashMap<>();

        //Set the field
        for (int x = lowestX; x <= highestX; x++) {
            if (!grid.containsKey(x)) {
                grid.put(x, new HashMap<>());
            }
            HashMap<Integer, GridPosition> xMap = grid.get(x);
            for (int y = lowestY; y <= highestY; y++) {
                if (!xMap.containsKey(y)) {
                    boolean isEdge = false;
                    //Set the edges as infinite
                    if (x == lowestX || x == highestX || y == lowestY || y == highestY) {
                        isEdge = true;
                    }
                    xMap.put(y, new GridPosition(0, x, y, isEdge));
                }
            }
        }

        ArrayList<GridPosition> growPositions = new ArrayList<>();

        //Place the seeds
        for (GridSeed seed : seeds) {
            grid.get(seed.X).get(seed.Y).IDs.add(seed.ID);
            grid.get(seed.X).get(seed.Y).Step = 1;
            gridArea.put(seed.ID, 1);
            GridPosition gridPosition = GetGridPosition(grid, seed.X, seed.Y);
            if (gridPosition != null) {
                growPositions.add(gridPosition);
            }
        }

        //Grow from the placed seeds and every step after, place in array
        Instant start = Instant.now();
        while (growPositions.size() > 0) {
            GridPosition gridPosition = growPositions.get(0);
            growPositions.remove(0);
            int currentStep = gridPosition.Step;
            int x = gridPosition.X;
            int y = gridPosition.Y;
            ProcessGridPosition(grid, growPositions, x - 1, y, currentStep + 1, gridPosition);
            ProcessGridPosition(grid, growPositions, x + 1, y, currentStep + 1, gridPosition);
            ProcessGridPosition(grid, growPositions, x, y + 1, currentStep + 1, gridPosition);
            ProcessGridPosition(grid, growPositions, x, y - 1, currentStep + 1, gridPosition);

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            if (timeElapsed >= 1) {
                DrawGrid(lowestX, highestX, lowestY, highestY, grid);
                start = Instant.now();
            }
        }

        //Calculate ID amounts
        HashMap<Integer, Integer> idCounts = new HashMap<>();
        HashMap<Integer, Boolean> idEdges = new HashMap<>();
        for (int x = lowestX; x <= highestX; x++) {
            HashMap<Integer, GridPosition> xMap = grid.get(x);
            for (int y = lowestY; y <= highestY; y++) {
                GridPosition position = xMap.get(y);
                if (position.IDs.size() == 1) {
                    int id = position.IDs.get(0);
                    if (!idCounts.containsKey(id)) {
                        idCounts.put(id, 0);
                    }
                    idCounts.put(id, idCounts.get(id) + 1);
                    if (position.IsEdge) {
                        idEdges.put(id, true);
                    }
                } else {
                    /*
                    if (position.IsEdge) {
                        for (int id : position.IDs) {
                            idEdges.put(id, true);
                        }
                    }
                    */
                }
            }
        }

        int largestNonInfiniteAmount = 0;
        for (Map.Entry<Integer, Integer> e : idCounts.entrySet()) {
            int id = e.getKey();
            int count = e.getValue();
            boolean isInfinite = idEdges.containsKey(id);
            System.out.println("ID: " + id + ", count: " + count + ", isInfinite: " + isInfinite);
            if (!isInfinite) {
                largestNonInfiniteAmount = Math.max(largestNonInfiniteAmount, count);
            }
        }
        System.out.println("Largest area: " + largestNonInfiniteAmount);
    }

    //Only spread to positions equal to 0 or greater than or equal to step
    public static void ProcessGridPosition(HashMap<Integer, HashMap<Integer, GridPosition>> grid, ArrayList<GridPosition> growPositions, int x, int y, int currentStep, GridPosition oldGrowPosition) {
        boolean hasChanged = false;
        GridPosition position = GetGridPosition(grid, x, y);
        if (position != null) {
            if (position.Step == currentStep) {
                //Add all ids
                for (int id : oldGrowPosition.IDs) {
                    if (!position.IDs.contains(id)) {
                        position.IDs.add(id);
                        hasChanged = true;
                    }
                }
            } else if (position.Step > currentStep || position.Step == 0) {
                position.IDs.clear();
                position.Step = currentStep;
                for (int id : oldGrowPosition.IDs) {
                    if (!position.IDs.contains(id)) {
                        position.IDs.add(id);
                        hasChanged = true;
                    }
                }
            }
        }
        if (hasChanged) {
            if (!growPositions.contains(position)) {
                growPositions.add(position);
            }
        }
    }

    //Return id and step
    public static GridPosition GetGridPosition(HashMap<Integer, HashMap<Integer, GridPosition>> grid, int x, int y) {
        if (grid.containsKey(x)) {
            HashMap<Integer, GridPosition> xMap = grid.get(x);
            if (xMap.containsKey(y)) {
                return xMap.get(y);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void DrawGrid(int lowestX, int highestX, int lowestY, int highestY, HashMap<Integer, HashMap<Integer, GridPosition>> grid) {
        for (int y = lowestY; y <= highestY; y++) {
            String line = "";
            for (int x = lowestX; x <= highestX; x++) {
                HashMap<Integer, GridPosition> xMap = grid.get(x);
                GridPosition fields = xMap.get(y);
                if (fields.IsEdge) {
                    line += "/";
                } else if (fields.IDs.size() == 0) {
                    line += " ";
                } else {
                    if (fields.IDs.size() > 1) {
                        line += ".";
                    } else {
                        line += fields.IDs.get(0);
                    }
                }
            }
            System.out.println(line);
        }
    }
}
