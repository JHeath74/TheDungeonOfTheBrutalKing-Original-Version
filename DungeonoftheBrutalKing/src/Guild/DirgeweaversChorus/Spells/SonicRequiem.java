
package Guild.DirgeweaversChorus.Spells;

import java.util.List;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildMembershipStatus;
import SharedData.GuildType;
import Spells.Spell;

public class SonicRequiem implements Spell {

    private static final int MAGIC_POINTS_COST = 12;

    // Tunables
    private static final int BASE_DAMAGE = 8;
    private static final int DAMAGE_PER_LEVEL = 2;
    private static final int MAX_DAMAGE = 60;

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;

        if (caster.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) return;
        if (caster.getCurrentGuild() != GuildType.BARD) return;

        if (caster.getMagicPoints() < MAGIC_POINTS_COST) return;
        caster.setMagicPoints(caster.getMagicPoints() - MAGIC_POINTS_COST);

        int damage = computeDamage(caster);

        int newHp = Math.max(0, target.getHitPoints() - damage);
        target.setHitPoints(newHp);
    }

    /**
     * Damage scales with caster level.
     * If your project uses a different API, swap `getLevel()` for a bard stat,
     * e.g. `getCharisma()` or `getDexterity()`.
     */
    private int computeDamage(Charecter caster) {
        int level = 1;
        try {
            level = Math.max(1, caster.getLevel());
        } catch (Throwable ignored) {
            // Keep fallback = 1 if the project has no level getter.
        }

        int dmg = BASE_DAMAGE + (level * DAMAGE_PER_LEVEL);
        return Math.min(MAX_DAMAGE, dmg);
    }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.DIRGEWEAVERS_CHORUS; }

    @Override
    public int getRequiredMagicPoints() { return MAGIC_POINTS_COST; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() { return "Sonic Requiem"; }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { }

    @Override
    public void cast(Charecter caster) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "A weaponized chant that damages the target with a piercing sonic burst.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
