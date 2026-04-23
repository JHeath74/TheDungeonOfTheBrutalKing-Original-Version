
// `src/Guild/HarmonicLightEnsemble/Spells/ChantOfPurification.java`
package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class ChantOfPurification implements Spell {

    private static final String NAME = "Chant of Purification";
    private static final String DESCRIPTION =
            "A steady chant that purges a harmful status effect from yourself.";

    private static final Guild SPELL_GUILD = Guild.HARMONIC_LIGHT_ENSEMBLE;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 5;

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
                && caster.getWisdom() >= REQUIRED_WISDOM
                && caster.getMagicPoints() >= REQUIRED_MAGIC_POINTS;
    }

    private static void spendMp(Character caster) {
        caster.setMagicPoints(Math.max(0, caster.getMagicPoints() - REQUIRED_MAGIC_POINTS));
    }

    @Override
    public void cast(Character caster) {
        if (!canCast(caster)) return;

        spendMp(caster);

        caster.removeRandomNegativeStatus();
    }

    @Override
    public void cast(Character caster, Character target) { }

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
    public void cast(Character caster, Enemies target) { }

    @Override
    public void castWithStrength(Character enemy, double d) { }
}
