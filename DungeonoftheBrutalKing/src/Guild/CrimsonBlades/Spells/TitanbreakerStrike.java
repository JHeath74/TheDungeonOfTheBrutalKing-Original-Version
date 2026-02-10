package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter; import SharedData.Guild; import Spells.Spell; import java.util.List;

public class TitanbreakerStrike implements Spell {

private static final int REQUIRED_MAGIC_POINTS = 15;
private static final int STRIKE_DAMAGE = 22;

private final String name = "Titanbreaker Strike";
private final String description = "A devastating overhead blow meant to shatter armor and resolve.";

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
            target.reduceHitPoints(STRIKE_DAMAGE);
            System.out.println(caster.getName() + " smashes " + target.getName() +
                " with Titanbreaker Strike, dealing " + STRIKE_DAMAGE + " damage and shattering their armor!");
        }
    }
}

@Override
public void cast(Charecter caster) {
    System.out.println(caster.getName() + " prepares Titanbreaker Strike, but there is no target.");
}

@Override
public void cast() {
    System.out.println("Titanbreaker Strike is cast, but there is no caster.");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.reduceHitPoints(STRIKE_DAMAGE);
    System.out.println(caster.getName() + " smashes " + target.getName() +
        " with Titanbreaker Strike, dealing " + STRIKE_DAMAGE + " damage and shattering their armor!");
}
}