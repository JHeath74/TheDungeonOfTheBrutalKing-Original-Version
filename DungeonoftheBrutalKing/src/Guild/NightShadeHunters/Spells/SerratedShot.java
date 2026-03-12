
package Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class SerratedShot implements Spell {

    private static final String NAME = "Serrated Shot";
    private static final String DESCRIPTION =
            "A barbed projectile that tears on impact. Deals agility-scaled damage and inflicts Bleed.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 9;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_DAMAGE = 5;
    private static final int AGILITY_SCALING_DIVISOR = 2;

    private static final StatusType BLEED_STATUS = StatusType.BLEED_STATUS;
    private static final int BLEED_DURATION_TURNS = 3;
    private static final int BLEED_VALUE = 2;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    private static boolean canCast(Charecter caster) {
        return caster != null
                && caster.getGuild() == SPELL_GUILD
                && caster.getAgility() >= REQUIRED_AGILITY
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    private static void spendMp(Charecter caster) {
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MAGIC_POINTS));
    }

    private static int computeDamage(Charecter caster) {
        int scaled = caster.getAgility() / AGILITY_SCALING_DIVISOR;
        return Math.max(1, BASE_DAMAGE + scaled);
    }

    @Override
    public void cast(Charecter caster) {
        // Requires a target; no-op for self-only cast.
        if (!canCast(caster)) return;
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (!canCast(caster)) return;
        if (target == null) return;

        spendMp(caster);

        int dmg = computeDamage(caster);
        target.takeDamage(dmg);

        target.applyStatusEffect(BLEED_STATUS, BLEED_DURATION_TURNS, BLEED_VALUE, caster);
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // If your engine supports "selected target" elsewhere, keep this conservative.
        cast(caster);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Enemies target) {
        // Character-only spell; fall back to no-op instead of guessing enemy APIs.
        if (!canCast(caster)) return;
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
