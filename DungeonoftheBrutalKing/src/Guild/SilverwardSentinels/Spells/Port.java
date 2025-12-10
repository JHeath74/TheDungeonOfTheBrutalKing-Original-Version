
// src/Spells/Port.java
package Guild.SilverwardSentinels.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Singleton;

public class Port implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final int MIN_WISDOM = 30;
    private static final int MIN_INTELLIGENCE = 30;

    public Port() {
        // No need for name field
    }

    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        Character character = Singleton.myCharSingleton();
        int wisdom = character.getWisdom();
        int intelligence = character.getIntelligence();
        int magicPoints = character.getMagicPoints();

        if ((wisdom >= MIN_WISDOM || intelligence >= MIN_INTELLIGENCE) && magicPoints >= REQUIRED_MAGIC_POINTS) {
            int[] position = new int[3];
            character.getPosition(position);

            int currentX = position[0];
            int currentY = position[1];
            int currentZ = position[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            character.setPosition(targetX, targetY, targetZ);

            System.out.println("Teleported to (" + targetX + ", " + targetY + ", " + targetZ + ")!");
        } else {
            System.out.println("You don't have enough Magic Points, or either Wisdom or Intelligence to cast this spell.");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }


    public void cast() {
        // Not used for this spell
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
