package Spells;

import DungeonoftheBrutalKing.Charecter;

public abstract class Light implements Spells{

	 private static Charecter myChar = Charecter.Singleton();

	public Light()
	{
		super();

		String name = "Light";
		int requiredint = 30;
		int requiredwis = 30;

		String charintelligence = Charecter.Singleton().CharInfo.get(8).toString();
		String charwisdom = Charecter.Singleton().CharInfo.get(9).toString();

	}

}
