import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");
        String[] lines = Input.get().split("\n");
        int height = lines.length;
        int width = lines[0].length();
        Unit[][] unitGrid = new Unit[height][width];

        ArrayList<Unit> goblins = new ArrayList<>();
        ArrayList<Unit> elves = new ArrayList<>();

        int y = 0;
        int x = 0;
        for (String l : lines) {
            x = 0;
            for (char c : l.toCharArray()) {
                unitGrid[y][x] = null;
                if (c == '#' || c == 'E' || c == 'G') {
                    Unit unit = new Unit();
                    unit.Class = c;
                    unit.X = x;
                    unit.Y = y;
                    if (c == 'E') {
                        unit.AttackPower = 40;
                    }
                    unitGrid[y][x] = unit;
                    if (unit.Class == 'G') {
                        goblins.add(unit);
                    } else if (unit.Class == 'E') {
                        elves.add(unit);
                    }
                }
                x += 1;
            }
            y += 1;
        }

        int rounds = 0;
        attackLoop:
        while (goblins.size() > 0 && elves.size() > 0) {
            System.out.println("Round: " + rounds);
            PrintMap(unitGrid, width, height);
            ArrayList<Unit> unitArraylist = new ArrayList<>();
            unitArraylist.addAll(goblins);
            unitArraylist.addAll(elves);
            unitArraylist.sort(Comparator.comparingInt(unit->unit.Y * width + unit.X));
            for (int i = 0; i < unitArraylist.size(); i++) {
                Unit unit = unitArraylist.get(i);

                if (unit.Class == 'G' && elves.size() == 0 ||
                        unit.Class == 'E' && goblins.size() == 0) {
                    break attackLoop;
                }

                ArrayList<Unit> attackableUnits = GetAttackableUnits(unit, unitGrid, width, height);
                if (attackableUnits.size() == 0) {
                    //Move
                    Point point = GetMoveToPoint(unit, unitGrid, width, height, unit.Class=='G'?elves:goblins);
                    if (point != null) {
                        unitGrid[unit.Y][unit.X] = null;
                        unitGrid[point.Y][point.X] = unit;
                        unit.X = point.X;
                        unit.Y = point.Y;
                        attackableUnits = GetAttackableUnits(unit, unitGrid, width, height);
                    }
                }
                if (attackableUnits.size() > 0) {
                    Unit attackUnit = attackableUnits.get(0);
                    attackUnit.Health -= unit.AttackPower;
                    if (attackUnit.Health <= 0) {
                        if (attackUnit.Class == 'E') {
                            throw new Exception("Elf died");
                        }
                        if (unitArraylist.indexOf(attackUnit) < i) {
                            i -= 1;
                        }
                        unitArraylist.remove(attackUnit);
                        goblins.remove(attackUnit);
                        elves.remove(attackUnit);
                        unitGrid[attackUnit.Y][attackUnit.X] = null;
                    }
                }
            }
            rounds += 1;
        }
        PrintMap(unitGrid, width, height);
        System.out.println("Rounds: " + rounds);

        long hitPoints = 0;
        for (Unit u : goblins) {
            hitPoints += u.Health;
        }
        for (Unit u : elves) {
            hitPoints += u.Health;
        }
        long answer = rounds * hitPoints;


        System.out.println("Round: " + rounds);
        System.out.println("Health points:  " + hitPoints);
        System.out.println("Part 1 Answer: " + answer);
    }

    public static void PrintMap(Unit[][] unitGrid, int width, int height) {
        for (int y = 0; y < height; y++) {
            String s = "";
            for (int x = 0; x < width; x++) {
                Unit unit = unitGrid[y][x];
                if (unit == null) {
                    s += ".";
                } else {
                    s += unit.Class;
                }
            }
            System.out.println(s);
        }
        System.out.println("------");
    }

    public static void AddPoint(ArrayList<Point> points, Point point) {
        for (Point p : points) {
            if (p.X == point.X && p.Y == point.Y) {
                return;
            }
        }
        points.add(point);
    }

    public static Point GetMoveToPoint(Unit unit, Unit[][] unitGrid, int width, int height, ArrayList<Unit> enemies) {
        ArrayList<Point> inRangePoints = new ArrayList<>();
        for (Unit enemy : enemies) {
            if (IsValidMove(enemy.X, enemy.Y - 1, width, height) && unitGrid[enemy.Y-1][enemy.X] == null) {
                AddPoint(inRangePoints, new Point(enemy.X, enemy.Y - 1));
            }
            if (IsValidMove(enemy.X-1, enemy.Y, width, height) && unitGrid[enemy.Y][enemy.X-1] == null) {
                AddPoint(inRangePoints, new Point(enemy.X-1, enemy.Y));
            }
            if (IsValidMove(enemy.X+1, enemy.Y, width, height) && unitGrid[enemy.Y][enemy.X+1] == null) {
                AddPoint(inRangePoints, new Point(enemy.X+1, enemy.Y));
            }
            if (IsValidMove(enemy.X, enemy.Y + 1, width, height) && unitGrid[enemy.Y+1][enemy.X] == null) {
                AddPoint(inRangePoints, new Point(enemy.X, enemy.Y + 1));
            }
        }

        if (inRangePoints.size() == 0) {
            return null;
        }
        ArrayList<ShortestPath> reachablePoints = new ArrayList<>();
        for (Point p : inRangePoints) {
            ShortestPath path = ShortestPathToPoint(unit, unitGrid, width, height, p);
            if (path != null) {
                reachablePoints.add(path);
            }
        }
        if (reachablePoints.size() == 0) {
            return null;
        }
        int minPath = -1;
        for (ShortestPath p : reachablePoints) {
            if (minPath == -1) {
                minPath = p.Value;
            } else {
                minPath = Math.min(minPath, p.Value);
            }
        }
        ArrayList<ShortestPath> nearestPaths = new ArrayList<>();
        for (ShortestPath p : reachablePoints) {
            if (p.Value == minPath) {
                nearestPaths.add(p);
            }
        }
        nearestPaths.sort(Comparator.comparingInt(p->p.toPoint.Y*width + p.toPoint.X));
        ShortestPath chosenPath = nearestPaths.get(0);

        return chosenPath.fromPoint;
    }

    public static ShortestPath ShortestPathToPoint(Unit unit, Unit[][] unitGrid, int width, int height, Point point) {
        int[][] moveGrid = new int[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                moveGrid[y][x] = -1;
                Unit u = unitGrid[y][x];
                if (u != null && u != unit) {
                    moveGrid[y][x] = -2;
                }
                if (x == point.X && y == point.Y) {
                    moveGrid[y][x] = 0;
                }
            }
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (moveGrid[y][x] >= 0) {
                        int value = moveGrid[y][x];
                        if (MoveValueLesser(moveGrid, x-1, y, width, height, value)) {
                            moveGrid[y][x-1] = value + 1;
                            changed = true;
                        }
                        if (MoveValueLesser(moveGrid, x+1, y, width, height, value)) {
                            moveGrid[y][x+1] = value + 1;
                            changed = true;
                        }
                        if (MoveValueLesser(moveGrid, x, y-1, width, height, value)) {
                            moveGrid[y-1][x] = value + 1;
                            changed = true;
                        }
                        if (MoveValueLesser(moveGrid, x, y+1, width, height, value)) {
                            moveGrid[y+1][x] = value + 1;
                            changed = true;
                        }
                    }
                }
            }
        }

        if (moveGrid[unit.Y][unit.X] <= 0) {
            return null;
        }

        //From point is going to be the closest step towards the target
        ArrayList<Point> firstSteps = new ArrayList();
        int minStep = -1;
        if (IsValidMove(unit.X - 1, unit.Y, width, height) && moveGrid[unit.Y][unit.X - 1] >= 0) {
            int value = moveGrid[unit.Y][unit.X - 1];
            if (minStep == -1 || minStep >= value) {
                if (value < minStep) {
                    firstSteps.clear();
                }
                minStep = value;
                firstSteps.add(new Point(unit.X - 1, unit.Y));
            }
        }
        if (IsValidMove(unit.X + 1, unit.Y, width, height) && moveGrid[unit.Y][unit.X + 1] >= 0) {
            int value = moveGrid[unit.Y][unit.X + 1];
            if (minStep == -1 || minStep >= value) {
                if (value < minStep) {
                    firstSteps.clear();
                }
                minStep = value;
                firstSteps.add(new Point(unit.X + 1, unit.Y));
            }
        }
        if (IsValidMove(unit.X, unit.Y - 1, width, height) && moveGrid[unit.Y - 1][unit.X] >= 0) {
            int value = moveGrid[unit.Y - 1][unit.X];
            if (minStep == -1 || minStep >= value) {
                if (value < minStep) {
                    firstSteps.clear();
                }
                minStep = value;
                firstSteps.add(new Point(unit.X, unit.Y - 1));
            }
        }
        if (IsValidMove(unit.X, unit.Y + 1, width, height) && moveGrid[unit.Y + 1][unit.X] >= 0) {
            int value = moveGrid[unit.Y + 1][unit.X];
            if (minStep == -1 || minStep >= value) {
                if (value < minStep) {
                    firstSteps.clear();
                }
                minStep = value;
                firstSteps.add(new Point(unit.X, unit.Y + 1));
            }
        }
        firstSteps.sort(Comparator.comparingInt(p->p.Y*width + p.X));

        ShortestPath shortestPath = new ShortestPath();
        shortestPath.fromPoint = firstSteps.get(0);
        shortestPath.toPoint = point;
        shortestPath.Value = moveGrid[unit.Y][unit.X];

        return shortestPath;
    }

    public static boolean IsSmallestMove(int smallestMove, int[][] moveGrid, int x, int y, int width, int height) {
        if (IsValidMove(x, y, width, height)) {
            return moveGrid[y][x] == smallestMove;
        }
        return false;
    }

    public static int SetSmallestMove(int smallestMove, int[][] moveGrid, int x, int y, int width, int height) {
        if (IsValidMove(x, y, width, height) && moveGrid[y][x] > 0) {
            if (smallestMove == -1) {
                smallestMove = moveGrid[y][x];
            } else {
                smallestMove = Math.min(moveGrid[y][x], smallestMove);
            }
        }
        return smallestMove;
    }

    public static boolean IsValidMove(int x, int y, int width, int height) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        return true;
    }

    public static boolean MoveValueLesser(int[][] moveGrid, int x, int y, int width, int height, int value) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        if (moveGrid[y][x] == -1) {
            return true;
        }
        if (moveGrid[y][x] <= value + 1) {
            return false;
        }
        return true;
    }

    public static ArrayList<Unit> GetAttackableUnits(Unit currentUnit, Unit[][] unitGrid, int width, int height) {
        int x = currentUnit.X;
        int y = currentUnit.Y;
        ArrayList<Unit> attackableUnits = new ArrayList<>();
        Unit leftUnit = GetAttackableUnit(currentUnit, x - 1, y, unitGrid, width, height);
        if (leftUnit != null) {
            attackableUnits.add(leftUnit);
        }
        Unit rightUnit = GetAttackableUnit(currentUnit, x + 1, y, unitGrid, width, height);
        if (rightUnit != null) {
            attackableUnits.add(rightUnit);
        }
        Unit topUnit = GetAttackableUnit(currentUnit, x, y - 1, unitGrid, width, height);
        if (topUnit != null) {
            attackableUnits.add(topUnit);
        }
        Unit bottomUnit = GetAttackableUnit(currentUnit, x, y + 1, unitGrid, width, height);
        if (bottomUnit != null) {
            attackableUnits.add(bottomUnit);
        }
        attackableUnits.sort(Comparator.comparingInt(u->u.Health * width * height + u.Y * width + u.X));
        return attackableUnits;
    }

    public static Unit GetAttackableUnit(Unit currentUnit, int x, int y, Unit[][] unitGrid, int width, int height) {
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            return null;
        }
        Unit u = unitGrid[y][x];
        if (u == null || u.Class == currentUnit.Class || u.Class == '#') {
            u = null;
        }
        return u;
    }

    public static void print() {
        String s = "";
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
