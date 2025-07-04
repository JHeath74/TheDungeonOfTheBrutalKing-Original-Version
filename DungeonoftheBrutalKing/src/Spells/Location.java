
package Spells;

import DungeonoftheBrutalKing.Singleton;
import Spells.Spells;
import Spells.SpellAlignment;

public abstract class Location implements Spells {

    private static final SpellAlignment SPELL_ALIGNMENT = SpellAlignment.NEUTRAL; // Alignment of the spell
    String name = null;
    int minWisdom = 30;
    int minIntelligence = 30;

    public Location() {
        this.name = "Location";
    }

    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        int Wisdom = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(10));
        int Intelligence = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(9));
        int MagicPoints = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(5));

        if (Wisdom > minWisdom || Intelligence > minIntelligence && MagicPoints > 5) {
            int[] charInfo = Singleton.myCharSingleton().getCharInfo();
            int currentX = charInfo[0];
            int currentY = charInfo[1];
            int currentZ = charInfo[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            Singleton.myCharSingleton().updatePosition(targetX, targetY, targetZ);

            System.out.println("Your Location is to (" + currentX + ", " + currentY + "," + currentZ + ")!");
        } else {
            System.out.println("You Don't have enough Magic Points, Wisdom, or Intelligence to Cast this spell");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    public SpellAlignment getSpellAlignment() {
        return SPELL_ALIGNMENT; // Getter for the spell alignment
    }

}
