
package Guild.DirgeweaversChorus.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildMembershipStatus;
import SharedData.GuildType;
import Spells.Spell;

public class HealingChant implements Spell {

    private static final int MAGIC_POINTS_COST = 10;
    private static final int HEAL_AMOUNT = 25;

    public HealingChant() {
        // No-op: implements Spell.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;

        // Must belong to Dirgeweavers guild to use this spell.
        if (caster.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) return;
        if (caster.getCurrentGuild() != GuildType.BARD) return;

        if (caster.getMagicPoints() < MAGIC_POINTS_COST) return;
        caster.setMagicPoints(caster.getMagicPoints() - MAGIC_POINTS_COST);

        int currentHp = target.getHitPoints();
        int maxHp = target.getMaxHitPoints();
        int newHp = Math.min(maxHp, currentHp + HEAL_AMOUNT);
        target.setHitPoints(newHp);
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.DIRGEWEAVERS_CHORUS;
    }

    @Override
    public int getRequiredMagicPoints() {
        return MAGIC_POINTS_COST;
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() {
        return "Healing Chant";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "Heals the target for a fixed amount, up to their maximum health.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
