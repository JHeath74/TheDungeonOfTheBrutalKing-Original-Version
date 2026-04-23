
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Spells;

import java.util.List;
import java.util.Random;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;

public class Conjure_Food implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final Guild SPELL_GUILD = Guild.CELESTIAL_ARCANE_ORDER;

    public Conjure_Food() {}

    // Core spell logic: conjures food for the target character
    private void conjureFood(Character target) {
        if (target == null) return;
        int wisdom = target.getWisdom();
        int intelligence = target.getIntelligence();
        if (wisdom < MINIMUM_WISDOM || intelligence < MINIMUM_INTELLIGENCE) {
            System.out.println(target.getName() + " lacks the necessary Wisdom or Intelligence to cast Conjure Food!");
        } else {
            Random random = new Random();
            int foodConjured = random.nextInt(3) + 1;
            int currentFood = target.getFood();
            target.setFood(currentFood + foodConjured);
            System.out.println(target.getName() + " conjured " + foodConjured + " food items!");
        }
    }

    @Override
    public void cast(Character caster, Character target) {
        conjureFood(target != null ? target : caster);
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (allCharacters != null && !allCharacters.isEmpty()) {
            for (Character ch : allCharacters) {
                conjureFood(ch);
            }
        }
    }

    @Override
    public void cast(Character caster) {
        conjureFood(caster);
    }

    @Override
    public void cast() {
        // Not applicable: requires a character
    }

    @Override
    public void cast(int toonWisdom) {
        // Not used for this spell
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // Not used for this spell
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM || toonIntelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Wisdom or Intelligence to cast Conjure Food!");
        } else {
            Random random = new Random();
            int foodConjured = random.nextInt(3) + 1;
            System.out.println("Conjured " + foodConjured + " food items!");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public String getName() {
        return "Conjure Food";
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void castWithStrength(Character enemy, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
