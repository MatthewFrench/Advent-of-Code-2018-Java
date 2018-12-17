import java.util.List;

public class Pattern {
    Boolean[] Pattern = new Boolean[5];
    boolean OutputsPlant = false;
    Pattern(String s) {
        //"####. => #"
        int index = 0;
        for (char c : s.substring(0, s.indexOf(" ")).toCharArray()) {
            if (c == '#') {
                Pattern[index] = true;
            } else {
                Pattern[index] = false;
            }
            index++;
        }
        OutputsPlant = s.substring(s.length() - 1, s.length()).equals("#");
    }

    void SetPotOutput(Pot pot) {
        Pot centerPot = pot;
        if (pot.LeftPot == null || pot.LeftPot.LeftPot == null
            || pot.RightPot == null || pot.RightPot.RightPot == null) {
            return;
        }
        pot = pot.LeftPot.LeftPot;
        boolean match = true;
        for (int i = 0; i < 5; i++) {
            if (pot.HasPlant != Pattern[i]) {
                match = false;
                break;
            }
            pot = pot.RightPot;
        }
        if (match) {
            centerPot.NextGeneration = OutputsPlant;
        }
        //For every pot that changes, make sure 5 empty pots exist to the left and right
        if (centerPot.NextGeneration) {
            Pot leftPot = centerPot;
            for (int i = 0; i < 5; i++) {
                if (leftPot.LeftPot == null) {
                    Pot newPot = new Pot();
                    newPot.RightPot = leftPot;
                    leftPot.LeftPot = newPot;
                    newPot.Position = leftPot.Position - 1;
                }
                leftPot = leftPot.LeftPot;
            }
            Pot rightPot = centerPot;
            for (int i = 0; i < 5; i++) {
                if (rightPot.RightPot == null) {
                    Pot newPot = new Pot();
                    newPot.LeftPot = rightPot;
                    rightPot.RightPot = newPot;
                    newPot.Position = rightPot.Position + 1;
                }
                rightPot = rightPot.RightPot;
            }
        }
    }
}