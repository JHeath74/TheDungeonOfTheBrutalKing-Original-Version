package DungeonoftheBrutalKing.Guild.CrimsonBlades.Spells;
import java.util.List;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class EchoingBladeDance implements Spell {
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final double DAMAGE_MULTIPLIER = 1.2;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

    private final String name = "Echoing Blade Dance";
    private final String description = "Rapid slashes that leave shimmering after‐images, striking multiple foes.";

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isGuildSpell() { return true; }

    @Override
    public Guild getSpellGuild() { return Guild.CRIMSON_BLADES; }

    @Override
    public int getRequiredMagicPoints() { return REQUIRED_MAGIC_POINTS; }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Echoing Blade Dance.");
            return;
        }
        int damage = (int) Math.round(caster.getStrength() * DAMAGE_MULTIPLIER);
        for (Character target : allCharacters) {
            if (target != caster) {
                target.takeDamage(damage);
                System.out.println(caster.getName() + " slashes " + target.getName() +
                    " with Echoing Blade Dance, dealing " + damage + " damage!");
            }
        }
    }

    @Override
    public void cast(Character caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Echoing Blade Dance.");
            return;
        }
        System.out.println(caster.getName() + " prepares Echoing Blade Dance, but there is no target.");
    }

    @Override
    public void cast() {
        System.out.println("Echoing Blade Dance is cast, but there is no caster.");
    }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Echoing Blade Dance.");
            return;
        }
        if (target == null) return;
        int damage = (int) Math.round(caster.getStrength() * DAMAGE_MULTIPLIER);
        target.takeDamage(damage);
        System.out.println(caster.getName() + " slashes " + target.getName() +
            " with Echoing Blade Dance, dealing " + damage + " damage!");
    }

    @Override
    public void castWithStrength(Character caster, double strengthMultiplier) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Echoing Blade Dance.");
            return;
        }
        int damage = (int) Math.round(caster.getStrength() * strengthMultiplier);
        System.out.println(caster.getName() + " uses Echoing Blade Dance with a strength multiplier of " + strengthMultiplier + ", dealing " + damage + " damage (no target specified).");
    }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
