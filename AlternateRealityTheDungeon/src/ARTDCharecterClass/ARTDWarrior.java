package ARTDCharecterClass;

import AlternateRealityTheDungeon.ARTDCharecter;
import AlternateRealityTheDungeon.ARTDClass;

public class ARTDWarrior extends ARTDClass {

	ARTDCharecter myChar = new ARTDCharecter();
	
	int sta; //Stamina
	int chr; //Charisma
	int str; //Strength
	int inti; //Intelligence
	int wis; //Wisdom
	int agi; //Agility
	
	int Herolevel;
	
	public static String charClass;
	
	public static String WarriorClassDescription;
	
	public ARTDWarrior()
	{
		Herolevel = myChar.CharInfo.indexOf(myChar.CharInfo.get(2));
		
		charClass = "Warrior";
		
		WarriorClassDescription = ARTDWarrior.charClass + " share an unparalleled mastery with weapons and armor, and a thorough knowledge \"\r\n"
				+ "					 of the skills of combat. They are well acquainted with death, both meting it out and staring it defiantly \"\r\n"
				+ "					 in the face. " + ARTDWarrior.charClass
				+ "					  share an unparalleled mastery with weapons and armor, and a thorough knowledge of the \"\r\n"
				+ "					 skills of combat. They are well acquainted with death, both meting it out and staring it defiantly in the face.\\n\\n\"\r\n"
				+ "					 Strength (STR) is an important skill for a " + ARTDWarrior.charClass;

		
	}

	@Override
	public double Heal() {
		double Heal = inti;  
		return Heal;
		
	}

	@Override
	public double Cold_Blast() {
		double ColdBlast = inti;
		return ColdBlast;
	}

	@Override
	public double Conjure_Food() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Fire_Ball() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Light() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Location() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Shield() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double RandomStat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Port() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
