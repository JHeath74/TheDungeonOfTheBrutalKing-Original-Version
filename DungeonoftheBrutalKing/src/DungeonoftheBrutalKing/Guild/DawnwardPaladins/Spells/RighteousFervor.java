
package Guild.DawnwardPaladins.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class RighteousFervor implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_DAMAGE_BONUS = 3;
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
        return "Righteous Fervor";
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

        int bonus = computeDamageBonus(caster.getIntelligence());
        int duration = computeDuration(caster.getIntelligence());

        target.applyStatusEffect(StatusType.DAMAGE_UP_STATUS, duration, bonus, null);
    }

    private int computeDamageBonus(int intelligence) {
        return BASE_DAMAGE_BONUS + Math.max(0, intelligence / 6);
    }

    private int computeDuration(int intelligence) {
        return BASE_DURATION + Math.max(0, intelligence / 12);
    }

    @Override
    public String getDescription() {
        return "Righteous Fervor: A Dawnward Paladins guild spell that empowers an ally's strikes, increasing their damage for a short duration. Scales with intelligence.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
