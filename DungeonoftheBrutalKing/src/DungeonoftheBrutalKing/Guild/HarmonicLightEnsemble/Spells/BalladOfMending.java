
// `src/Guild/HarmonicLightEnsemble/Spells/BalladOfMending.java`
package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class BalladOfMending implements Spell {

    private static final String NAME = "Ballad of Mending";
    private static final String DESCRIPTION =
            "A gentle ballad that mends wounds and renews magical energy.";

    private static final Guild SPELL_GUILD = Guild.HARMONIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public boolean canCast(Character caster) {
        return caster != null
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(Character caster) {
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
    public void cast(Character caster, Enemies target) { }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Character caster, List<Character> allCharacters) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || target == null) return;
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}
