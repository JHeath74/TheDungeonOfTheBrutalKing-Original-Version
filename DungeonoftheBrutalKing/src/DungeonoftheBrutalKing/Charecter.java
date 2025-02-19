package DungeonoftheBrutalKing;

import java.util.ArrayList;

public class Charecter {


	private static Charecter single_instance_myChar;
	public ArrayList<String> CharInfo = new ArrayList<>();

	public ArrayList<String> SpellsLearned = new ArrayList<>();

	//int combatSpells = 21;

	//Add Magic Points

	// CharInfo[0] = Charecter Name
	// CharInfo[1] = Class
	// CharInfo[2] = Level
	// CharInfo[3] = Experience
	// CharInfo[4] = Hit Points
	// CharInfo[5] = Magic Points

	// CharInfo[6] = Stat: Stamina
	// CharInfo[7] = Stat: Charisma
	// CharInfo[8] = Stat: Strength
	// CharInfo[9] = Stat: Intelligence
	// CharInfo[10] = Stat: Wisdom
	// CharInfo[11] = Stat: Agility

	// CharInfo[12] = Gold
	// CharInfo[13] = Food
	// CharInfo[14] = Water
	// CharInfo[15] = Torches
	// CharInfo[16] = Gems
	
	// CharInfo[17] = Weapon
	// CharInfo[18] = Armour
	// CharInfo[19] = Shield
	
	// CharInfo[20] = Map Location
	// CharInfo[21] = Morality

	//CharInfo[22] = LocationX
	//CharInfo[23] = LocationY
	//CharInfo[24] = LocationZ
	
	//Acquired Spells


	// CharInfo[24]
	// CharInfo[25]
	// CharInfo[26]
	// CharInfo[27]
	// CharInfo[28]
	// CharInfo[29]
	// CharInfo[30]
	// CharInfo[31]


	public Charecter()
	{

	}

	// Method
	// Static method to create instance of Singleton class
	public static Charecter Singleton()
	{
		// To ensure only one instance is created
		if (single_instance_myChar == null) {
			single_instance_myChar = new Charecter();
		}
		return single_instance_myChar;
	}








}
