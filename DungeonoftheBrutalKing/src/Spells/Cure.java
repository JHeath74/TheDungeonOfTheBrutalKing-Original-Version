
// Cure.java
package Spells;

import DungeonoftheBrutalKing.Singleton;
import DungeonoftheBrutalKing.Spells;
import java.util.Random;

public class Cure extends Spells {

	String name = null;
	
	
    public Cure() {
        this.name = "Cure";
    }

    public void castSpell() {
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
}
