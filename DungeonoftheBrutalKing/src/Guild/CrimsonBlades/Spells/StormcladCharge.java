package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter; import SharedData.Guild; import Spells.Spell; import java.util.List;

public class StormcladCharge implements Spell {

private static final int REQUIRED_MAGIC_POINTS = 9;
private static final int CHARGE_DAMAGE = 14;

private final String name = "Stormclad Charge";
private final String description = "A lightning‐fast rush that bowls through enemies.";

@Override
public String getName() {
    return name;
}

@Override
public String getDescription() {
    return description;
}

@Override
public boolean isGuildSpell() {
    return true;
}

@Override
public Guild getSpellGuild() {
    return Guild.CRIMSON_BLADES;
}

@Override
public int getRequiredMagicPoints() {
    return REQUIRED_MAGIC_POINTS;
}

@Override
public void cast(int toonWisdom) { }

@Override
public void castWithIntelligence(int toonIntelligence) { }

@Override
public void cast(int toonWisdom, int toonIntelligence) { }

@Override
public void cast(Charecter caster, List<Charecter> allCharacters) {
    for (Charecter target : allCharacters) {
        if (target != caster) {
            target.reduceHitPoints(CHARGE_DAMAGE);
            System.out.println(caster.getName() + " charges through " + target.getName() +
                " with Stormclad Charge, dealing " + CHARGE_DAMAGE + " damage!");
        }
    }
}

@Override
public void cast(Charecter caster) {
    System.out.println(caster.getName() + " prepares Stormclad Charge, but there is no target.");
}

@Override
public void cast() {
    System.out.println("Stormclad Charge is cast, but there is no caster.");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.reduceHitPoints(CHARGE_DAMAGE);
    System.out.println(caster.getName() + " charges through " + target.getName() +
        " with Stormclad Charge, dealing " + CHARGE_DAMAGE + " damage!");
}
}