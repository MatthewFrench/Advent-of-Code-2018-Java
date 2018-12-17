import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        //part1();
        part2();
    }

    /*
    public static void part1() throws Exception {
        System.out.println("Part 1 Starting");

        int numberOfRecipes = Input.get();
        ArrayList<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int numberOfElves = 2;
        int[] elfRecipeIndexArray = new int[numberOfElves];
        elfRecipeIndexArray[0] = 0;
        elfRecipeIndexArray[1] = 1;

        while (recipes.size() < numberOfRecipes + 10) {
            int score = 0;
            for (int index : elfRecipeIndexArray) {
                score += recipes.get(index);
            }
            String scoreText = score + "";
            for (int i = 0; i < scoreText.length(); i++) {
                String numText = scoreText.substring(i, i + 1);
                recipes.add(Integer.parseInt(numText));
            }
            for (int elfIndex = 0; elfIndex < numberOfElves; elfIndex++) {
                int index = elfRecipeIndexArray[elfIndex];
                int individualScore = recipes.get(index) + 1 + index;
                while (individualScore >= recipes.size()) {
                    individualScore -= recipes.size();
                }
                elfRecipeIndexArray[elfIndex] = individualScore;
            }
        }
        //Print 10
        String s = "";
        for (int i = numberOfRecipes - 5; i < numberOfRecipes; i++) {
            s += recipes.get(i);
        }
        System.out.println(s);

        System.out.println("Part 1 Answer: ");
    }
*/


    public static void part2() {
        System.out.println("Part 2");

        String sequenceString = Input.get() + "";
        int[] sequence = new int[sequenceString.length()];
        for (int i = 0; i < sequenceString.length(); i++) {
            String numText = sequenceString.substring(i, i + 1);
            sequence[i] = Integer.parseInt(numText);
        }
        ArrayList<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int numberOfElves = 2;
        int[] elfRecipeIndexArray = new int[numberOfElves];
        elfRecipeIndexArray[0] = 0;
        elfRecipeIndexArray[1] = 1;

        boolean found = false;
        while (!found) {
            int score = 0;
            for (int index : elfRecipeIndexArray) {
                score += recipes.get(index);
            }
            String scoreText = score + "";
            int add = 0;
            for (int i = 0; i < scoreText.length(); i++) {
                String numText = scoreText.substring(i, i + 1);
                recipes.add(Integer.parseInt(numText));
                add += 1;
            }
            for (int elfIndex = 0; elfIndex < numberOfElves; elfIndex++) {
                int index = elfRecipeIndexArray[elfIndex];
                int individualScore = recipes.get(index) + 1 + index;
                while (individualScore >= recipes.size()) {
                    individualScore -= recipes.size();
                }
                elfRecipeIndexArray[elfIndex] = individualScore;
            }
            if (recipes.size() - add >= sequence.length) {
                for (int subtract = add; subtract >= 0; subtract --) {
                    found = true;
                    for (int i = 0; i < sequence.length; i++) {
                        if (sequence[i] != recipes.get(recipes.size() - sequence.length + i - subtract)) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        System.out.println("Found at:  " + (recipes.size() - sequence.length - subtract));
                        break;
                    }
                }
            }
        }
        System.out.println("Number of recipes: " + recipes.size());
    }
}
