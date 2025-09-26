package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;
import SharedData.Alignment;

public abstract class Heal implements Spell {

    private static Charecter myChar = Charecter.getInstance();
    String name = null;

    public Heal() {
        this.name = "Heal";
    }

    @Override
    public void cast() {
        int intelligence = Integer.parseInt(myChar.getCharInfo().get(8));
        int maxHealth = Integer.parseInt(myChar.getCharInfo().get(10));
        int currentHealth = Integer.parseInt(myChar.getCharInfo().get(11));

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
    public Alignment getSpellAlignment() {
        return Alignment.NOT_ALIGNED;
    }
}
