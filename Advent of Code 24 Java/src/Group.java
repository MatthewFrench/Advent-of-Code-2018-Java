import java.util.ArrayList;

public class Group {
    int DamageAmount = 0;
    int NumberOfUnits = 0;
    int HitPointsPerUnit = 0;
    ArrayList<String> Weaknesses = new ArrayList<>();
    ArrayList<String> Immunities = new ArrayList<>();
    String DamageType = "";
    int Initiative = 0;

    //In Battle
    Group ChosenTarget = null;

    public int GetEffectivePower() {
        return NumberOfUnits * DamageAmount;
    }

    public int GetDamageDealtFrom(Group g) {
        if (Immunities.contains(g.DamageType)) {
            return 0;
        }
        int damage = g.GetEffectivePower();
        if (Weaknesses.contains(g.DamageType)) {
            damage *= 2;
        }
        return damage;
    }

    public int DealDamageFrom(Group g) {
        int damage = GetDamageDealtFrom(g);
        int killedUnits = (int)Math.floor(damage / HitPointsPerUnit);
        NumberOfUnits -= killedUnits;
        if (NumberOfUnits < 0) {
            NumberOfUnits = 0;
        }
        return killedUnits;
    }
}
