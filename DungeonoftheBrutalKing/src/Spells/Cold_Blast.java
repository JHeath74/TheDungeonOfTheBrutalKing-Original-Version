
package Spells;

import DungeonoftheBrutalKing.Spells;
import DungeonoftheBrutalKing.Charecter;
import java.util.Random;

public class Cold_Blast extends Spells{

    // Minimum Wisdom required to cast the spell
    private static final int MINIMUM_WISDOM = 10;
    private static Charecter myChar = Charecter.Singleton();

    int AttackerWisdom = Integer.parseInt(myChar.CharInfo.get(10));

    public static void castSpell(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
        } else {
            // Calculate random damage based on attacker's Wisdom
            Random random = new Random();
            int damage = random.nextInt(attackerWisdom) + 1; // Random value from 1 to attacker's Wisdom
            System.out.println("Cold Blast deals " + damage + " cold damage!");
        }
    }
}
