
// src/DungeonoftheBrutalKing/Charecter.java
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

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

    // Placeholder methods for inventory management
    public void addToInventory(String selectedItem) {
        // TODO: Implement inventory addition logic
    }

    public void removeFromInventory(String selectedItem) {
        // TODO: Implement inventory removal logic
    }

    // Placeholder methods for resource management
    public boolean removeGold(int amount) {
        // TODO: Implement gold removal logic
        return false;
    }

    public void addGold(int amount) {
        // TODO: Implement gold addition logic
    }

    public int getGold() {
        // TODO: Implement logic to retrieve current gold
        return 0;
    }

    public void addFood(int amount) {
        // TODO: Implement food addition logic
    }

    public boolean removeFood(int amount) {
        // TODO: Implement food removal logic
        return false;
    }

    public int getFood() {
        // TODO: Implement logic to retrieve current food
        return 0;
    }

    public int getWater() {
        // TODO: Implement logic to retrieve current water
        return 0;
    }

    public int getTorches() {
        // TODO: Implement logic to retrieve current torches
        return 0;
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

    /**
     * Removes a specific effect from the character.
     * This method is a placeholder and should be implemented to handle
     * the removal of status effects or buffs/debuffs applied to the character.
     *
     * @param effect The name of the effect to be removed.
     */
    

}
