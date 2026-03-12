
// `src/Guild/NightShadeHunters/Spells/FieldDressing.java`
package Guild.NightShadeHunters.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;

public class FieldDressing implements Spell {

    private static final String NAME = "Field Dressing";
    private static final String DESCRIPTION =
            "A quick bind-and-brew remedy. Restores health to an ally (or yourself) based on agility.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 7;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_HEAL = 4;
    private static final int AGILITY_SCALING_DIVISOR = 2;

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

    private static int computeHeal(Charecter caster) {
        int scaled = caster.getAgility() / AGILITY_SCALING_DIVISOR;
        return Math.max(1, BASE_HEAL + scaled);
    }

    private static void applyHeal(Charecter target, int amount) {
        // Uses damage API with negative values, matching common patterns in simple RPG codebases.
        // If your project has a `heal(int)` method, replace this with `target.heal(amount)`.
        target.takeDamage(-Math.max(1, amount));
    }

    @Override
    public void cast(Charecter caster) {
        if (!canCast(caster)) return;

        spendMp(caster);
        applyHeal(caster, computeHeal(caster));
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (!canCast(caster)) return;

        Charecter healTarget = (target != null) ? target : caster;

        spendMp(caster);
        applyHeal(healTarget, computeHeal(caster));
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
        // Not applicable: heals `Charecter` targets.
        cast(caster);
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
