
// src/Guild/DawnwardPaladins/Spells/RadiantAegis.java
package Guild.DawnwardPaladins.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.StatusType;
import java.util.List;

public class RadiantAegis implements Spell {
    private static final int BASE_SHIELD = 10;
    private static final int REQUIRED_MAGIC_POINTS = 9;
    private static final int BASE_DURATION = 2;

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
        return "RadiantAegis";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            applyShield(target, caster.getWisdom());
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            applyShield(caster, caster.getWisdom());
        }
    }

    public void applyShield(Charecter target, int wisdom) {
        int shield = BASE_SHIELD + (wisdom / 3);
        int duration = BASE_DURATION + (wisdom / 12);
        target.applyStatusEffect(StatusType.DEFENSE_UP_STATUS, duration, shield, null);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            applyShield(target, caster.getWisdom());
        }
    }

    @Override
    public String getDescription() {
        return "Radiant Aegis: Grants a radiant shield that absorbs damage for several turns. Effectiveness scales with wisdom.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
