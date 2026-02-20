package Guild.DawnwardPaladins.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;

public class ManaInfusion implements Spell {
    private static final int BASE_REGEN = 3;
    private static final int REQUIRED_MAGIC_POINTS = 7;
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
        return "Mana Infusion";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            applyManaRegen(target, caster.getIntelligence());
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            applyManaRegen(caster, caster.getIntelligence());
        }
    }

    public void applyManaRegen(Charecter target, int intelligence) {
        int regen = BASE_REGEN + (intelligence / 5);
        int duration = BASE_DURATION + (intelligence / 12);
        target.applyStatusEffect(StatusType.MANA_REGEN_STATUS, duration, regen, null);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            applyManaRegen(target, caster.getIntelligence());
        }
    }

    @Override
    public String getDescription() {
        return "Mana Infusion: Temporarily increases the target's magic points regeneration. Effectiveness scales with intelligence.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
