package DungeonoftheBrutalKing.Guild.CrimsonBlades.Spells;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import java.util.List;

public class TitanbreakerStrike implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 15;
    private static final int STRIKE_DAMAGE = 22;
    private static final double DAMAGE_MULTIPLIER = 1.7;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

    private final String name = "Titanbreaker Strike";
    private final String description = "A devastating overhead blow meant to shatter armor and resolve.";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.CRIMSON_BLADES;
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
    public void cast(Character caster, List<Character> allCharacters) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Titanbreaker Strike.");
            return;
        }
        for (Character target : allCharacters) {
            if (target != caster) {
                target.takeDamage(STRIKE_DAMAGE);
                System.out.println(caster.getName() + " smashes " + target.getName() +
                    " with Titanbreaker Strike, dealing " + STRIKE_DAMAGE + " damage and shattering their armor!");
            }
        }
    }

    @Override
    public void cast(Character caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Titanbreaker Strike.");
            return;
        }
        System.out.println(caster.getName() + " prepares Titanbreaker Strike, but there is no target.");
    }

    @Override
    public void cast() {
        System.out.println("Titanbreaker Strike is cast with no caster.");
    }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Titanbreaker Strike.");
            return;
        }
        if (target == null) return;
        target.takeDamage((int)Math.round(STRIKE_DAMAGE * DAMAGE_MULTIPLIER));
        System.out.println(caster.getName() + " slams into " + target.getName() +
            " with Titanbreaker Strike, dealing " + (int)Math.round(STRIKE_DAMAGE * DAMAGE_MULTIPLIER) + " damage.");
    }

    @Override
    public void castWithStrength(Character caster, double d) { }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}