
// src/DungeonoftheBrutalKing/Class.java
package Charecters;

// Abstract class representing a character class in the game
public abstract class Class {

    // Array of available character classes
    public static final String[] toonclassarray = {"Paladin", "Wizard", "Cleric", "Rogue", "Hunter", "Warrior", "Bard"};

    // Description of the class (can be overridden by subclasses)
    public static String ClassDescription;

    // Level of the hero
    int HeroLevel;

    // Name of the character class
    protected String charClass;

    // Constructor to initialize default values
    public Class() {
        String ClassDescription; // Local variable for class description
        HeroLevel = 0;           // Default hero level
        setCharClass("");          // Default character class name
    }

    // Static method to retrieve a class instance by name
    // (To be implemented in the future)
    public static Class forName(String className) {
        // TODO: Implement logic to return a class instance based on the name
        return null;
    }

    // Placeholder method to retrieve a declared constructor
    // (To be implemented in the future)
    public Object getDeclaredConstructor() {
        // TODO: Implement logic to return the declared constructor
        return null;
    }

	public String getCharClass() {
		return charClass;
	}

	public void setCharClass(String charClass) {
		this.charClass = charClass;
	}
}
