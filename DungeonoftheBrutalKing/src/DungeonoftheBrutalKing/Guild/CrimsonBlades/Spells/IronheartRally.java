package DungeonoftheBrutalKing.Guild.CrimsonBlades.Spells;

import java.util.List;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class IronheartRally implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final int HEAL_AMOUNT = 8;
    
    private static final Guild SPELL_GUILD = Guild.CRIMSON_BLADES;

    private final String name = "Ironheart Rally";
    private final String description = "A battle cry that restores courage and boosts allies’ morale.";

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
            System.out.println("Only members of the Crimson Blades guild can use Ironheart Rally.");
            return;
        }
        for (Character ally : allCharacters) {
            if (ally != caster) {
                ally.restoreHitPoints(HEAL_AMOUNT);
                System.out.println(caster.getName() + " rallies " + ally.getName() +
                    ", restoring " + HEAL_AMOUNT + " hit points!");
            }
        }
    }

    @Override
    public void cast(Character caster) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Ironheart Rally.");
            return;
        }
        System.out.println(caster.getName() + " shouts Ironheart Rally, but there are no allies to boost.");
    }

    @Override
    public void cast() {
        System.out.println("Ironheart Rally is cast, but there is no caster.");
    }

    @Override
    public void cast(Character caster, Character target) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Ironheart Rally.");
            return;
        }
        if (target == null) return;
        target.restoreHitPoints(HEAL_AMOUNT);
        System.out.println(caster.getName() + " rallies " + target.getName() +
            ", restoring " + HEAL_AMOUNT + " hit points!");
    }

    @Override
    public void castWithStrength(Character caster, double strengthMultiplier) {
        if (caster == null || caster.getGuild() != Guild.CRIMSON_BLADES) {
            System.out.println("Only members of the Crimson Blades guild can use Ironheart Rally.");
            return;
        }
        int heal = (int) Math.round(HEAL_AMOUNT * strengthMultiplier);
        System.out.println(caster.getName() + " uses Ironheart Rally with a strength multiplier of " + strengthMultiplier +
            ", restoring " + heal + " hit points to each ally (no targets specified).");
    }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
