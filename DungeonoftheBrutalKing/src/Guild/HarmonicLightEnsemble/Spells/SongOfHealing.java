
package Guild.HarmonicLightEnsemble.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;

public class SongOfHealing implements Spell {

    private static final String NAME = "Song of Healing";
    private static final String DESCRIPTION =
            "A restorative song that mends wounds and renews magical energy.";

    private static final Guild SPELL_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public boolean canCast(Charecter caster) {
        return caster != null
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(Charecter caster) {
        if (caster == null) return;
        if (!canCast(caster)) return;

        // Spend MP (never below 0 due to setter, but clamp anyway).
        int mpAfterCost = caster.getMagicPoints() - REQUIRED_MAGIC_POINTS;
        caster.setMagicPoints(Math.max(0, mpAfterCost));

        // Heal HP (cap at max HP, never negative).
        int healScaling = Math.max(caster.getWisdom(), caster.getIntelligence()) / 5;
        int healAmount = Math.max(1, 10 + healScaling);

        int currentHp = caster.getHitPoints();
        int maxHp = caster.getMaxHitPoints();
        int effectiveHeal = Math.max(0, Math.min(healAmount, maxHp - currentHp));
        caster.setHitPoints(currentHp + effectiveHeal);

        // Restore MP (cap at max MP, never negative).
        int mpRestore = Math.max(1, 4 + (caster.getWisdom() / 10));
        int maxMp = caster.getMaxMagicPoints();
        int restoredMp = Math.min(maxMp, caster.getMagicPoints() + mpRestore);
        caster.setMagicPoints(Math.max(0, restoredMp));
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(Charecter caster, Enemies target) { }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
