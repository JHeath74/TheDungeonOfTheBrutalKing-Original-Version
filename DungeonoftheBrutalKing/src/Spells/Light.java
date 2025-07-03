
package Spells;

import DungeonoftheBrutalKing.Charecter;
import Spells.SpellAlignment;
import Spells.Spells;

public abstract class Light implements Spells {

    private static final SpellAlignment SPELL_ALIGNMENT = SpellAlignment.GOOD; // Alignment of the spell
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

    public SpellAlignment getSpellAlignment() {
        return SPELL_ALIGNMENT; // Getter for the spell alignment
    }
}
