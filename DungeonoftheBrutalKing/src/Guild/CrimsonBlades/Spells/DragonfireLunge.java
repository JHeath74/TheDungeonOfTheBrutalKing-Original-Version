package Guild.CrimsonBlades.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class DragonfireLunge implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int FIRE_DAMAGE = 15;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

    private final String name = "Dragonfire Lunge";
    private final String description = "A fiery thrust attack, channeling inner fury into a burning strike.";

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
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Dragonfire Lunge.");
            return;
        }
        for (Charecter target : allCharacters) {
            if (target != caster) {
                applyFireDamage(caster, target);
            }
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Dragonfire Lunge.");
            return;
        }
        System.out.println(caster.getName() + " prepares a Dragonfire Lunge, but there is no target.");
    }

    @Override
    public void cast() {
        System.out.println("Dragonfire Lunge is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Dragonfire Lunge.");
            return;
        }
        applyFireDamage(caster, target);
    }

    private void applyFireDamage(Charecter caster, Charecter target) {
        if (target == null || caster == null) return;
        int strength = caster.getStrength();
        int fireDamage = (int) Math.round(strength * 1.5); // Example scaling
        target.reduceHitPoints(fireDamage);
        System.out.println(caster.getName() + " lunges at " + target.getName() +
            " with Dragonfire Lunge, dealing " + fireDamage + " fire damage!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // Not used for this spell
    }
}
