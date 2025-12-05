
// src/Spells/Heal.java
package Spells;

import SharedData.Guild;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Singleton;

public class Heal implements Spell {

    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static Character myChar = Character.getInstance();
    private String name;

    public Heal() {
        this.name = "Heal";
    }

    @Override
    public void cast() {
        int intelligence = myChar.getIntelligence();
        int maxHealth = myChar.getMaxHitPoints();
        int currentHealth = myChar.getHitPoints();

        int healthRestored = 10 + intelligence;
        int newHealth = Math.min(currentHealth + healthRestored, maxHealth);

        Singleton.myCharSingleton().setHitPoints(newHealth);

        System.out.println("Restored " + (newHealth - currentHealth) + " health points!");
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    
    public void cast(int attackerWisdom) {
        // Not used for this spell
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    
    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }
}
