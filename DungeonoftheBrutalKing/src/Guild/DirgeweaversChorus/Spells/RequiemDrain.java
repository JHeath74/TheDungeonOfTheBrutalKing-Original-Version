
package Guild.DirgeweaversChorus.Spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Spells.Spell;
import Status.LifeStealStatus;

public class RequiemDrain implements Spell {

    private static final String NAME = "Requiem Drain";
    private static final String DESCRIPTION =
            "A profane verse that siphons vitality from the target and binds a lingering life-steal curse.";

    private static final Guild SPELL_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final int REQUIRED_WISDOM = 4;
    private static final int REQUIRED_MAGIC_POINTS = 4;

    private static final int BASE_DAMAGE = 6;

    private static final int LIFE_STEAL_MIN_PERCENT = 10;
    private static final int LIFE_STEAL_MAX_PERCENT = 20;
    private static final int LIFE_STEAL_DURATION_TURNS = 2;

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

        int damage = Math.max(1, BASE_DAMAGE + (caster.getCharisma() / 6));
        target.takeDamage(damage);

        int rollPercent = ThreadLocalRandom.current().nextInt(LIFE_STEAL_MIN_PERCENT, LIFE_STEAL_MAX_PERCENT + 1);

        int targetHp = Math.max(0, target.getHitPoints());
        int lifeStealAmount = Math.max(1, (targetHp * rollPercent) / 100);

        LifeStealStatus siphon = new LifeStealStatus(lifeStealAmount, LIFE_STEAL_DURATION_TURNS);
        siphon.apply(caster, target);

        target.addStatus(siphon);
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
    public void cast(int toonWisdom) {
        // No-op: cannot resolve caster/target from stats alone.
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // No-op: spell uses wisdom/charisma and needs entities.
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // No-op: needs entities.
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // No-op: this spell is single-target and needs an enemy target.
    }

    @Override
    public void cast(Charecter caster) {
        // No-op: needs a target.
    }

    @Override
    public void cast() {
        // No-op: needs caster and target.
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        // No-op: cannot safely treat Charecter as Enemies with current types.
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) {
        // No-op: not strength-based.
    }
}
