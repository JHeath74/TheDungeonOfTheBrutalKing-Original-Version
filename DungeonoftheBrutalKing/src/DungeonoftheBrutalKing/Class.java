package DungeonoftheBrutalKing;

public abstract class Class {

	public static final String[] toonclassarray = {"Paladin", "Wizard", "Cleric", "Rogue", "Hunter", "Warrior", "Bard"};

	/*
	 * public abstract double Heal(); // Heal yourself
	 * public abstract double Cold_Blast(); // Cast a blast of cold to hurt a monster
	 * public abstract double Conjure_Food(); // provide 1-3 food to keep you feed.
	 * public abstract double Fire_Ball(); // Cast a fireball to hurt a monster
	 * public abstract double Light(); // Provide light for a short time
	 * public abstract double Location(); // Find your location in the game
	 * public abstract double Shield(); // Increases your protection for a short time
	 * public abstract double RandomStat(); //Increases a random Stat for a short time
	 * public abstract double Port(); // Port to the Dungeon Entrance
	 */

	public static String ClassDescription;
	int HeroLevel;
	String charClass;

	public Class()
	{
		String ClassDescription;
		HeroLevel = 0;
		charClass = "";

	}

	protected static Class forName(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDeclaredConstructor() {
		// TODO Auto-generated method stub
		return null;
	}
}
