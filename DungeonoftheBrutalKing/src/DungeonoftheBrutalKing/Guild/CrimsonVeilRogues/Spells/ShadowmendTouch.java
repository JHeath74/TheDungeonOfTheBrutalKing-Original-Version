
// src/Guild/CrimsonVeilRogues/Spells/ShadowmendTouch.java
package DungeonoftheBrutalKing.Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
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
    public void cast(Character caster, List<Character> allCharacters) { applyShadowmendTouch(caster); }

    @Override
    public void cast(Character caster) { applyShadowmendTouch(caster); }

    @Override
    public void cast() {
        System.out.println("Shadowmend Touch is cast, but there is no caster.");
    }

    @Override
    public void cast(Character caster, Character target) { applyShadowmendTouch(caster); }

    private void applyShadowmendTouch(Character caster) {
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
    public void castWithStrength(Character enemy, double d) { /* Not used */ }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
