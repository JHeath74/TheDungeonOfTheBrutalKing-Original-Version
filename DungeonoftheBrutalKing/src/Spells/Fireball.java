
package Spells;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Alignment;

//public abstract class Fireball implements Spells {
	public abstract class Fireball implements Spells{

    // Minimum Wisdom required to cast the spell
    private static final int MINIMUM_WISDOM = 10;
    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Alignment of the spell
    private static Charecter myChar = Charecter.Singleton();



    @Override
    public void cast() {
        if (myChar == null || myChar.CharInfo == null) {
            System.out.println("Character or character info is not available.");
            return;
        }
        String wisdomValue = myChar.CharInfo.get(10);
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
	public SharedData.Alignment getSpellAlignment() {
        return getSpellAlignment(); // Getter for the spell type
    }
}
