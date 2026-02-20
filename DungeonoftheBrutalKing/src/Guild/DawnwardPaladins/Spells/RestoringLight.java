
java
package Guild.DawnwardPaladins.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import java.util.List;

public class RestoringLight implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == Guild.DAWNWARD_PALADINS;
    }

    private int calculateHealAmount(Charecter caster) {
        int level = caster.getLevel();
        int wisdom = caster.getWisdom();
        return Math.max(1, (level * wisdom) / 2);
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
    public String getName() {
        return "Restoring Light";
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            int healAmount = calculateHealAmount(caster);
            int currentHP = caster.getHitPoints();
            int maxHP = caster.getMaxHitPoints();
            int actualHeal = Math.min(healAmount, maxHP - currentHP);
            if (actualHeal > 0) {
                caster.restoreHitPoints(actualHeal);
            }
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        int amount = calculateHealAmount(caster);

        if (target == caster && canUseSpell(caster)) {
            // Self-heal
            int currentHP = caster.getHitPoints();
            int maxHP = caster.getMaxHitPoints();
            int actualHeal = Math.min(amount, maxHP - currentHP);
            if (actualHeal > 0) {
                caster.restoreHitPoints(actualHeal);
            }
        } else if (target.isUndead()) {
            // Damage undead
            target.takeDamage(amount);
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "Restoring Light: Heals the caster or damages undead for an amount based on level and wisdom.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
