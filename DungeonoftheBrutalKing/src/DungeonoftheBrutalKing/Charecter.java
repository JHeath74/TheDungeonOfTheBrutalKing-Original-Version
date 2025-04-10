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
	
	//Date and Time
	// CharInfo[25] = Date (Day)
	//CharInfo[26] = Date (Month)
	//CharInfo[27] = Time (Time)
	
	
	
	//Acquired Spells


	// CharInfo[28]
	// CharInfo[29]
	// CharInfo[30]
	// CharInfo[31]
	// CharInfo[32]
	// CharInfo[33]
	// CharInfo[34]
	// CharInfo[35]



	// src/DungeonoftheBrutalKing/Charecter.java


	    // Static method to create instance of Singleton class
	    public static Charecter Singleton() {
	        if (single_instance_myChar == null) {
	            single_instance_myChar = new Charecter();
	        }
	        return single_instance_myChar;
	    }
	    
	    // Method to update CharInfo at a specific index
	    public void updateCharInfo(int index, String value) {
	        if (index >= 0 && index < CharInfo.size()) {
	            CharInfo.set(index, value);
	        } else if (index == CharInfo.size()) {
	            CharInfo.add(value);
	        } else {
	            // Handle invalid index if necessary
	        }
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
	    
	    public int getHitPoints() {
	        return Integer.parseInt(CharInfo.get(4));
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

		public int[] getCharInfo(int i) {
			int[] position = new int[3];
	        position[0] = Integer.parseInt(CharInfo.get(22)); // current x position
	        position[1] = Integer.parseInt(CharInfo.get(23)); // current y position
	        position[2] = Integer.parseInt(CharInfo.get(24)); // current z position
	        return position;
		}

		public void updatePosition(int targetX, int targetY, int targetZ) {
			CharInfo.set(22, String.valueOf(targetX)); // Update x position
	        CharInfo.set(23, String.valueOf(targetY)); // Update y position
	        CharInfo.set(24, String.valueOf(targetZ)); // Update z position
			
		}

		public void addToInventory(String selectedItem) {
			// TODO Auto-generated method stub
			
		}
		
		public void removeFromInventory(String selectedItem)
		{
			
		}
		

		// Method to remove gold

		// Method to remove gold
		public boolean removeGold(int amount) {
		    // Ensure CharInfo has enough elements
		    while (CharInfo.size() <= 12) {
		        CharInfo.add("0"); // Add default values if necessary
		    }

		    int currentGold = Integer.parseInt(CharInfo.get(12)); // Get current gold
		    int newGold = Math.max(0, currentGold - amount); // Subtract amount, ensuring it doesn't go below 0
		    CharInfo.set(12, String.valueOf(newGold)); // Update gold in CharInfo
		    return currentGold >= amount; // Return true if enough gold was available.
		}



		// Method to add gold
		public void addGold(int amount) {
		    int currentGold = Integer.parseInt(CharInfo.get(12)); // Get current gold
		    int newGold = currentGold + amount; // Add the specified amount
		    CharInfo.set(12, String.valueOf(newGold)); // Update gold in CharInfo
		}


		// Method to get the current gold
		public int getGold() {
		    return Integer.parseInt(CharInfo.get(12)); // Retrieve and return current gold
		}
		

		// Method to add food
		public void addFood(int amount) {
		    // Ensure CharInfo has enough elements
		    while (CharInfo.size() <= 13) {
		        CharInfo.add("0"); // Add default values if necessary
		    }

		    int currentFood = Integer.parseInt(CharInfo.get(13)); // Get current food
		    int newFood = currentFood + amount; // Add the specified amount
		    CharInfo.set(13, String.valueOf(newFood)); // Update food in CharInfo
		}



		// Method to remove food
		public boolean removeFood(int amount) {
		    // Ensure CharInfo has enough elements
		    while (CharInfo.size() <= 13) {
		        CharInfo.add("0"); // Add default values if necessary
		    }

		    int currentFood = Integer.parseInt(CharInfo.get(13)); // Get current food
		    if (currentFood < amount) {
		        return false; // Not enough food to remove
		    }

		    int newFood = currentFood - amount; // Subtract the specified amount
		    CharInfo.set(13, String.valueOf(newFood)); // Update food in CharInfo
		    return true; // Food successfully removed
		}


		// Method to get the current food
		public int getFood() {
		    return Integer.parseInt(CharInfo.get(13)); // Retrieve and return current food
		}


		// Method to get the current water
		public int getWater() {
		    return Integer.parseInt(CharInfo.get(14)); // Retrieve and return current water
		}

		// Method to get the current torches
		public int getTorches() {
		    return Integer.parseInt(CharInfo.get(15)); // Retrieve and return current torches
		}





	}







