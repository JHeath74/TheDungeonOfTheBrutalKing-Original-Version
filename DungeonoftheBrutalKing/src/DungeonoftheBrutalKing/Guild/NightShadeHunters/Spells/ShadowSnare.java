package DungeonoftheBrutalKing.Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

public class ShadowSnare implements Spell {

    private static final String NAME = "Shadow Snare";
    private static final String DESCRIPTION =
            "A shadow-laced shot that snares the target's senses. Deals light damage and reduces accuracy for a short time.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 8;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final StatusType ON_HIT_STATUS = StatusType.ACCURACY_STATUS;
    private static final int STATUS_DURATION_TURNS = 3;
    private static final int STATUS_VALUE = -2;

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

    private static int computeDamage(Character caster) {
        int power = caster.getAgility();
        int base = 2;
        return Math.max(1, base + (power / 3));
    }

    @Override
    public void cast(Character caster) {
        if (!canCast(caster)) return;
        // No target provided; do nothing.
    }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;

        spendMp(caster);

        target.takeDamage(computeDamage(caster));
        target.applyStatusEffect(ON_HIT_STATUS, STATUS_DURATION_TURNS, STATUS_VALUE, caster);
    }

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
    public void cast(Character caster, Enemies target) {
        // Intentionally unused: this implementation attacks `Charecter` targets.
    }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}