
package Guild.NightShadeHunters;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;

public class VoidFangBolt implements Spell {

    private static final String NAME = "Void Fang Bolt";
    private static final String DESCRIPTION =
            "A concentrated void-bolt fired with hunter precision. Deals heavy damage based on agility and may critically strike.";

    private static final Guild SPELL_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final int REQUIRED_AGILITY = 10;
    private static final int REQUIRED_MAGIC_POINTS = 8;

    private static final int BASE_DAMAGE = 4;
    private static final int AGILITY_SCALING_DIVISOR = 2;

    private static final int CRIT_CHANCE_PERCENT = 15;
    private static final double CRIT_MULTIPLIER = 1.75;

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

    private static boolean rollCrit() {
        return ThreadLocalRandom.current().nextInt(100) < CRIT_CHANCE_PERCENT;
    }

    private static int computeDamage(Charecter caster) {
        int scaled = caster.getAgility() / AGILITY_SCALING_DIVISOR;
        return Math.max(1, BASE_DAMAGE + scaled);
    }

    @Override
    public void cast(Charecter caster) {
        if (!canCast(caster)) return;
        // No target provided; do nothing.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (!canCast(caster)) return;

        spendMp(caster);

        int dmg = computeDamage(caster);
        if (rollCrit()) {
            dmg = (int) Math.max(1, Math.round(dmg * CRIT_MULTIPLIER));
        }

        target.takeDamage(dmg);
    }

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
    public void cast(Charecter caster, Enemies target) {
        // Intentionally unused: this implementation attacks `Charecter` targets.
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
