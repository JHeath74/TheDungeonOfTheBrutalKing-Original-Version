
// src/DungeonoftheBrutalKing/Charecter.java
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Quests.Quest;


//CharInfo ArrayList:
//Index 0: Character's name (String)
//Index 1: Character's class or type (String)
//Index 2: Character's level (int as String)
//Index 3: Character's experience points (int as String)
//Index 4: Character's health points (HP) (int as String)
//Index 5: Character's magic points (MP) (int as String)
//Index 6: Character's stamina (int as String)
//Index 7: Character's charisma (int as String)
//Index 8: Character's strength (int as String)
//Index 9: Character's intelligence (int as String)
//Index 10: Character's wisdom (int as String)
//Index 11: Character's agility (int as String)
//Index 12: Character's gold (int as String)
//Index 13: Character's food (int as String)
//Index 14: Character's water (int as String)
//Index 15: Character's torches (int as String)
//Index 16: Character's gems (int as String)
//Index 17: Character's equipped weapon (String)
//Index 18: Character's equipped armor (String)
//Index 19: Character's equipped shield (String)
//Index 22: Character's X position (int as String)
//Index 23: Character's Y position (int as String)
//Index 24: Character's Z position (int as String)


//SpellsLearned ArrayList:
//This ArrayList stores the names of spells that the character has learned.
//Each element represents the name of a spell as a String.
//Example:
//Index 0: "Fireball"
//Index 1: "Heal"
//Index 2: "Shield"
//The list is used to track which spells the character can cast during gameplay.



public class Charecter {

    private String name;
    private EffectManager effectManager = new EffectManager();
    private List<String> activeEffects = new ArrayList<>();


    public Charecter() {
        this.name = name;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    // Singleton instance of the Charecter class
    private static Charecter single_instance_myChar;

    // List to store character information
    public ArrayList<String> CharInfo = new ArrayList<>();

    // List to store spells learned by the character
    public ArrayList<String> SpellsLearned = new ArrayList<>();

    // List to store the character's inventory
    public ArrayList<String> CharInventory = new ArrayList<>();
    
 // List to store active quests for the character
    private List<Quest> activeQuests = new ArrayList<>();

    // Static method to create or retrieve the Singleton instance of the Charecter class
    public static Charecter Singleton() {
        if (single_instance_myChar == null) {
            single_instance_myChar = new Charecter();
        }
        return single_instance_myChar;
    }

    // Method to update CharInfo at a specific index
    public void updateCharInfo(int index, String value) {
        if (index >= 0 && index < CharInfo.size()) {
            CharInfo.set(index, value); // Update existing value
        } else if (index == CharInfo.size()) {
            CharInfo.add(value); // Add new value if index matches size
        } else {
            // Handle invalid index if necessary
        }
    }

    // Method to update the character's level
    public void updateLevel(int Level) {
        CharInfo.set(2, String.valueOf(Level));
    }

    // Method to update the character's experience
    public void updateExperience(int Experience) {
        CharInfo.set(3, String.valueOf(Experience));
    }

    // Method to update the character's health
    public void updateHealth(int health) {
        CharInfo.set(4, String.valueOf(health));
    }

    // Method to retrieve the character's hit points
    public int getHitPoints() {
        return Integer.parseInt(CharInfo.get(4));
    }

    // Method to update the character's magic points
    public void updateMagicPoints(int magicPoints) {
        CharInfo.set(5, String.valueOf(magicPoints));
    }

    // Methods to update character stats
    public void updateStamina(int Stamina) {
        CharInfo.set(6, String.valueOf(Stamina));
    }

    public void updateCharisma(int Charisma) {
        CharInfo.set(7, String.valueOf(Charisma));
    }

    public void updateStrength(int Strength) {
        CharInfo.set(8, String.valueOf(Strength));
    }

    public void updateIntelligence(int Intelligence) {
        CharInfo.set(9, String.valueOf(Intelligence));
    }

    public void updateWisdom(int Wisdom) {
        CharInfo.set(10, String.valueOf(Wisdom));
    }

    public void updateAgility(int Agility) {
        CharInfo.set(11, String.valueOf(Agility));
    }

    // Methods to update resources
    public void updateGold(int Gold) {
        CharInfo.set(12, String.valueOf(Gold));
    }

    public void updateFood(int food) {
        CharInfo.set(13, String.valueOf(food));
    }

    public void updateWater(int Water) {
        CharInfo.set(14, String.valueOf(Water));
    }

    public void updateTorches(int torches) {
        CharInfo.set(15, String.valueOf(torches));
    }

    public void updateGems(int gems) {
        CharInfo.set(16, String.valueOf(gems));
    }

    // Methods to update equipment
    public void updateWeapon(String weapon) {
        CharInfo.set(17, weapon);
    }

    public void updateArmour(String armour) {
        CharInfo.set(18, armour);
    }

    public void updateShield(String shield) {
        CharInfo.set(19, shield);
    }

    // Method to retrieve the character's current position
    public int[] getCharInfo() {
        int[] position = new int[3];
        position[0] = Integer.parseInt(CharInfo.get(22)); // X position
        position[1] = Integer.parseInt(CharInfo.get(23)); // Y position
        position[2] = Integer.parseInt(CharInfo.get(24)); // Z position
        return position;
    }

    // Method to update the character's position
    public void updatePosition(int targetX, int targetY, int targetZ) {
        CharInfo.set(22, String.valueOf(targetX)); // Update X position
        CharInfo.set(23, String.valueOf(targetY)); // Update Y position
        CharInfo.set(24, String.valueOf(targetZ)); // Update Z position
    }


public void addToInventory(String item) {
    if (!CharInventory.contains(item)) {
        CharInventory.add(item);
        System.out.println(item + " has been added to the inventory.");
    } else {
        System.out.println(item + " is already in the inventory.");
    }
}



public boolean removeFromInventory(String item) {
    if (CharInventory.contains(item)) {
        CharInventory.remove(item);
        return true; // Successfully removed the item
    }
    return false; // Item not found in inventory
}


    // Placeholder methods for resource management

public boolean removeGold(int amount) {
    int currentGold = Integer.parseInt(CharInfo.get(12));
    if (currentGold >= amount) {
        CharInfo.set(12, String.valueOf(currentGold - amount));
        return true; // Successfully removed gold
    }
    return false; // Not enough gold to remove
}



public void addGold(int amount) {
    int currentGold = Integer.parseInt(CharInfo.get(12));
    CharInfo.set(12, String.valueOf(currentGold + amount));
}



public int getGold() {
    return Integer.parseInt(CharInfo.get(12));
}



public void addFood(int amount) {
    int currentFood = Integer.parseInt(CharInfo.get(13));
    CharInfo.set(13, String.valueOf(currentFood + amount));
}


    public boolean removeFood(int amount) {
        int currentFood = Integer.parseInt(CharInfo.get(13));
        if (currentFood >= amount) {
            CharInfo.set(13, String.valueOf(currentFood - amount));
            return true; // Successfully removed food
        }
        return false; // Not enough food to remove
    }


public int getFood() {
    return Integer.parseInt(CharInfo.get(13));
}



public int getWater() {
    return Integer.parseInt(CharInfo.get(14));
}



public int getTorches() {
    return Integer.parseInt(CharInfo.get(15));
}


    /**
     * Removes a specific effect from the character.
     *
     * @param effect The name of the effect to be removed.
     */
    public void removeEffect(String effect) {
        if (activeEffects.contains(effect)) {
            activeEffects.remove(effect);
            System.out.println(effect + " has been removed.");
        } else {
            System.out.println(effect + " is not active.");
        }
    }

    /**
     * Adds an effect to the character.
     *
     * @param effect The name of the effect to be added.
     * @return
     */
    /**
     * Adds an effect to the character.
     *
     * @param effect The name of the effect to be added.
     * @return A list of active effects.
     */
    public List<String> addEffect(String effect) {
        if (!activeEffects.contains(effect)) {
            activeEffects.add(effect);
            System.out.println(effect + " has been added.");
        } else {
            System.out.println(effect + " is already active.");
        }
        return activeEffects;
    }

        /**
         * Gets the list of active effects.
         *
         * @return A list of active effects.
         */
        public List<String> getActiveEffects() {
            return activeEffects;
        }

        public int getAgility() {
            return Integer.parseInt(CharInfo.get(11));
        }

        public int getStrength() {
            return Integer.parseInt(CharInfo.get(8));
        }

		public int getHP() {

			return Integer.parseInt(CharInfo.get(4));
		}

		public void setHP(int hp) {
		    CharInfo.set(4, String.valueOf(hp));
		}


		public int getWeaponDamage() {
		    int strength = getStrength(); // Retrieve character's strength
		    String weapon = CharInfo.get(17); // Retrieve equipped weapon
		    int baseDamage = weapon != null && !weapon.isEmpty() ? Integer.parseInt(weapon) : 0; // Parse weapon damage
		    Random random = new Random();
		    int randomFactor = random.nextInt(5) + 1; // Random factor between 1 and 5
		    return baseDamage + (int) (strength * 1.2) + randomFactor; // Total weapon damage
		}


		// src/DungeonoftheBrutalKing/Charecter.java
		public Spells getSpellByName(String selectedSpell) {
		    for (String spellName : SpellsLearned) {
		        if (spellName.equalsIgnoreCase(selectedSpell)) {
		            return SpellList.getSpells(spellName); // Retrieve the spell from SpellList
		        }
		    }
		    return null; // Return null if the spell is not found
		}



		// src/DungeonoftheBrutalKing/Charecter.java
		public String[] getKnownSpells() {
		    return SpellsLearned.toArray(new String[0]); // Convert the list to an array
		}
		
	    public List<Quest> getActiveQuests() {
	        return activeQuests;
	    }

	    public void addActiveQuest(Quest quest) {
	        if (!activeQuests.contains(quest)) {
	            activeQuests.add(quest);
	        }
	    }

	    public boolean removeActiveQuest(Quest quest) {
	        return activeQuests.remove(quest);
	    }


    /**
     * Removes a specific effect from the character.
     * This method is a placeholder and should be implemented to handle
     * the removal of status effects or buffs/debuffs applied to the character.
     *
     * @param effect The name of the effect to be removed.
     */


}
