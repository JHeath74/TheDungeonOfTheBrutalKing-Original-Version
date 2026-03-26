
// src/Guild/CrimsonVeilRogues/Spells/SilentTakedown.java
package Guild.CrimsonVeilRogues.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class SilentTakedown implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Silent Takedown";
    private final String description = "If the rogue is behind an unaware target, they can attempt a quiet incapacitation or heavy damage strike (critical hit).";

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return SPELL_GUILD; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { /* Not used */ }

    @Override
    public void castWithIntelligence(int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { /* Not used */ }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) { /* Not used */ }

    @Override
    public void cast(Charecter caster) { /* Not used */ }

    @Override
    public void cast() {
        System.out.println("Silent Takedown is cast, but there is no caster or target.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        applySilentTakedown(caster, target);
    }

    private void applySilentTakedown(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Silent Takedown.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Silent Takedown.");
            return;
        }
        int criticalDamage = caster.getAttack() * 2;
        target.takeDamage(criticalDamage);
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " performs a Silent Takedown on " + target.getName() + ", dealing " + criticalDamage + " critical damage!");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
