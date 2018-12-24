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

        int boostAmount = 0;
        boolean immunityWon = false;
        while (!immunityWon) {
            boostAmount++;

            ArrayList<Group> immuneArmy = Input.getImmunityArmy();
            ArrayList<Group> infectionArmy = Input.getInfectionArmy();
            for (Group g : immuneArmy) {
                g.DamageAmount += boostAmount;
            }

            while (immuneArmy.size() > 0 && infectionArmy.size() > 0) {
                //Target
                ArrayList<Group> sortedGroups = new ArrayList<>();
                sortedGroups.addAll(immuneArmy);
                sortedGroups.addAll(infectionArmy);
                sortedGroups.sort((g1, g2) -> {
                    if (g1.GetEffectivePower() > g2.GetEffectivePower()) {
                        return -1;
                    } else if (g1.GetEffectivePower() < g2.GetEffectivePower()) {
                        return 1;
                    } else {
                        if (g1.Initiative > g2.Initiative) {
                            return -1;
                        } else if (g1.Initiative < g2.Initiative) {
                            return 1;
                        }
                    }
                    return 0;
                });
                ArrayList<Group> immuneArmyLeft = new ArrayList<>();
                immuneArmyLeft.addAll(immuneArmy);
                ArrayList<Group> infectionArmyLeft = new ArrayList<>();
                infectionArmyLeft.addAll(infectionArmy);
                for (Group g : sortedGroups) {
                    g.ChosenTarget = null;
                    ArrayList<Group> enemyGroup = new ArrayList<>();
                    if (immuneArmy.contains(g)) {
                        enemyGroup = infectionArmyLeft;
                    } else {
                        enemyGroup = immuneArmyLeft;
                    }
                    Group enemyWithMostDamageAgainst = null;
                    int maxDamageAgainst = 0;
                    for (Group enemy : enemyGroup) {
                        int damage = enemy.GetDamageDealtFrom(g);
                        if (damage == 0) {
                            continue;
                        }
                        if (damage > maxDamageAgainst) {
                            enemyWithMostDamageAgainst = enemy;
                            maxDamageAgainst = damage;
                        } else if (damage == maxDamageAgainst) {
                            if (enemy.GetEffectivePower() > enemyWithMostDamageAgainst.GetEffectivePower()) {
                                enemyWithMostDamageAgainst = enemy;
                                maxDamageAgainst = damage;
                            } else if (enemy.GetEffectivePower() == enemyWithMostDamageAgainst.GetEffectivePower()) {
                                if (enemy.Initiative > enemyWithMostDamageAgainst.Initiative) {
                                    enemyWithMostDamageAgainst = enemy;
                                    maxDamageAgainst = damage;
                                }
                            }
                        }
                    }
                    if (enemyWithMostDamageAgainst != null) {
                        g.ChosenTarget = enemyWithMostDamageAgainst;
                        enemyGroup.remove(enemyWithMostDamageAgainst);
                    }
                }

                //Battle
                int unitsKilled = 0;
                ArrayList<Group> battleGroups = new ArrayList<>();
                battleGroups.addAll(immuneArmy);
                battleGroups.addAll(infectionArmy);
                battleGroups.sort(Comparator.comparingInt(g->-g.Initiative));
                for (Group g : battleGroups) {
                    if (g.NumberOfUnits <= 0 || g.ChosenTarget == null) {
                        continue;
                    }
                    Group chosenTarget = g.ChosenTarget;
                    if (chosenTarget.NumberOfUnits <= 0) {
                        continue;
                    }
                    unitsKilled += chosenTarget.DealDamageFrom(g);
                }
                immuneArmy.removeIf(g->g.NumberOfUnits <= 0);
                infectionArmy.removeIf(g->g.NumberOfUnits <= 0);
                if (unitsKilled == 0) {
                    break;
                }
            }
            ArrayList<Group> aliveGroups = new ArrayList<>();
            aliveGroups.addAll(immuneArmy);
            aliveGroups.addAll(infectionArmy);
            int unitsLeft = 0;
            for (Group g : aliveGroups) {
                unitsLeft += g.NumberOfUnits;
            }

            if (infectionArmy.size() > 0) {
                System.out.println("Infection won, units left: " + unitsLeft);
            } else {
                System.out.println("Immunity won, units left: " + unitsLeft);
                immunityWon = true;
            }
        }
        System.out.println("Immunity won with boost: " + boostAmount);
    }

}
