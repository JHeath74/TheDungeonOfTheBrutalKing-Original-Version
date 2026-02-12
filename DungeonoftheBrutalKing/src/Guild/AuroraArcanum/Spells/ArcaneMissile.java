
package Guild.AuroraArcanum.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class ArcaneMissile implements Spell {
    private static final int BASE_POWER = 10;
    private static final int REQUIRED_MAGIC_POINTS = 6;

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
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // This method is for direct damage calculation, but no target is provided
        // Example: could log or return damage if needed
        int damage = BASE_POWER + (toonIntelligence * 2);
        // No target to apply damage
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public String getName() {
        return "Arcane Missile";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Target the first enemy in the list (if any)
        if (allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            int damage = calculatePower(caster);
            target.reduceHitPoints(damage);
        }
    }

    @Override
    public void cast(Charecter caster) {
        // No target specified, so nothing happens
    }

    public int calculatePower(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return BASE_POWER + (intelligence * 2) + (level * 1);
    }

    @Override
    public void cast() {
        // No caster or target, so nothing happens
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster != null && target != null) {
            int damage = calculatePower(caster);
            target.reduceHitPoints(damage);
        }
    }

    @Override
    public String getDescription() {
        return "Arcane Missile: Launches a bolt of magical energy at a target, dealing damage based on the caster's intelligence and level.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) {
        // Not used for this spell
    }
}
