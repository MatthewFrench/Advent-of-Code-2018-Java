import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        part1();
    }

    public static void part1() {
        System.out.println("Part 1 Starting");

        ArrayList<Point> points = Input.getPoints();

        System.out.println("points: " + points.size());

        ArrayList<ArrayList<Point>> constellations = new ArrayList<>();
        for (Point p : points) {
            ArrayList<ArrayList<Point>> connectedConstellations = new ArrayList<>();
            for (ArrayList<Point> c : constellations) {
                boolean connected = false;
                for (Point p2 : c) {
                    if (p.DistanceToPoint(p2) <= 3) {
                        connected = true;
                        break;
                    }
                }
                if (connected) {
                    connectedConstellations.add(c);
                }
            }

            if (connectedConstellations.size() == 0) {
                ArrayList<Point> constellation = new ArrayList<>();
                constellation.add(p);
                constellations.add(constellation);
            } else {
                //Now remove and merge all constellations and the point
                constellations.removeAll(connectedConstellations);
                ArrayList<Point> newConstellation = new ArrayList<>();
                for (ArrayList<Point> c : connectedConstellations) {
                    for (Point p2 : c) {
                        newConstellation.add(p2);
                    }
                }
                newConstellation.add(p);
                constellations.add(newConstellation);
            }
        }

        System.out.println("Number of constellations: " + constellations.size());
    }

}
