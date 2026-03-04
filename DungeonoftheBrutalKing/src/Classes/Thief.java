
// src/Classes/Thief.java
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Thief extends Class {

    private static Charecter myChar = Charecter.getInstance();

    int sta; // Stamina
    int chr; // Charisma
    int str; // Strength
    int inti; // Intelligence
    int wis; // Wisdom
    int agi; // Agility

    public static String charClass = "Thief";
    public static String ClassDescription;
    public static String ThiefImage;

    public Thief() {
        int Herolevel = myChar.getLevel();
        charClass = "Thief";
        ThiefImage = "Thief.webp";
    }

    public static String ClassDescription() {
        return ClassDescription = "A " + Thief.charClass + " is a nimble opportunist who excels at stealth, tricks, and precision.\n"
                + Thief.charClass + " are a playable class focused on agility, evasion, and striking weak points.\n"
                + "\n Agility (AGI) and Charisma (CHA) are important skills for a " + Thief.charClass;
    }
}
