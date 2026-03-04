
// `src/Classes/Ministrel.java`
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Ministrel extends Class {

    private static Charecter myChar = Charecter.getInstance();

    int sta; //Stamina
    int chr; //Charisma
    int str; //Strength
    int inti; //Intelligence
    int wis; //Wisdom
    int agi; //Agility

    public static String charClass = "Ministrel";
    public static String ClassDescription;
    public static String MinistrelImage;

    public Ministrel() {
        int Herolevel = myChar.getLevel();
        charClass = "Ministrel";
        MinistrelImage = "Ministrel.webp";
    }

    public static String ClassDescription() {
        return ClassDescription = "A " + Ministrel.charClass + " is a traveling performer and storyteller, inspiring allies with music.\n"
                + Ministrel.charClass + " are a playable class focused on support, morale, and clever utility.\n"
                + "\n Charisma (CHA) and Wisdom (WIS) are important skills for a " + Ministrel.charClass;
    }
}
