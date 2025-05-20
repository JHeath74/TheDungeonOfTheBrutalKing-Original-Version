package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;

public abstract class Light implements Spells{
	
	 private static Charecter myChar = Charecter.Singleton();

	public Light()
	{
		super();
		
		String name = "Light";
		int requiredint = 30;
		int requiredwis = 30;
	
		String charintelligence = myChar.Singleton().CharInfo.get(8).toString();
		String charwisdom = myChar.Singleton().CharInfo.get(9).toString();

	}

}
