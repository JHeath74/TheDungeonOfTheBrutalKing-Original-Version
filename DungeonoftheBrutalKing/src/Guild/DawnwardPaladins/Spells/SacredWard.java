
package Guild.DawnwardPaladins.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class SacredWard implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 5;

    private static final int BASE_DEFENSE_BONUS = 3;
    private static final int BASE_DURATION = 3;

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
        return "Sacred Ward";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            cast(caster, caster);
        }
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (!canUseSpell(caster) || target == null) return;

        int defenseBonus = computeDefenseBonus(caster);
        int duration = computeDuration(caster);

        // Uses the same pattern as ManaInfusion: (type, duration, value, extra)
        target.applyStatusEffect(StatusType.DEFENSE_UP_STATUS, duration, defenseBonus, null);
    }

    private int computeDefenseBonus(Charecter caster) {
        int intelligence = caster.getIntelligence();
        return BASE_DEFENSE_BONUS + Math.max(0, intelligence / 6);
    }

    private int computeDuration(Charecter caster) {
        int intelligence = caster.getIntelligence();
        return BASE_DURATION + Math.max(0, intelligence / 12);
    }

    @Override
    public String getDescription() {
        return "Sacred Ward: A Dawnward Paladins guild spell that fortifies an ally's armor for a short duration. Scales with intelligence.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
