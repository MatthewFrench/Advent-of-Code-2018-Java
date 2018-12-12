import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //Part 1
        String originalInput = Input.get();
        System.out.println("Starting");

        Map<Integer, Integer> numberMap = new HashMap<Integer, Integer>();

        String[] splitInput = originalInput.split("\n");

        for (String piece : splitInput) {
            System.out.println(piece);
            Map<Character, Integer> letterMap = new HashMap<Character, Integer>();
            for (char c: piece.toCharArray()) {
                if (!letterMap.containsKey(c)) {
                    letterMap.put(c, 1);
                } else {
                    letterMap.put(c, letterMap.get(c) + 1);
                }
            }

            Set<Integer> pieceNumberSet = new HashSet<Integer>();
            for (Map.Entry<Character, Integer> e : letterMap.entrySet()) {
                if (e.getValue() != 1  && e.getValue() < 4) {
                    System.out.println(e.getKey() + ":" + e.getValue());
                    if (!pieceNumberSet.contains(e.getValue())) {
                        pieceNumberSet.add(e.getValue());
                    }
                }
            }

            for (Integer number : pieceNumberSet) {
                System.out.println("Added " + number);
                if (!numberMap.containsKey(number)) {
                    numberMap.put(number, 1);
                } else {
                    numberMap.put(number, numberMap.get(number) + 1);
                }
            }
        }

        int amount = 1;
        for (Map.Entry<Integer, Integer> e : numberMap.entrySet()) {
            amount = amount * e.getValue();
        }

        System.out.println("Checksum: " + amount);

        //Part 2
        for (int index1 = 0; index1 < splitInput.length; index1++) {
            for (int index2 = index1 + 1; index2 < splitInput.length; index2++) {
                int diffCount = 0;
                char[] piece1Array = splitInput[index1].toCharArray();
                char[] piece2Array = splitInput[index2].toCharArray();
                String sameLetters = splitInput[index1] + "";
                for (int index3 = 0; index3 < piece1Array.length; index3++) {
                    if (piece1Array[index3] != piece2Array[index3]) {
                        diffCount++;
                    }
                }
                if (diffCount <= 1) {
                    System.out.println("Found " + splitInput[index1] + ", " + splitInput[index2] + " with diff " + diffCount);

                    String endResult = "";
                    for (int index3 = 0; index3 < piece1Array.length; index3++) {
                        if (piece1Array[index3] == piece2Array[index3]) {
                            endResult += piece1Array[index3];
                        }
                    }
                    System.out.println(endResult);
                }
            }
        }
    }
}
