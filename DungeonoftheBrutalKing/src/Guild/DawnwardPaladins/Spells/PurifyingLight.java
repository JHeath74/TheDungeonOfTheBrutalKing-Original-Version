package Guild.DawnwardPaladins.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusManager;

/**
 * PurifyingLight is a guild spell for the Dawnward Paladins.
 * When cast, it removes one negative status effect from the target.
 * Only Dawnward Paladins can use this spell.
 * - Removes a single negative status effect.
 * - Requires 6 magic points to cast.
 */
public class PurifyingLight implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == Guild.DAWNWARD_PALADINS;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.DAWNWARD_PALADINS;
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
    public String getName() {
        return "Purifying Light";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            removeNegativeStatus(target);
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            removeNegativeStatus(caster);
        }
    }

    // Uses StatusManager to remove the first negative status
    public void removeNegativeStatus(Charecter target) {
        if (target != null) {
            target.removeOneNegativeEffect();
        }
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            removeNegativeStatus(target);
        }
    }

    @Override
    public String getDescription() {
        return "Purifying Light: Removes one negative status effect from the target.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
