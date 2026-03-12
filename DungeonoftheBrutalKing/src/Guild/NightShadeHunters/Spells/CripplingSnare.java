
// `src/Guild/NightShadeHunters/Spells/CripplingSnare.java`
package Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class CripplingSnare implements Spell {

    private static final String NAME = "Crippling Snare";
    private static final String DESCRIPTION =
            "A hidden snare that bites into the target’s legs. Reduces agility for a short duration.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 8;
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private static final StatusType DEBUFF_STATUS = StatusType.LIFE_STEAL_STATUS;
    private static final int DEBUFF_DURATION_TURNS = 3;
    private static final int DEBUFF_VALUE = -3;

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
        target.applyStatusEffect(DEBUFF_STATUS, DEBUFF_DURATION_TURNS, DEBUFF_VALUE, caster);
    }

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
    public void cast() { }

    @Override
    public void cast(Charecter caster, Enemies target) {
        if (!canCast(caster)) return;
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
