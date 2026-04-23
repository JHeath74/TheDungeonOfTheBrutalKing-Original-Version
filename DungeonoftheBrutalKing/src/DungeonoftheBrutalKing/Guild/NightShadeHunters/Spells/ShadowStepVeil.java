package DungeonoftheBrutalKing.Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

public class ShadowStepVeil implements Spell {

    private static final String NAME = "Shadow Step Veil";
    private static final String DESCRIPTION =
            "A swift, silent reposition. Briefly increases evasion to help avoid incoming attacks.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 8;
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private static final StatusType SELF_STATUS = StatusType.EVASION_STATUS;
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

    private static boolean canCast(Character caster) {
        return caster != null
                && caster.getGuild() == SPELL_GUILD
                && caster.getAgility() >= REQUIRED_AGILITY
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    private static void spendMp(Character caster) {
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MAGIC_POINTS));
    }

    @Override
    public void cast(Character caster) {
        if (!canCast(caster)) return;

        spendMp(caster);
        caster.applyStatusEffect(SELF_STATUS, STATUS_DURATION_TURNS, STATUS_VALUE, caster);
    }

    @Override
    public void cast(Character caster, Character target) {
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
    public void cast(Character caster, List<Character> allCharacters) {
        cast(caster);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Character caster, Enemies target) {
        cast(caster);
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}