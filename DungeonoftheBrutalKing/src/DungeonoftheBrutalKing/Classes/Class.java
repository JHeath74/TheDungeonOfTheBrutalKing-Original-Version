
package DungeonoftheBrutalKing.Classes;

import DungeonoftheBrutalKing.SharedData.Stat;

/**
 * Abstract base class representing a character class in the game.
 * <p>
 * Subclasses define specific class properties, stat bonuses, and behaviors.
 * Provides common fields and methods for all character classes.
 */
public abstract class Class {

    /**
     * Array of available character class names.
     * TODO: Keep this list in sync with all implemented class subclasses.
     */
    public static final String[] toonclassarray = {
        "Mage", "Ministrel", "Ranger", "Thief", "Paladin", "Wizard",
        "Cleric", "Rogue", "Hunter", "Warrior", "Bard"
    };

    /** Description of the class (can be overridden by subclasses). */
    protected String classDescription = "";

    /** Level of the hero (set by game logic). */
    protected int heroLevel = 0;

    /** Name of the character class. */
    protected String charClass = "";

    /** Default constructor. */
    public Class() {
        // Defaults are set via field initializers
    }

    /**
     * Returns a class instance by name.
     * Not yet implemented.
     * @param className the name of the class
     * @return instance of the class, or null
     */
    public static Class forName(String className) {
        // TODO: Implement logic to return a class instance based on the name
        throw new UnsupportedOperationException("forName() not implemented yet.");
    }

    /**
     * Placeholder for retrieving a declared constructor.
     * Not yet implemented.
     * @return the declared constructor
     */
    public Object getDeclaredConstructor() {
        // TODO: Implement logic to return the declared constructor
        throw new UnsupportedOperationException("getDeclaredConstructor() not implemented yet.");
    }

    /** @return the name of the character class */
    public String getCharClass() {
        return charClass;
    }

    /** Sets the name of the character class. */
    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    /** @return the hero's level */
    public int getHeroLevel() {
        return heroLevel;
    }

    /** Sets the hero's level. */
    public void setHeroLevel(int heroLevel) {
        this.heroLevel = heroLevel;
    }

    /** @return the class description */
    public String getClassDescription() {
        return classDescription;
    }

    /** Sets the class description. */
    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    /**
     * Returns the image path for this class.
     * Subclasses should override to provide the correct resource path.
     * @return the image path, or null if not set
     */
    public String getImage() {
        // Override in subclasses to return the correct image path (e.g., "/images/Bard.webp")
        return null;
    }

    /** @return true if this class is a magic user */
    public abstract boolean isMagicUser();

    /** @return the primary stat for this class */
    public abstract Stat getPrimaryStat();

    /** @return the secondary stat for this class */
    public abstract Stat getSecondaryStat();
}
