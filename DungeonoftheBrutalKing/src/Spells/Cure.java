
package Spells;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;
import SharedData.Alignment;

public abstract class Cure implements Spell {

    private static final int MINIMUM_WISDOM = 10;
    private static final int MINIMUM_INTELLIGENCE = 10;
    private static Charecter myChar = Charecter.getInstance();

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Alignment of the spell
    private String name = "Cure";

    public Cure() {
        super();
    }

    @Override
    public void cast() {
        // List of negative effects to be cured
        String[] negativeEffects = {"Poison", "Blindness", "Paralysis"};
        Random random = new Random();

        // Attempt to remove negative effects from the character
        for (String effect : negativeEffects) {
            if (random.nextDouble() < 0.75) { // 75% chance to remove the effect
                Singleton.myCharSingleton().removeEffect(effect);
                System.out.println("Cured " + effect + "!");
            } else {
                System.out.println("Failed to cure " + effect + ".");
            }
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
