
package Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Alignment;

public abstract class Light implements Spell {

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Alignment of the spell
    private static Charecter myChar = Charecter.Singleton();

    public Light() {
        super();

        String name = "Light";
        int requiredint = 30;
        int requiredwis = 30;

        String charintelligence = Charecter.Singleton().CharInfo.get(8).toString();
        String charwisdom = Charecter.Singleton().CharInfo.get(9).toString();
    }

    @Override
    public void cast() {
        System.out.println("Casting the Light spell...");
        // Add specific behavior for the Light spell here
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    public SharedData.Alignment getSpellAlignment() {
        return getSpellAlignment(); // Getter for the spell type
    }
}
