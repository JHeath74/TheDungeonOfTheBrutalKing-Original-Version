
package Guild.DirgeweaversChorus.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.ImmobilizedStatus;

public class SappingDirge implements Spell {

    private static final String NAME = "Sapping Dirge";
    private static final String DESCRIPTION =
            "A venomous refrain that bites into resolve, dealing damage and weakening the target’s attacks for a short time.";

    private static final Guild SPELL_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final int REQUIRED_WISDOM = 5;
    private static final int REQUIRED_MAGIC_POINTS = 6;

    private static final int BASE_DAMAGE = 3;

    private static final int WEAKEN_MIN_PERCENT = 10;
    private static final int WEAKEN_MAX_PERCENT = 20;
    private static final int WEAKEN_DURATION_TURNS = 2;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public boolean canCast(Charecter caster) {
        return caster != null && caster.getWisdom() >= REQUIRED_WISDOM;
    }

    public void cast(Charecter caster, Enemies target) {
        if (caster == null || target == null) return;
        if (target.isDead()) return;
        if (!canCast(caster)) return;

        int damage = Math.max(1, BASE_DAMAGE + (caster.getCharisma() / 10));
        target.takeDamage(damage);

        if (target.isDead()) return;

        // Use ImmobilizedStatus instead of WeakenStatus
        int duration = ThreadLocalRandom.current().nextInt(WEAKEN_DURATION_TURNS, WEAKEN_DURATION_TURNS + 1);
        target.addStatus(new ImmobilizedStatus(duration));
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
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
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
