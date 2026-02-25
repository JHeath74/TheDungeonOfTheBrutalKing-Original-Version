
// src/Guild/DirgeweaversChorus/Spells/RendingRefrain.java
package Guild.DirgeweaversChorus.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.DazeStatus;

// TODO: replace this import with whichever status you actually have:
// import Status.ReduceDefenseStatus;
// import Status.ReduceStrengthStatus;
// import Status.FearStatus;
// import Status.DazeStatus;

public class RendingRefrain implements Spell {

    private static final String NAME = "Rending Refrain";
    private static final String DESCRIPTION =
            "A serrated chorus that cuts through composure, dealing damage and cursing the target with a hindering debuff.";

    private static final Guild SPELL_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_DAMAGE = 4;

    // Pick ONE debuff type you already support:
    // \- ReduceDefense: percent range
    // \- ReduceStrength: percent range
    // \- Fear/Daze: chance + duration
    private static final int MIN_PERCENT = 10;
    private static final int MAX_PERCENT = 20;

    private static final int PROC_CHANCE_PERCENT = 35;
    private static final int DURATION_TURNS = 2;

    @Override
    public String getName() { return NAME; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    public boolean canCast(Charecter caster) {
        return caster != null && caster.getWisdom() >= REQUIRED_WISDOM;
    }

    public void cast(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;
        if (target.isDead()) return;
        if (!canCast(caster)) return;

        int damage = Math.max(1, BASE_DAMAGE + (caster.getCharisma() / 9));
        target.takeDamage(damage);
        if (target.isDead()) return;

        // Option A (percent debuff): reduce defense or strength.
        int percent = ThreadLocalRandom.current().nextInt(MIN_PERCENT, MAX_PERCENT + 1);

        // Uncomment ONE of these lines after fixing the import/class name:
        // target.addStatus(new ReduceDefenseStatus(percent, DURATION_TURNS));
        // target.addStatus(new ReduceStrengthStatus(percent, DURATION_TURNS));

        // Option B (control debuff): fear or daze with a chance.
        int roll = ThreadLocalRandom.current().nextInt(1, 101);
        if (roll <= PROC_CHANCE_PERCENT) {
            // Uncomment ONE of these lines after fixing the import/class name:
            // target.addStatus(new FearStatus(DURATION_TURNS));
             target.addStatus(new DazeStatus(DURATION_TURNS));
        }
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) { }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
