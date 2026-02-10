
package Guild.CrimsonBlades.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class DragonfireLunge implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int FIRE_DAMAGE = 15;

    private final String name = "Dragonfire Lunge";
    private final String description = "A fiery thrust attack, channeling inner fury into a burning strike.";

    @Override
    public String getName() {
        return name;
    }

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
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Example: Attack all enemies with fire damage
        for (Charecter target : allCharacters) {
            if (target != caster) {
                applyFireDamage(caster, target);
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        // No target specified
        System.out.println(caster.getName() + " prepares a Dragonfire Lunge, but there is no target.");
    }

    @Override
    public void cast() {
        System.out.println("Dragonfire Lunge is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        applyFireDamage(caster, target);
    }

    private void applyFireDamage(Charecter caster, Charecter target) {
        target.reduceHitPoints(FIRE_DAMAGE);
        System.out.println(caster.getName() + " lunges at " + target.getName() +
            " with Dragonfire Lunge, dealing " + FIRE_DAMAGE + " fire damage!");
    }
}
