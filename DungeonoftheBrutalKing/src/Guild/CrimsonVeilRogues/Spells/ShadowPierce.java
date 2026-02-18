
// src/Guild/CrimsonVeilRogues/Spells/ShadowPierce.java
package Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import java.util.List;

public class ShadowPierce implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int BASE_DAMAGE = 12;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Shadow Pierce";
    private final String description = "A thrust that briefly phases through armor, dealing direct damage to the target’s vitality rather than their defenses.";

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
        System.out.println("Shadow Pierce is cast, but there is no caster or target.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Shadow Pierce.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Shadow Pierce.");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        int vitalityDamage = BASE_DAMAGE;
        target.setHitPoints(Math.max(target.getHitPoints() - vitalityDamage, 0));
        System.out.println(caster.getName() + " uses Shadow Pierce! " + target.getName() +
            " loses " + vitalityDamage + " vitality (ignoring defense).");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
