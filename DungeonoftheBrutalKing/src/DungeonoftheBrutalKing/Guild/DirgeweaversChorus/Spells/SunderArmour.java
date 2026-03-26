
// `src/Guild/DirgeweaversChorus/Spells/SunderArmourSpell.java`

package Guild.DirgeweaversChorus.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.ReduceDefenseStatus;
import Status.StatusManager;

public class SunderArmour implements Spell {

    private static final int MAGIC_POINTS_COST = 8;
    private static final int REDUCE_DEFENSE_DURATION = 3;

    public SunderArmour() {
        // No-op: implements Spell, so no super(name, cost) constructor exists.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;

        if (caster.getMagicPoints() < MAGIC_POINTS_COST) return;
        caster.setMagicPoints(caster.getMagicPoints() - MAGIC_POINTS_COST);

        StatusManager manager = target.getStatusManager();
        if (manager != null) {
            manager.addStatus(new ReduceDefenseStatus(REDUCE_DEFENSE_DURATION), target);
        } else {
            target.addStatus(new ReduceDefenseStatus(REDUCE_DEFENSE_DURATION));
        }
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return null;
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
        return "Sunder Armour";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "Reduces the target's defense for a short duration.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
