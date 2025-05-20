
// Location.java
package Spells;

import DungeonoftheBrutalKing.Singleton;
import DungeonoftheBrutalKing.Spells;

public class Location extends Spells {

    String name = null;
    int minWisdom = 30;
    int minIntelligence = 30;

    public Location() {
        this.name = "Location";
    }

    public void castSpell(int[][][] dungeon, int targetX, int targetY, int targetZ) {

        int Wisdom = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(10));
        int Intelligence = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(9));
        int MagicPoints = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(5));

        if (Wisdom > minWisdom || Intelligence > minIntelligence && MagicPoints > 5) {
            // Assuming the character's current position is stored in Singleton
        	
        	int[] charInfo = Singleton.myCharSingleton().getCharInfo();
            int currentX = charInfo[0];
            int currentY = charInfo[1];
            int currentZ = charInfo[2];

            // Move character to the target position on the top level (z = 0)
            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            // Update character's position in Singleton
            Singleton.myCharSingleton().updatePosition(targetX, targetY, targetZ);

            System.out.println("Your Location is to (" + currentX + ", " + currentY + "," + currentZ +")!");
        }else {
        System.out.println("You Don't have of Magic Points, or either Wisdom or Intelligence to Cast this spell");
        }
    }
}
