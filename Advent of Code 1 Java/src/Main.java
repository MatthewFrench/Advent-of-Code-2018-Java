import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String originalInput = Input.get();
        System.out.println("Starting");

        Set<Integer> frequencySet = new HashSet<Integer>();

        int amount = 0;
        boolean found = false;

        String[] splitInput = originalInput.split("\n");
        while (!found) {
            System.out.println("Going");
            for (int index = 0; index < splitInput.length; index++) {
                String piece = splitInput[index];
                char sign = piece.charAt(0);
                int number = Integer.parseInt(piece.substring(1));
                if (sign == '-') {
                    amount -= number;
                } else if (sign == '+') {
                    amount += number;
                } else {
                    System.out.println("Uh oh " + piece);
                }

                if (frequencySet.contains(amount)) {
                    found = true;
                    break;
                }
                frequencySet.add(amount);
            }
        }

        System.out.println("Set length: " + frequencySet.size());
        System.out.println("Frequency: " + amount);
    }
}
