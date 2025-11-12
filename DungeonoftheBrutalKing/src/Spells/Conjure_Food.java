
package Spells;

import java.util.Random;

import Alignment.Alignment;
import DungeonoftheBrutalKing.Charecter;

public abstract class Conjure_Food implements Spell {

    // Minimum Wisdom and Intelligence required to cast the spell
    private static final int MINIMUM_WISDOM = 10;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static Charecter myChar = Charecter.getInstance();

    public Conjure_Food() {
        super(); // Explicitly call the constructor of the Spells class
    }

    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM || toonIntelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Wisdom or Intelligence to cast Conjure Food!");
        } else {
            // Conjure 1 to 3 food items
            Random random = new Random();
            int foodConjured = random.nextInt(3) + 1; // Random value from 1 to 3
            int currentFood = Integer.parseInt(myChar.getCharInfo().get(13));
            myChar.setFood(currentFood + foodConjured);
            System.out.println("Conjured " + foodConjured + " food items!");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    @Override
    public Alignment getSpellAlignment() {
        return Alignment.NOT_ALIGNED;
    }
}
