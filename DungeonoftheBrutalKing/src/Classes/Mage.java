
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Mage extends Class {

    private static Charecter myChar = Charecter.getInstance();

    int sta; //Stamina
    int chr; //Charisma
    int str; //Strength
    int inti; //Intelligence
    int wis; //Wisdom
    int agi; //Agility

    public static String charClass = "Mage";
    public static String ClassDescription;
    public static String MageImage;

    public Mage() {
        int Herolevel = myChar.getLevel();
        charClass = "Mage";
        MageImage = "Mage.webp";
    }

    public static String ClassDescription() {
        return ClassDescription = "A " + Mage.charClass + " is a wielder of arcane power, studying spells and ancient lore.\n"
                + Mage.charClass + " are a playable class focused on magical damage, utility, and control.\n"
                + "\n Intelligence (INT) and Wisdom (WIS) are important skills for a " + Mage.charClass;
    }
}
