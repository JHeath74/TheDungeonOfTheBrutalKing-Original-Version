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


	// CharInfo[25]
	// CharInfo[26]
	// CharInfo[27]
	// CharInfo[28]
	// CharInfo[29]
	// CharInfo[30]
	// CharInfo[31]
	// CharInfo[32]



	// src/DungeonoftheBrutalKing/Charecter.java


	    // Static method to create instance of Singleton class
	    public static Charecter Singleton() {
	        if (single_instance_myChar == null) {
	            single_instance_myChar = new Charecter();
	        }
	        return single_instance_myChar;
	    }
	    
		public void removeEffect(String effect) {
			// TODO Auto-generated method stub
			
		}

	    // Method to update Level
	    public void updateLevel(int Level) {
	        CharInfo.set(2, String.valueOf(Level));
	    }

	    // Method to update experience
	    public void updateExperience(int Experience) {
	        CharInfo.set(3, String.valueOf(Experience));
	    }
	    
	    // Method to update health
	    public void updateHealth(int health) {
	        CharInfo.set(4, String.valueOf(health));
	    }

	    // Method to update magic points
	    public void updateMagicPoints(int magicPoints) {
	        CharInfo.set(5, String.valueOf(magicPoints));
	    }

	 // Method to update Stamina Stat
	    public void updateStamina(int Stamina) {
	        CharInfo.set(6, String.valueOf(Stamina));
	    }
	    
	 // Method to update Charisma Stat
	    public void updateCharisma(int Charisma) {
	        CharInfo.set(7, String.valueOf(Charisma));
	    }
	    
	    // Method to update Strength Stat
	    public void updateStrength(int Strength) {
	        CharInfo.set(8, String.valueOf(Strength));
	    }
	    
	 // Method to update Intelligence Stat
	    public void updateIntelligence(int Intelligence) {
	        CharInfo.set(9, String.valueOf(Intelligence));
	    }
	    
	 // Method to update Wisdom Stat
	    public void updateWisdom(int Wisdom) {
	        CharInfo.set(10, String.valueOf(Wisdom));
	    }
	    
	 // Method to update Agility Stat
	    public void updateAgility(int Agility) {
	        CharInfo.set(11, String.valueOf(Agility));
	    }
	    
	    // Method to update Gold
	    public void updateGold(int Gold) {
	        CharInfo.set(12, String.valueOf(Gold));
	    }

	    // Method to get hungry (update food)
	    public void updateFood(int food) {
	        CharInfo.set(13, String.valueOf(food));
	    }
	    
	    // Method to get hungry (update food)
	    public void updateWater(int Water) {
	        CharInfo.set(14, String.valueOf(Water));
	    }
	    
	    // Method to get torches
	    public void updateTorches(int torches) {
	        CharInfo.set(15, String.valueOf(torches));
	    }
	    
	    // Method to get gems
	    public void updateGems(int gems) {
	        CharInfo.set(16, String.valueOf(gems));
	    }

	    
	    // Method to buy or sell a weapon
	    public void updateWeapon(String weapon) {
	        CharInfo.set(17, weapon);
	    }

	    // Method to buy or sell armour
	    public void updateArmour(String armour) {
	        CharInfo.set(18, armour);
	    }

	    // Method to buy or sell a shield
	    public void updateShield(String shield) {
	        CharInfo.set(19, shield);
	    }






	}







