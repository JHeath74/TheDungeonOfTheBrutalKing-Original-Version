
package Spells;

import java.util.Random;

import Alignment.Alignment;
import DungeonoftheBrutalKing.Character;

public abstract class Cold_Blast implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static Character myChar = Character.getInstance();

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Non-aligned spell type

    String wisdomValue = myChar.getCharInfo().get(10);
    int attackerWisdom = (wisdomValue != null) ? Integer.parseInt(wisdomValue) : 0;

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

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    public Alignment getSpellAlignment() {
        return getSpellAlignment(); // Getter for the spell type
    }
}
