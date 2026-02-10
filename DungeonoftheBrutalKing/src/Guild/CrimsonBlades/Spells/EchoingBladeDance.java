package Guild.CrimsonBlades.Spells;
import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
public class EchoingBladeDance implements Spell {
private static final int REQUIRED_MAGIC_POINTS = 8;
private static final int SLASH_DAMAGE = 10;

private final String name = "Echoing Blade Dance";
private final String description = "Rapid slashes that leave shimmering after‐images, striking multiple foes.";

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
            target.reduceHitPoints(SLASH_DAMAGE);
            System.out.println(caster.getName() + " slashes " + target.getName() +
                " with Echoing Blade Dance, dealing " + SLASH_DAMAGE + " damage!");
        }
    }
}

@Override
public void cast(Charecter caster) {
    System.out.println(caster.getName() + " prepares Echoing Blade Dance, but there is no target.");
}

@Override
public void cast() {
    System.out.println("Echoing Blade Dance is cast, but there is no caster.");
}

@Override
public void cast(Charecter caster, Charecter target) {
    target.reduceHitPoints(SLASH_DAMAGE);
    System.out.println(caster.getName() + " slashes " + target.getName() +
        " with Echoing Blade Dance, dealing " + SLASH_DAMAGE + " damage!");
}

}