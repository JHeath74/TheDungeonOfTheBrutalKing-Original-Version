package Guild.DawnwardPaladins.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.LifeStealStatus;
import java.util.List;

public class SanctifiedLeech implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 10;
    private static final int BASE_DURATION = 2;
    private static final double LEECH_PERCENT = 0.10;

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
        return "SanctifiedLeech";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            Charecter target = allCharacters.get(0);
            applyLeech(target, caster.getWisdom());
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (canUseSpell(caster)) {
            applyLeech(caster, caster.getWisdom());
        }
    }

    public void applyLeech(Charecter target, int wisdom) {
        int duration = BASE_DURATION + (wisdom / 10);
        // Amount is 0; combat system should use LEECH_PERCENT when applying the effect
        LifeStealStatus status = new LifeStealStatus(0, duration);
        target.addStatus(status);
    }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (canUseSpell(caster) && target != null) {
            applyLeech(target, caster.getWisdom());
        }
    }

    @Override
    public String getDescription() {
        return "Sanctified Leech: For a few turns, when you attack, you heal for 10% of the enemy's current health.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double strength) { }
}
