
package Guild.DawnwardPaladins.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class HolySmite implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_DAMAGE = 6;
    private static final double STR_SCALE = 1.1;
    private static final double MP_SCALE = 0.6;
    private static final double INT_SCALE = 0.3;

    private static final double SELF_HEAL_RATIO = 0.25;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == Guild.DAWNWARD_PALADINS;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.DAWNWARD_PALADINS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() {
        return "Holy Smite";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (!canUseSpell(caster) || target == null) return;

        int damage = computeDamage(caster);
        target.takeDamage(damage);

        int selfHeal = (int) Math.ceil(damage * SELF_HEAL_RATIO);
        caster.setHitPoints(Math.min(caster.getMaxHitPoints(), caster.getHitPoints() + selfHeal));
    }

    private int computeDamage(Charecter caster) {
        double scaled =
                BASE_DAMAGE
                        + (caster.getStrength() * STR_SCALE)
                        + (caster.getMagicPoints() * MP_SCALE)
                        + (caster.getIntelligence() * INT_SCALE);

        return Math.max(1, (int) Math.ceil(scaled));
    }

    @Override
    public String getDescription() {
        return "Holy Smite: A Dawnward Paladins guild spell that smites a target with holy power, dealing damage scaling with strength and magic points, and healing the caster for a portion of the damage.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
