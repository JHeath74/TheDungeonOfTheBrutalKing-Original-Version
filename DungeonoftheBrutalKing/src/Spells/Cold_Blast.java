
package Spells;


import DungeonoftheBrutalKing.Charecter;
import java.util.Random;

public abstract class Cold_Blast implements Spells {

    private static final int MINIMUM_WISDOM = 10;
    private static Charecter myChar = Charecter.Singleton();

    int attackerWisdom = Integer.parseInt(myChar.CharInfo.get(10));

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Cold Blast!");
        } else {
            Random random = new Random();
            int damage = random.nextInt(attackerWisdom) + 1;
            System.out.println("Cold Blast deals " + damage + " cold damage!");
        }
    }


}
