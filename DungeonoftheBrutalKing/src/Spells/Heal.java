
package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;
import DungeonoftheBrutalKing.Spells;

public class Heal extends Spells {

    private static Charecter myChar = Charecter.Singleton();
    String name = null;

    public Heal() {
        this.name = "Heal";
    }

    public void castSpell() {
        int intelligence = Integer.parseInt(myChar.CharInfo.get(8));
        int maxHealth = Integer.parseInt(myChar.CharInfo.get(10));
        int currentHealth = Integer.parseInt(myChar.CharInfo.get(11));

        // Minimum health restored
        int healthRestored = 10;

        // Additional health based on intelligence
        healthRestored += intelligence;

        // Ensure health does not exceed max health
        int newHealth = Math.min(currentHealth + healthRestored, maxHealth);

        // Update character's health
        Singleton.myCharSingleton().updateHealth(newHealth);

        System.out.println("Restored " + (newHealth - currentHealth) + " health points!");
    }
    
    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }
}
