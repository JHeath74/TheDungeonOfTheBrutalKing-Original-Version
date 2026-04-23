
// src/Guild/CrimsonVeilRogues/Spells/DuskbladeInfusion.java
package DungeonoftheBrutalKing.Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.StatusType;

public class DuskBladeInFusion implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 5;
    private static final Guild SPELL_GUILD = Guild.CRIMSON_VEIL_ROGUES;
    private final String name = "Duskblade Infusion";
    private final String description = "Channels shadow magic into your weapon. Next hit deals bonus damage if hidden. First hit hides you, 75% chance to crit.";

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
    public void cast(Character caster, Character target) {
        if (caster.getGuild() != SPELL_GUILD) {
            System.out.println("Only Crimson Veil Rogues can use Duskblade Infusion.");
            return;
        }
        if (caster.getMagicPoints() < REQUIRED_MAGIC_POINTS) {
            System.out.println(caster.getName() + " does not have enough magic points.");
            return;
        }
        caster.applyStatusEffect(StatusType.HIDDEN_STATUS, 1, 0, caster); // Hide caster for 1 turn
        caster.setMagicPoints(caster.getMagicPoints() - REQUIRED_MAGIC_POINTS);
        System.out.println(caster.getName() + " channels shadow magic, hiding and infusing their weapon!");

        // Next attack logic (pseudo-code, depends on your attack system)
        // if (caster.isHidden()) {
        //     int bonusDamage = ...;
        //     boolean crit = Math.random() < 0.75;
        //     if (crit) { /* apply critical damage */ }
        //     /* apply bonus damage */
        //     caster.setHidden(false); // Remove hidden after attack
        // }
    }

    // Other unused methods...
    @Override public void cast(int toonWisdom) {}
    @Override public void castWithIntelligence(int toonIntelligence) {}
    @Override public void cast(int toonWisdom, int toonIntelligence) {}
    @Override public void cast(Character caster, java.util.List<Character> allCharacters) {}
    @Override public void cast(Character caster) {}
    @Override public void cast() {}
    @Override public void castWithStrength(Character enemy, double d) {}

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
