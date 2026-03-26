
// `src/Guild/NightShadeHunters/Spells/DeadeyeFocus.java`
package Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class DeadeyeFocus implements Spell {

    private static final String NAME = "Deadeye Focus";
    private static final String DESCRIPTION =
            "Steadies the hunter's breath and gaze. Increases accuracy for a short time.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 7;
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private static final StatusType SELF_STATUS = StatusType.ACCURACY_STATUS;
    private static final int STATUS_DURATION_TURNS = 3;
    private static final int STATUS_VALUE = 3;

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
        if (!canCast(caster)) return;

        spendMp(caster);
        caster.applyStatusEffect(SELF_STATUS, STATUS_DURATION_TURNS, STATUS_VALUE, caster);
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        // Self-buff: ignore target and apply to caster.
        cast(caster);
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
        cast(caster);
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
