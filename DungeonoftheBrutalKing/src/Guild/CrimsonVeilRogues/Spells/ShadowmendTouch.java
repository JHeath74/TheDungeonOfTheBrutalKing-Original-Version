
// src/Guild/CrimsonVeilRogues/Spells/ShadowmendTouch.java
package Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import java.util.List;

public class ShadowmendTouch implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 4;
    private static final int HEAL_AMOUNT = 8;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;

    private final String name = "Shadowmend Touch";
    private final String description = "A thin ribbon of shadow stitches a small wound closed. Restores a small amount of health — just enough to keep the rogue moving.";

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
    public void cast(Charecter caster, List<Charecter> allCharacters) { applyShadowmendTouch(caster); }

    @Override
    public void cast(Charecter caster) { applyShadowmendTouch(caster); }

    @Override
    public void cast() {
        System.out.println("Shadowmend Touch is cast, but there is no caster.");
    }

    @Override
    public void cast(Charecter caster, Charecter target) { applyShadowmendTouch(caster); }

    private void applyShadowmendTouch(Charecter caster) {
        if (caster == null) return;
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only members of the Crimson Veil Rogues guild can use Shadowmend Touch.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points to cast Shadowmend Touch.");
            return;
        }
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        int healed = Math.min(HEAL_AMOUNT, caster.getMaxHitPoints() - caster.getHitPoints());
        caster.setHitPoints(caster.getHitPoints() + healed);
        System.out.println(caster.getName() + " uses Shadowmend Touch! A ribbon of shadow stitches their wound, restoring " + healed + " health.");
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { /* Not used */ }
}
