import java.util.ArrayList;
import java.util.regex.Pattern;

public class Input {

    public static ArrayList<Group> getImmunityArmy() {
        return getArmy(get().split("\n\n")[0]);
    }
    public static ArrayList<Group> getInfectionArmy() {
        return getArmy(get().split("\n\n")[1]);
    }

    public static ArrayList<Group> getArmy(String side) {
        ArrayList<Group> groups = new ArrayList<>();
        String[] lines = side.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            //Units
            int units = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            //Hit points
            line = line.substring(line.indexOf("with") + 5);
            int hitPoints = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            //Weakness/Immunities
            ArrayList<String> weaknesses = new ArrayList<>();
            ArrayList<String> immunities = new ArrayList<>();
            if (line.indexOf("(") != -1) {
                line = line.substring(line.indexOf("(")+1);
                String weaknessImmunityString = line.substring(0, line.indexOf(")"));
                String[] splitWeaknessImmunity = weaknessImmunityString.split(Pattern.quote("; "));
                for (String weaknessImmunity : splitWeaknessImmunity) {
                    String firstWord = weaknessImmunity.substring(0, weaknessImmunity.indexOf(" "));
                    String[] types = weaknessImmunity.substring(weaknessImmunity.indexOf(" to ") + 4).split(", ");
                    if (firstWord.equals("weak")) {
                        for (String t : types) {
                            weaknesses.add(t);
                        }
                    } else if (firstWord.equals("immune")) {
                        for (String t : types) {
                            immunities.add(t);
                        }
                    } else {
                        System.out.println("Unknown first work: " + firstWord);
                    }
                }

                line = line.substring(line.indexOf(")")+1);
            }

            //Damage Amount
            line = line.substring(line.indexOf("does") + 5);
            int damageAmount = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            line = line.substring(line.indexOf(" ")+1);
            //Damage type
            String damageType = line.substring(0, line.indexOf(" damage"));
            //Initiative
            int initiative = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));

            Group g = new Group();
            g.DamageType = damageType;
            g.HitPointsPerUnit = hitPoints;
            g.Immunities = immunities;
            g.Weaknesses = weaknesses;
            g.Initiative = initiative;
            g.NumberOfUnits = units;
            g.DamageAmount = damageAmount;
            groups.add(g);
        }

        return groups;
    }

    public static String get() {
        return "Immune System:\n" +
                "2086 units each with 11953 hit points with an attack that does 46 cold damage at initiative 13\n" +
                "329 units each with 3402 hit points (weak to bludgeoning) with an attack that does 90 slashing damage at initiative 1\n" +
                "414 units each with 7103 hit points (weak to bludgeoning; immune to radiation) with an attack that does 170 radiation damage at initiative 4\n" +
                "2205 units each with 7118 hit points (immune to cold; weak to fire) with an attack that does 26 radiation damage at initiative 18\n" +
                "234 units each with 9284 hit points (weak to slashing; immune to cold, fire) with an attack that does 287 radiation damage at initiative 12\n" +
                "6460 units each with 10804 hit points (weak to fire) with an attack that does 15 slashing damage at initiative 11\n" +
                "79 units each with 1935 hit points with an attack that does 244 radiation damage at initiative 8\n" +
                "919 units each with 2403 hit points (weak to fire) with an attack that does 22 slashing damage at initiative 2\n" +
                "172 units each with 1439 hit points (weak to slashing; immune to cold, fire) with an attack that does 69 slashing damage at initiative 3\n" +
                "1721 units each with 2792 hit points (weak to radiation, fire) with an attack that does 13 cold damage at initiative 16\n" +
                "\n" +
                "Infection:\n" +
                "1721 units each with 29925 hit points (weak to cold, radiation; immune to slashing) with an attack that does 34 radiation damage at initiative 5\n" +
                "6351 units each with 21460 hit points (weak to cold) with an attack that does 6 slashing damage at initiative 15\n" +
                "958 units each with 48155 hit points (weak to bludgeoning) with an attack that does 93 radiation damage at initiative 7\n" +
                "288 units each with 41029 hit points (immune to bludgeoning; weak to radiation) with an attack that does 279 cold damage at initiative 20\n" +
                "3310 units each with 38913 hit points with an attack that does 21 radiation damage at initiative 19\n" +
                "3886 units each with 16567 hit points (immune to bludgeoning, cold) with an attack that does 7 cold damage at initiative 9\n" +
                "39 units each with 7078 hit points with an attack that does 300 bludgeoning damage at initiative 14\n" +
                "241 units each with 40635 hit points (weak to cold) with an attack that does 304 fire damage at initiative 6\n" +
                "7990 units each with 7747 hit points (immune to fire) with an attack that does 1 radiation damage at initiative 10\n" +
                "80 units each with 30196 hit points (weak to fire) with an attack that does 702 bludgeoning damage at initiative 17";
        /*
        return "Immune System:\n" +
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2\n" +
                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3\n" +
                "\n" +
                "Infection:\n" +
                "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1\n" +
                "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4";
                */
    }
}