
package Spells;

import DungeonoftheBrutalKing.Spells;
import DungeonoftheBrutalKing.Charecter;
import java.util.Random;

public class Conjure_Food extends Spells {

    // Minimum Wisdom and Intelligence required to cast the spell
    private static final int MINIMUM_WISDOM = 10;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static Charecter myChar = Charecter.Singleton();

    public static void castSpell(int toonWisdom, int toonIntelligence) {
        if (toonWisdom < MINIMUM_WISDOM || toonIntelligence < MINIMUM_INTELLIGENCE) {
            System.out.println("You lack the necessary Wisdom or Intelligence to cast Conjure Food!");
        } else {
            // Conjure 1 to 3 food items
            Random random = new Random();
            int foodConjured = random.nextInt(3) + 1; // Random value from 1 to 3
            int currentFood = Integer.parseInt(myChar.CharInfo.get(13));
            myChar.updateFood(currentFood + foodConjured);
            System.out.println("Conjured " + foodConjured + " food items!");
        }
    }
}
