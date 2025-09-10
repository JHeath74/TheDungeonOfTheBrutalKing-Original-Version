
// src/DungeonoftheBrutalKing/Charecter.java
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Quests.Quest;

//CharInfo ArrayList:
//Index 0: Character's name (String)
//Index 1: Character's class or type (String)
//Index 2: Character's Race (String)
//Index 3: Character's level (int as String)
//Index 4: Character's experience points (int as String)
//Index 5: Character's health points (HP) (int as String)
//Index 6: Character's magic points (MP) (int as String)
//Index 7: Character's stamina (int as String)
//Index 8: Character's charisma (int as String)
//Index 9: Character's strength (int as String)
//Index 10: Character's intelligence (int as String)
//Index 11: Character's wisdom (int as String)
//Index 12: Character's agility (int as String)
//Index 13: Character's gold (int as String)
//Index 14: Character's food (int as String)
//Index 15: Character's water (int as String)
//Index 16: Character's torches (int as String)
//Index 17: Character's gems (int as String)
//Index 18: Character's equipped weapon (String)
//Index 19: Character's equipped armor (String)
//Index 20: Character's equipped shield (String)
//Index 21: Character's alignment (int as String)
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


//The CharInventory ArrayList is used to store the items that the character possesses in their inventory.
//Each element in the list represents an item as a String.
//Example:
//Index 0: "Sword"
//Index 1: "Potion"
//Index 2: "Shield"
//The list allows for inventory management, such as adding, removing, or checking items during gameplay.

//The activeQuests ArrayList stores the quests that the character is currently undertaking.
//Each element in the list represents a Quest object, which contains details about the quest.
//Example:
//Index 0: Quest object for "Retrieve the Ancient Artifact"
//Index 1: Quest object for "Defeat the Goblin King"
//The list allows for tracking and managing active quests, such as adding new quests,
//removing completed quests, or checking the status of ongoing quests.


public class Charecter {

	public ArrayList<String> getSpellsLearned() {
		return SpellsLearned;
	}

	public void setSpellsLearned(ArrayList<String> spellsLearned) {
		SpellsLearned = spellsLearned;
	}

	public ArrayList<String> getCharInventory() {
		return CharInventory;
	}

	public void setCharInventory(ArrayList<String> charInventory) {
		CharInventory = charInventory;
	}

	public static Charecter getSingle_instance_myChar() {
		return single_instance_myChar;
	}

	public String getName() {
	    return CharInfo != null && CharInfo.size() > 0 ? CharInfo.get(0) : "";
	}

	public void setName(String name) {
	    if (CharInfo != null) {
	        if (CharInfo.size() > 0) {
	            CharInfo.set(0, name);
	        } else {
	            CharInfo.add(name);
	        }
	    }
	}

	public void setEffectManager(EffectManager effectManager) {
		this.effectManager = effectManager;
	}

	public void setActiveEffects(List<String> activeEffects) {
		this.activeEffects = activeEffects;
	}

	public void setCharInfo(ArrayList<String> charInfo) {
		CharInfo = charInfo;
	}

	public void setActiveQuests(List<Quest> activeQuests) {
		this.activeQuests = activeQuests;
	}
	// Singleton instance of the Charecter class
	private static Charecter single_instance_myChar;
	// Static method to create or retrieve the Singleton instance of the Charecter
	// class
	public static Charecter Singleton() {
		if (single_instance_myChar == null) {
			single_instance_myChar = new Charecter();
		}
		return single_instance_myChar;
	}
	private static String name;

	private EffectManager effectManager = new EffectManager();

	private List<String> activeEffects = new ArrayList<>();

	// List to store character information
	public ArrayList<String> CharInfo = new ArrayList<>();

	// List to store spells learned by the character
	public ArrayList<String> SpellsLearned = new ArrayList<>();

	// List to store guild spells
    public static ArrayList<String> GuildSpells = new ArrayList<>();

	// List to store the character's inventory
	public ArrayList<String> CharInventory = new ArrayList<>();

	// List to store active quests for the character
	private List<Quest> activeQuests = new ArrayList<>();

	public Charecter() {
		Charecter.name = name;
	}

	public void addActiveQuest(Quest quest) {
		if (!activeQuests.contains(quest)) {
			activeQuests.add(quest);
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
			MainGameScreen.appendToMessageTextPane(effect + " has been added.");
		} else {
			MainGameScreen.appendToMessageTextPane(effect + " is already active.");
		}
		return activeEffects;
	}



	public void addToInventory(String item) {
		if (!CharInventory.contains(item)) {
			CharInventory.add(item);
			MainGameScreen.appendToMessageTextPane(item + " has been added to the inventory.");
		} else {
			MainGameScreen.appendToMessageTextPane(item + " is already in the inventory.");
		}
	}

	private void checkLevelUp() {
		int currentLevel = Integer.parseInt(CharInfo.get(2)); // Get current level
		int currentExperience = Integer.parseInt(CharInfo.get(3)); // Get current experience
		int experienceRequired = getExperienceRequiredForLevel(currentLevel + 1); // Experience needed for next level

		if (currentExperience >= experienceRequired) {
			setLevel(currentLevel + 1); // Level up the character
			MainGameScreen.appendToMessageTextPane("Congratulations! You leveled up to level " + (currentLevel + 1));
		}
	}

	/**
	 * Gets the list of active effects.
	 *
	 * @return A list of active effects.
	 */
	public List<String> getActiveEffects() {
		return activeEffects;
	}

	public List<Quest> getActiveQuests() {
		return activeQuests;
	}


	public EffectManager getEffectManager() {
		return effectManager;
	}

	public int getExperienceRequiredForLevel(int level) {
		int baseExperience = 1000; // Starting experience for level 1
		double scalingFactor = 1.5; // Experience multiplier for each level
		return (int) (baseExperience * Math.pow(scalingFactor, level - 1));
	}

	// src/DungeonoftheBrutalKing/Charecter.java
	public String[] getKnownSpells() {
		return SpellsLearned.toArray(new String[0]); // Convert the list to an array
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





	public int getWeaponDamage() {
		int strength = getStrength(); // Retrieve character's strength
		String weapon = CharInfo.get(17); // Retrieve equipped weapon
		int baseDamage = weapon != null && !weapon.isEmpty() ? Integer.parseInt(weapon) : 0; // Parse weapon damage
		Random random = new Random();
		int randomFactor = random.nextInt(5) + 1; // Random factor between 1 and 5
		return baseDamage + (int) (strength * 1.2) + randomFactor; // Total weapon damage
	}

	public boolean removeActiveQuest(Quest quest) {
		return activeQuests.remove(quest);
	}

	/**
	 * Removes a specific effect from the character.
	 *
	 * @param effect The name of the effect to be removed.
	 */
	public void removeEffect(String effect) {
		if (activeEffects.contains(effect)) {
			activeEffects.remove(effect);
			MainGameScreen.appendToMessageTextPane(effect + " has been removed.");
		} else {
			MainGameScreen.appendToMessageTextPane(effect + " is not active.");
		}
	}

	public boolean removeFood(int amount) {
		int currentFood = Integer.parseInt(CharInfo.get(13));
		if (currentFood >= amount) {
			CharInfo.set(13, String.valueOf(currentFood - amount));
			return true; // Successfully removed food
		}
		return false; // Not enough food to remove
	}

	// Placeholder methods for resource management

	public boolean removeFromInventory(String item) {
		if (CharInventory.contains(item)) {
			CharInventory.remove(item);
			return true; // Successfully removed the item
		}
		return false; // Item not found in inventory
	}

	public boolean removeGold(int amount) {
		int currentGold = Integer.parseInt(CharInfo.get(12));
		if (currentGold >= amount) {
			CharInfo.set(12, String.valueOf(currentGold - amount));
			return true; // Successfully removed gold
		}
		return false; // Not enough gold to remove
	}

	/**
	 * Removes a specific effect from the character. This method is a placeholder
	 * and should be implemented to handle the removal of status effects or
	 * buffs/debuffs applied to the character.
	 *
	 * @param effect The name of the effect to be removed.
	 */

	// src/DungeonoftheBrutalKing/Charecter.java
	public void rewardExperience(int experiencePoints) {
		int currentExperience = Integer.parseInt(CharInfo.get(3)); // Get current experience
		setExperience(currentExperience + experiencePoints); // Add experience points
		MainGameScreen.appendToMessageTextPane("You gained " + experiencePoints + " experience points!");

		checkLevelUp(); // Check if the character qualifies for a level-up
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

	// Index 2 – Race & Level
	public String getRace() {
	    return CharInfo.get(2);
	}
	public void setRace(String race) {
	    CharInfo.set(2, race);
	}
	// Index 3 – Level
	public String getLevel() {
	    return CharInfo.get(3);
	}
	public void setLevel(int level) {
	    CharInfo.set(3, String.valueOf(level));
	}

	// Index 4 – Experience
	public String getExperience() {
	    return CharInfo.get(4);
	}
	public void setExperience(int experience) {
	    CharInfo.set(4, String.valueOf(experience));
	}

	// Index 5 – Health Points (HP)
	// After:
	public int getHitPoints() {
	    if (CharInfo != null && CharInfo.size() > 5) {
	        try {
	            return Integer.parseInt(CharInfo.get(5));
	        } catch (NumberFormatException e) {
	            return 0; // fallback if not a number
	        }
	    }
	    return 0; // fallback if missing
	}
	
	public void setHitPoints(int hp) {
	    CharInfo.set(5, String.valueOf(hp));
	}

	// Index 6 – Action Points
	public int getActionPoints() {
	    return Integer.parseInt(CharInfo.get(6)); // Retrieve action points
	}

	public void setActionPoints(int actionPoints) {
	    CharInfo.set(6, String.valueOf(actionPoints)); // Update action points
	}


	// Index 6 – Magic Points (MP)
	public int getMagicPoints() {
	    return Integer.parseInt(CharInfo.get(6));
	}
	public void setMagicPoints(int magicPoints) {
	    CharInfo.set(6, String.valueOf(magicPoints));
	}

	// Index 7 – Stamina
	public int getStamina() {
	    return Integer.parseInt(CharInfo.get(7));
	}
	public void setStamina(int stamina) {
	    CharInfo.set(7, String.valueOf(stamina));
	}

	// Index 8 – Charisma
	public int getCharisma() {
	    return Integer.parseInt(CharInfo.get(8));
	}
	public void setCharisma(int charisma) {
	    CharInfo.set(8, String.valueOf(charisma));
	}

	// Index 9 – Strength
	public int getStrength() {
	    return Integer.parseInt(CharInfo.get(9));
	}
	public void setStrength(int strength) {
	    CharInfo.set(9, String.valueOf(strength));
	}

	// Index 10 – Intelligence
	public int getIntelligence() {
	    return Integer.parseInt(CharInfo.get(10));
	}
	public void setIntelligence(int intelligence) {
	    CharInfo.set(10, String.valueOf(intelligence));
	}

	// Index 11 – Wisdom
	public int getWisdom() {
	    return Integer.parseInt(CharInfo.get(11));
	}
	public void setWisdom(int wisdom) {
	    CharInfo.set(11, String.valueOf(wisdom));
	}

	// Index 12 – Agility
	public int getAgility() {
	    return Integer.parseInt(CharInfo.get(12));
	}
	public void setAgility(int agility) {
	    CharInfo.set(12, String.valueOf(agility));
	}

	// Index 13 – Gold
	public int getGold() {
	    return Integer.parseInt(CharInfo.get(13));
	}
	public void setGold(int gold) {
	    CharInfo.set(13, String.valueOf(gold));
	}

	// Index 14 – Food
	public int getFood() {
	    return Integer.parseInt(CharInfo.get(14));
	}
	public void setFood(int food) {
	    CharInfo.set(14, String.valueOf(food));
	}

	// Index 15 – Water
	public int getWater() {
	    return Integer.parseInt(CharInfo.get(15));
	}
	public void setWater(int water) {
	    CharInfo.set(15, String.valueOf(water));
	}

	// Index 16 – Torches
	public int getTorches() {
	    return Integer.parseInt(CharInfo.get(16));
	}
	public void setTorches(int torches) {
	    CharInfo.set(16, String.valueOf(torches));
	}

	// Index 17 – Gems
	public int getGems() {
	    return Integer.parseInt(CharInfo.get(17));
	}
	public void setGems(int gems) {
	    CharInfo.set(17, String.valueOf(gems));
	}

	// Index 18 – Equipped Weapon
	public String getWeapon() {
	    return CharInfo.get(18);
	}
	public void setWeapon(String weapon) {
	    CharInfo.set(18, weapon);
	}

	// Index 19 – Equipped Armor
	public String getArmour() {
	    return CharInfo.get(19);
	}
	public void setArmour(String armour) {
	    CharInfo.set(19, armour);
	}

	// Index 20 – Equipped Shield
	public String getShield() {
	    return CharInfo.get(20);
	}
	public void setShield(String shield) {
	    CharInfo.set(20, shield);
	}

	// Index 21 – Alignment
	public int getAlignment() {
	    return Integer.parseInt(CharInfo.get(21));
	}
	public void setAlignment(int alignment) {
	    CharInfo.set(21, String.valueOf(alignment));
	}

	// Indexes 23–25 – Position
	public void setPosition(int targetX, int targetY, int targetZ) {
		// Print index and value for debugging
	    for (int i = 0; i < CharInfo.size(); i++) {
	        System.out.println("Index " + i + ": " + CharInfo.get(i));
	    }

		
	    CharInfo.set(22, String.valueOf(targetX));
	    CharInfo.set(23, String.valueOf(targetY));
	    CharInfo.set(24, String.valueOf(targetZ));
	}

	public void getPosition(int[] position) {
		position[0] = Integer.parseInt(CharInfo.get(22)); // X position
		position[1] = Integer.parseInt(CharInfo.get(23)); // Y position
		position[2] = Integer.parseInt(CharInfo.get(24)); // Z position
	}

	public double getX() {
		return Double.parseDouble(CharInfo.get(22));
	}
	
	public double getY() {
	    return Double.parseDouble(CharInfo.get(23));
	}

	public double getZ() {
		return Double.parseDouble(CharInfo.get(24));
	}
	
	public double getDirection() {
		return Double.parseDouble(CharInfo.get(25));
	}













}
