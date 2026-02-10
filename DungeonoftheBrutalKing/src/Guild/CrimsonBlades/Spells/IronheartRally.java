package Guild.CrimsonBlades.Spells;

import java.util.List; import DungeonoftheBrutalKing.Charecter; import SharedData.Guild; import Spells.Spell;

public class IronheartRally implements Spell {

private static final int REQUIRED_MAGIC_POINTS = 6;
private static final int HEAL_AMOUNT = 8;

private final String name = "Ironheart Rally";
private final String description = "A battle cry that restores courage and boosts allies’ morale.";

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
    for (Charecter ally : allCharacters) {
        if (ally != caster) {
            ally.restoreHitPoints(HEAL_AMOUNT);
            System.out.println(caster.getName() + " rallies " + ally.getName() +
                ", restoring " + HEAL_AMOUNT + " hit points!");
        }
    }
}

@Override
public void cast(Charecter caster) {
    System.out.println(caster.getName() + " shouts Ironheart Rally, but there are no allies to boost.");
}

@Override
public void cast() {
    System.out.println("Ironheart Rally is cast, but there is no caster.");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.restoreHitPoints(HEAL_AMOUNT);
    System.out.println(caster.getName() + " rallies " + target.getName() +
        ", restoring " + HEAL_AMOUNT + " hit points!");
}
}