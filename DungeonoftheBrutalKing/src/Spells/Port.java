
// Port.java
package Spells;

import DungeonoftheBrutalKing.Singleton;
import DungeonoftheBrutalKing.Spells;

public class Port extends Spells {

    String name = null;
    int Wisdom = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(10));
    int Intelligence = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(9));
    int MagicPoints = Integer.parseInt(Singleton.myCharSingleton().CharInfo.get(5));
    
   
    int minWisdom = 30;
    int minIntelligence = 30;
    
    public Port() {
        this.name = "Teleport";
    }

    public void castSpell(int[][][] dungeon, int targetX, int targetY, int targetZ) {
      
    	if (Wisdom > minWisdom || Intelligence > minIntelligence && MagicPoints > 5)
    	{
    	
    	// Assuming the character's current position is stored in Singleton
        int currentX = (int) myChar.getCharInfo(22);
        int currentY = (int) myChar.getCharInfo(23);
        int currentZ = (int) myChar.getCharInfo(24);

        // Move character to the target position on the top level (z = 0)
        dungeon[currentZ][currentY][currentX] = 0; // Clear current position
        dungeon[targetZ][targetY][targetX] = 1; // Set new position

        // Update character's position in Singleton
        Singleton.myCharSingleton().updatePosition(targetX, targetY, targetZ);

        System.out.println("Teleported to (" + targetX + ", " + targetY + ", 0)!");
    	}else {
    		System.out.println("You Don't have of Magic Points, or either Wisdom or Intelligence to Cast this spell");
    	}
    }
}
