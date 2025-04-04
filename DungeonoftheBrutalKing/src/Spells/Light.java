package Spells;

import DungeonoftheBrutalKing.Spells;

public class Light extends Spells{

	public Light()
	{
		String name = "Light";
		int requiredint = 30;
		int requiredwis = 30;
		String charintelligence = myChar.myCharSingleton().CharInfo.get(8).toString();
		String charwisdom = myChar.myCharSingleton().CharInfo.get(9).toString();

	}

}
