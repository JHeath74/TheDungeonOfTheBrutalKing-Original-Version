package Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import java.util.List;

public class RendTheHeavens implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 12;
    private static final int SHOCKWAVE_DAMAGE = 18;
    private static final double DAMAGE_MULTIPLIER = 1.5;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

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
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Rend the Heavens.");
            return;
        }
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
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Rend the Heavens.");
            return;
        }
        System.out.println(caster.getName() + " prepares Rend the Heavens, but there is no target.");
    }

    @Override
    public void cast() {
        System.out.println("Rend the Heavens is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Rend the Heavens.");
            return;
        }
        if (target == null) return;
        target.reduceHitPoints(SHOCKWAVE_DAMAGE);
        System.out.println(caster.getName() + " unleashes Rend the Heavens on " + target.getName() +
            ", dealing " + SHOCKWAVE_DAMAGE + " damage!");
    }

    @Override
    public void castWithStrength(Charecter caster, double strengthMultiplier) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Rend the Heavens.");
            return;
        }
        int damage = (int) Math.round(caster.getStrength() * DAMAGE_MULTIPLIER * strengthMultiplier);
        System.out.println(caster.getName() + " uses Rend the Heavens with a strength multiplier of " + strengthMultiplier +
            ", dealing " + damage + " damage (no target specified).");
    }
}
