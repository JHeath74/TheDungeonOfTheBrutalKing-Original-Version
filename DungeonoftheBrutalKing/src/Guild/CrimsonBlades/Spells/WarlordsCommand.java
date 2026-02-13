
java
package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Status.StatusType;
import Spells.Spell;
import java.util.List;

public class WarlordsCommand implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int FALTER_DURATION = 1;

    private final String name = "Warlord’s Command";
    private final String description = "A forceful shout that compels enemies to falter or reposition.";

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.CRIMSON_BLADES; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Warlord’s Command.");
            return;
        }
        for (Charecter target : allCharacters) {
            if (target != caster) {
                target.applyStatusEffect(StatusType.FEAR_STATUS, FALTER_DURATION, 1, caster);
                System.out.println(caster.getName() + " commands " + target.getName() + " to falter!");
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Warlord’s Command.");
            return;
        }
        System.out.println(caster.getName() + " shouts Warlord’s Command, but there are no enemies to affect.");
    }

    @Override
    public void cast() {
        System.out.println("Warlord’s Command is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Warlord’s Command.");
            return;
        }
        if (target == null) return;
        target.applyStatusEffect(StatusType.FEAR_STATUS, FALTER_DURATION, 1, caster);
        System.out.println(caster.getName() + " commands " + target.getName() + " to falter!");
    }

    @Override
    public void castWithStrength(Charecter caster, double strengthMultiplier) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Warlord’s Command.");
            return;
        }
        int duration = (int) Math.round(FALTER_DURATION * strengthMultiplier);
        System.out.println(caster.getName() + " uses Warlord’s Command with a strength multiplier of " + strengthMultiplier +
            ", causing enemies to falter for " + duration + " turns (no targets specified).");
    }
}
