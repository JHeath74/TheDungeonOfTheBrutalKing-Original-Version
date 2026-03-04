
// src/Classes/Ranger.java
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Ranger extends Class {

    private static Charecter myChar = Charecter.getInstance();

    int sta; //Stamina
    int chr; //Charisma
    int str; //Strength
    int inti; //Intelligence
    int wis; //Wisdom
    int agi; //Agility

    public static String charClass = "Ranger";
    public static String ClassDescription;
    public static String RangerImage;

    public Ranger() {
        int Herolevel = myChar.getLevel();
        charClass = "Ranger";
        RangerImage = "Ranger.webp";
    }

    public static String ClassDescription() {
        return ClassDescription = "A " + Ranger.charClass + " is a skilled hunter and tracker, thriving in the wilds.\n"
                + Ranger.charClass + " are a playable class focused on survival, ranged combat, and precision strikes.\n"
                + "\n Agility (AGI) and Wisdom (WIS) are important skills for a " + Ranger.charClass;
    }
}
