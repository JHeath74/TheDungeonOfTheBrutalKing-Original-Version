package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter; import SharedData.Guild; import Spells.Spell; import java.util.List;

public class RendTheHeavens implements Spell {

private static final int REQUIRED_MAGIC_POINTS = 12;
private static final int SHOCKWAVE_DAMAGE = 18;

private final String name = "Rend the Heavens";
private final String description = "A powerful upward slash that sends a shockwave skyward.";

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
    // Deals damage to all except caster
    for (Charecter target : allCharacters) {
        if (target != caster) {
            target.reduceHitPoints(SHOCKWAVE_DAMAGE);
            System.out.println(caster.getName() + " unleashes Rend the Heavens on " + target.getName() +
                ", dealing " + SHOCKWAVE_DAMAGE + " damage!");
        }
    }
}

@Override
public void cast(Charecter caster) {
    System.out.println(caster.getName() + " prepares Rend the Heavens, but there is no target.");
}

@Override
public void cast() {
    System.out.println("Rend the Heavens is cast, but there is no caster.");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.reduceHitPoints(SHOCKWAVE_DAMAGE);
    System.out.println(caster.getName() + " unleashes Rend the Heavens on " + target.getName() +
        ", dealing " + SHOCKWAVE_DAMAGE + " damage!");
}
}