
package Classes;

import DungeonoftheBrutalKing.Charecter;

public class Rogue extends Class
{

    Charecter myChar = Charecter.Singleton();

    int sta; //Stamina
    int chr; //Charisma
    int str; //Strength
    int inti; //Intelligence
    int wis; //Wisdom
    int agi; //Agility

    int Herolevel = myChar.getLevel();

    public static String charclass = "Rogue";

    public static String ClassDescription;

    public Rogue()
    {
    }

    public static String ClassDescription()
    {
        return ClassDescription = "As adventurers, " + Rogue.charclass + " fall on both sides of the law. Some are hardened criminals "
                + "who decide to seek their fortune in treasure hoards, while others enter a life of adventure to escape "
                + "from the law. Others have learned and perfected their skills with the explicit purpose of infiltrating "
                + "ancient ruins and hidden crypts in search of treasure. \n\n"
                + "AGILITY (AGI) followed by Intelligence are important stats for a " + Rogue.charclass;
    }

    //Spells

}
