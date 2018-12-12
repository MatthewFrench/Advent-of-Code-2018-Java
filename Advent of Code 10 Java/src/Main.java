import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        int originalInput = Input.get();
        System.out.println("Part 1 Starting");

        int numberOfPlayers = originalInput;
        LinkedList<Marble> availableMarbles = new LinkedList<>();
        for (int i = 0; i <= 70769 * 100; i++) { //100
            availableMarbles.add(new Marble(i));
        }

        ArrayList<Long> playerScores = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerScores.add(0L);
        }

        Marble marbleCircle = availableMarbles.pollFirst();
        marbleCircle.ClockwiseMarble = marbleCircle;
        marbleCircle.CounterClockwiseMarble = marbleCircle;

        int originalNumberOfMarbles = availableMarbles.size();

        int currentPlayer = 0;
        Instant startInstant = Instant.now();
        while (availableMarbles.size() != 0) {
            Instant endInstant = Instant.now();

            if (Duration.between(startInstant, endInstant).getSeconds() >= 1) {
                startInstant = Instant.now();
                int marblesLeft = availableMarbles.size();
                int totalMarbles = originalNumberOfMarbles;
                System.out.println("Marbles left: " + marblesLeft + ", total marbles: " + totalMarbles + ", percent: " + (marblesLeft / totalMarbles * 100.0));
            }

            //Get the first marble
            Marble marble = availableMarbles.pollFirst();

            if (marble.Value % 23 != 0) {
                Marble newMarblePlacementSpot = getPlacementSpot(marbleCircle);
                //Insert the marble in the new spot
                Marble previousMarble = newMarblePlacementSpot.CounterClockwiseMarble;
                Marble nextMarble = newMarblePlacementSpot;
                previousMarble.ClockwiseMarble = marble;
                marble.CounterClockwiseMarble = previousMarble;
                nextMarble.CounterClockwiseMarble = marble;
                marble.ClockwiseMarble = nextMarble;

                marbleCircle = marble;
            } else {
                int addToScore = marble.Value;
                Marble marbleToTake = getIndexOfMarble7SpotsCounterClockWise(marbleCircle);
                addToScore += marbleToTake.Value;
                //Remove marble
                Marble prevMarble = marbleToTake.CounterClockwiseMarble;
                Marble nextMarble = marbleToTake.ClockwiseMarble;
                prevMarble.ClockwiseMarble = nextMarble;
                nextMarble.CounterClockwiseMarble = prevMarble;
                playerScores.set(currentPlayer, playerScores.get(currentPlayer) + addToScore);

                marbleCircle = nextMarble;
            }

            //Next player
            currentPlayer++;
            if (currentPlayer > numberOfPlayers - 1) {
                currentPlayer = 0;
            }
        }

        long highestScore = 0;
        for (long score : playerScores) {
            highestScore = Math.max(highestScore, score);
        }

        System.out.println("Part 1 Answer: highest score: " + highestScore);
    }

    public static Marble getPlacementSpot(Marble marbleCircle) {
        return marbleCircle.ClockwiseMarble.ClockwiseMarble;
    }

    public static Marble getIndexOfMarble7SpotsCounterClockWise(Marble marbleCircle) {
        return marbleCircle.CounterClockwiseMarble.CounterClockwiseMarble.CounterClockwiseMarble.CounterClockwiseMarble.CounterClockwiseMarble.CounterClockwiseMarble.CounterClockwiseMarble;
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
