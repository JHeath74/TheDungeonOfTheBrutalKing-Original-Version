package Charecters;

import DungeonoftheBrutalKing.Charecter;

public class Wizard extends Class {

	Charecter myChar = new Charecter();

	int sta; //Stamina
	int chr; //Charisma
	int str; //Strength
	int inti; //Intelligence
	int wis; //Wisdom
	int agi; //Agility

	int Herolevel;

	public static String charClass = "Wizard";

	public static String ClassDescription;

	public Wizard()
	{
		Herolevel = myChar.CharInfo.indexOf(myChar.CharInfo.get(2));

		charClass = "Wizard";



	}

	public static String ClassDescription()
	{
		return ClassDescription = "A " + Wizard.charClass + " is a master of arcane magic,"
			+	"shaping the fabric of reality through sheer intellect and rigorous study."
			+	"With their spellbook as a constant companion, "
			+	"they wield a vast array of powerful spells, excelling in versatility and utility."
			+	Wizard.charClass +  "thrive in roles requiring control, damage, or problem-solving, "
			+	"prowess and boosting their effectiveness in arcane knowledge and skill checks."
			+	"While physically frail, their mental acuity and deep magical expertise make them "
			+	"an indispensable asset to any adventuring party.";

	}

}
