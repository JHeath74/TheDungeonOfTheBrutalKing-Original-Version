package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter; import SharedData.Guild; import Spells.Spell; import java.util.List;

public class UnyieldingSpirit implements Spell {

private static final int REQUIRED_MAGIC_POINTS = 7;
private static final int RESILIENCE_DURATION = 3; // Example: lasts 3 turns

private final String name = "Unyielding Spirit";
private final String description = "A temporary surge of resilience that shrugs off pain and debuffs.";

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
    cast(caster);
}


@Override
public void cast(Charecter caster) {
    caster.applyStatusEffect(StatusType.RESILIENCE_STATUS, RESILIENCE_DURATION, 1, caster);
    System.out.println(caster.getName() + " is filled with Unyielding Spirit, becoming resilient for " + RESILIENCE_DURATION + " turns!");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.applyStatusEffect(StatusType.RESILIENCE_STATUS, RESILIENCE_DURATION, 1, caster);
    System.out.println(caster.getName() + " grants Unyielding Spirit to " + target.getName() + ", making them resilient for " + RESILIENCE_DURATION + " turns!");
}

}