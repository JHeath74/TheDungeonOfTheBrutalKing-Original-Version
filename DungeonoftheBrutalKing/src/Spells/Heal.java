
package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;
import SharedData.Alignment;

public abstract class Heal implements Spells {

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Alignment of the spell
    private static Charecter myChar = Charecter.Singleton();
    String name = null;

    public Heal() {
        this.name = "Heal";
    }

    @Override
	public void cast() {
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
        Singleton.myCharSingleton().setHitPoints(newHealth);

        System.out.println("Restored " + (newHealth - currentHealth) + " health points!");
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
