
// src/Guild/AuroraArcanum/Spells/Starfall.java
package Guild.AuroraArcanum.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class Starfall implements Spell {
    private static final int BASE_DAMAGE = 18;
    private static final int REQUIRED_MAGIC_POINTS = 12;

    // Casts Starfall on a single target
    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster != null && target != null) {
            int radiantDamage = BASE_DAMAGE + (int)(caster.getIntelligence() * 1.3);
            int newHP = Math.max(0, target.getHitPoints() - radiantDamage);
            target.setHitPoints(newHP);
            // Optionally, add visual or log effect here
        }
    }

    // Casts Starfall on the first character in the list
    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    // Casts Starfall on the caster (self-target)
    @Override
    public void cast(Charecter caster) {
        cast(caster, caster);
    }

    // Not applicable: requires a caster and target
    @Override
    public void cast() {}

    // Not used for this spell
    @Override
    public void cast(int toonWisdom) {}

    @Override
    public void castWithIntelligence(int toonIntelligence) {}

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {}

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.AURORA_ARCANUM;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public String getName() {
        return "Starfall";
    }
}
