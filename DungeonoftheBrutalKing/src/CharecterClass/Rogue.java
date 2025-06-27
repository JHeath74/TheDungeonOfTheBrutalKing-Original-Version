
package CharecterClass;

import DungeonoftheBrutalKing.Charecter;

public class Rogue extends Class {

    int sta; // Stamina
    int chr; // Charisma
    int str; // Strength
    int inti; // Intelligence
    int wis; // Wisdom
    int agi; // Agility

    int Herolevel;
    Charecter myChar = new Charecter();

    public Rogue() {
        charClass = "Rogue"; // Set the character class
        Herolevel = Integer.parseInt(myChar.CharInfo.get(2)); // Get the hero level
    }

    @Override
    public String getCharClass() {
        return charClass; // Return the character class
    }

    public static String ClassDescription() {
        return "As adventurers, " + new Rogue().getCharClass() + " fall on both sides of the law. Some are hardened criminals "
            + "who decide to seek their fortune in treasure hoards, while others enter a life of adventure to escape "
            + "from the law. Others have learned and perfected their skills with the explicit purpose of infiltrating "
            + "ancient ruins and hidden crypts in search of treasure. \n\n"
            + "AGILITY (AGI) followed by Intelligence are important stats for a " + new Rogue().getCharClass();
    }
}
