package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Status.StatusType;
import Spells.Spell;
import java.util.List;

public class UnyieldingSpirit implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final int RESILIENCE_DURATION = 3;

    private final String name = "Unyielding Spirit";
    private final String description = "A temporary surge of resilience that shrugs off pain and debuffs.";

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
        cast(caster);
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Unyielding Spirit.");
            return;
        }
        caster.applyStatusEffect(StatusType.RESILIENCE_STATUS, RESILIENCE_DURATION, 1, caster);
        System.out.println(caster.getName() + " is filled with Unyielding Spirit, becoming resilient for " + RESILIENCE_DURATION + " turns!");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Unyielding Spirit.");
            return;
        }
        if (target == null) return;
        target.applyStatusEffect(StatusType.RESILIENCE_STATUS, RESILIENCE_DURATION, 1, caster);
        System.out.println(caster.getName() + " grants Unyielding Spirit to " + target.getName() + ", making them resilient for " + RESILIENCE_DURATION + " turns!");
    }

    @Override
    public void cast() {
        System.out.println("Unyielding Spirit is cast, but there is no caster.");
    }

    @Override
    public void castWithStrength(Charecter caster, double strengthMultiplier) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Unyielding Spirit.");
            return;
        }
        int duration = (int) Math.round(RESILIENCE_DURATION * strengthMultiplier);
        caster.applyStatusEffect(StatusType.RESILIENCE_STATUS, duration, 1, caster);
        System.out.println(caster.getName() + " uses Unyielding Spirit with a strength multiplier of " + strengthMultiplier +
            ", becoming resilient for " + duration + " turns!");
    }
}
