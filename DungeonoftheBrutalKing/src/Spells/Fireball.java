
package Spells;

import java.util.Random;
import SharedData.Alignment;
import DungeonoftheBrutalKing.Character;

public abstract class Fireball implements Spell {

    // Minimum Wisdom required to cast the spell
    private static final int MINIMUM_WISDOM = 10;
    private static Character myChar = Character.getInstance();

    @Override
    public void cast() {
        if (myChar == null || myChar.getCharInfo() == null) {
            System.out.println("Character or character info is not available.");
            return;
        }
        String wisdomValue = myChar.getCharInfo().get(10);
        int attackerWisdom = (wisdomValue != null) ? Integer.parseInt(wisdomValue) : 0;

        if (attackerWisdom < MINIMUM_WISDOM) {
            System.out.println("You lack the necessary Wisdom to cast Fireball!");
        } else {
            // Calculate random damage based on attacker's Wisdom
            Random random = new Random();
            int damage = random.nextInt(attackerWisdom) + 1; // Random value from 1 to attacker's Wisdom
            System.out.println("Fireball deals " + damage + " fire damage!");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    @Override
    public Alignment getSpellAlignment() {
        return Alignment.NEUTRAL;
    }
}
