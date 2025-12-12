
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;

public class Starfall {
    private static final int BASE_DAMAGE = 18;

    // Casts Starfall on a single target
    public void cast(Charecter caster, Charecter target) {
        int radiantDamage = BASE_DAMAGE + (int)(caster.getIntelligence() * 1.3);
        target.setHitPoints(target.getHitPoints() - radiantDamage);
        // Optionally, add visual or log effect here
    }
}
