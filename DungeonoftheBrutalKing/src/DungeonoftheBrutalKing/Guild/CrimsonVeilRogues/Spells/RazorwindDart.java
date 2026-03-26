
// src/Guild/CrimsonVeilRogues/Spells/RazorwindDart.java
package Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class RazorwindDart implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 4;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;
    private final String name = "Razorwind Dart";
    private final String description = "A thrown blade moves with unnatural speed, slicing through the air and hitting a target even around partial cover.";

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
    public void cast(Charecter caster, Charecter target) {
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only Crimson Veil Rogues can use Razorwind Dart.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points.");
            return;
        }
        // Ignores partial cover
        int damage = 8; // Example base damage
        target.takeDamage(damage, caster);
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " throws a Razorwind Dart at " + target.getName() + ", ignoring partial cover and dealing " + damage + " damage!");
    }

    // Other unused methods...
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void cast(Charecter caster, java.util.List<Charecter> allCharacters) {}
    @Override public void cast(Charecter caster) {}
    @Override public void cast() {}
    @Override public void castWithStrength(Charecter enemy, double d) {}
}
