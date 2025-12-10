
// src/Spells/Location.java
package Guild.SilverwardSentinels.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Singleton;

public class Location implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    String name = null;
    int minWisdom = 30;
    int minIntelligence = 30;

    public Location() {
        this.name = "Location";
    }

    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        int Wisdom = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(10));
        int Intelligence = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(9));
        int MagicPoints = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(5));

        if ((Wisdom > minWisdom || Intelligence > minIntelligence) && MagicPoints > 5) {
            int[] position = new int[3];
            Singleton.myCharSingleton().getPosition(position);
            int currentX = position[0];
            int currentY = position[1];
            int currentZ = position[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            Singleton.myCharSingleton().setPosition(targetX, targetY, targetZ);

            System.out.println("Your Location is to (" + currentX + ", " + currentY + "," + currentZ + ")!");
        } else {
            System.out.println("You Don't have enough Magic Points, Wisdom, or Intelligence to Cast this spell");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}


	public void cast(int attackerWisdom) {
		// TODO Auto-generated method stub
		
	}

	  @Override
	    public int getRequiredMagicPoints() {
	        return REQUIRED_MAGIC_POINTS;
	    }


	public void cast(int toonWisdom, int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}
}
