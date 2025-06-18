package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

public class Singleton
{
	public static final String CharInfo = null;
	private static Singleton instance;
    private int[] charInfo = new int[25]; // Example: [x, y, z, ..., other indices]
    private static TimeClock timeClock = TimeClock.Singleton(); // Initialize the singleton instance

    Singleton() {
        // Initialize charInfo or other necessary fields
        charInfo[0] = 0; // x
        charInfo[1] = 0; // y
        charInfo[2] = 0; // z
        // Initialize other indices as needed
    }

    public static Charecter myCharSingleton()
	{
		return myChar;
	}

//    public static Singleton myCharSingleton() {
//        if (myChar == null) {
//            myChar = new Singleton();
//        }
//        return instance;
//    }

    public int getCharInfo(int index) {
        return charInfo[index];
    }

	private static Charecter myChar = Charecter.Singleton();

	private static WeaponManager myWeapon = WeaponManager.Singleton();

	private static ArmourManager myArmour = ArmourManager.Singleton();
	private static ShieldManager myShields = ShieldManager.Singleton();
	private static Spells mySpells = Spells.Singleton();
	private static Enemies myMonsters = Enemies.Singleton();


	private static List<ArmourManager> armourList = addArmour();
	private static List<WeaponManager> weaponList = addWeapon();
	private static List<Spells> spellList = addSpells();
	private static List<ShieldManager> shieldList = addShields();

	//Singletons

	private static List<ArmourManager> addArmour()
	{
		armourList = new ArrayList<>();

	//	  armourList.add(new Skin());
		//  armourList.add(new Cloth());
	//	  armourList.add(new Leather());
	//	  armourList.add(new StuddedLeather());
	//	  armourList.add(new Chainmail());
	//	  armourList.add(new BreastPlate());
	//	  armourList.add(new Plate());

		return armourList;

	}

	private static List<ShieldManager> addShields() {

		shieldList = new ArrayList<>();

	//	shieldList.add(new Wooden_Shield());
	//	shieldList.add(new Small_Shield());
	//    shieldList.add(new Spiked_Shield());
	//	shieldList.add(new Magical_Shield());
	//	shieldList.add(new Magical_Small_Shield());
	//	shieldList.add(new Magical_Spiked_Shield());


		return shieldList;
	}



	  private static List<Spells> addSpells()
	  {
		  spellList = new ArrayList<>();
	 /*
	 * spellList.add(new ARTDCold_Blast()); spellList.add(new ARTDConjure_Food());
	 * spellList.add(new ARTDFireball()); spellList.add(new ARTDHeal());
	 * spellList.add(new ARTDLight()); spellList.add(new ARTDLocation());
	 * spellList.add(new ARTDShield()); spellList.add(new ARTDRandomStat());
	 * spellList.add(new ARTDPort());
	 *
	 *
	 */
	  return spellList;

	  }


	private static List<WeaponManager> addWeapon()
	{
		weaponList = new ArrayList<>();

	//	weaponList.add(new Hand());
	//	weaponList.add(new Daggger());
	//	weaponList.add(new Stilleto());
//		weaponList.add(new Dart());
//		weaponList.add(new Sling());
//		weaponList.add(new ShortSword());
//		weaponList.add(new Shortbow());
//		weaponList.add(new WarNet());
	//	weaponList.add(new Flail());
	//	weaponList.add(new Sword());
	//	weaponList.add(new Whip());
	//	weaponList.add(new Crossbow());
	//	weaponList.add(new LongSword());
	//	weaponList.add(new Longbow());
	//	weaponList.add(new Javelin());
	//	weaponList.add(new BattleAxe());
	//	weaponList.add(new BattleHammer());


		return weaponList;

	}

	public static List<ArmourManager> armourList()
	{
		return armourList;
	}

	public static ArmourManager myArmourSingleton()
	{
		return myArmour;
	}

	public static ShieldManager myShieldSingleton()
	{
		return myShields;
	}

	public static Spells spellListSingleton()
	{
		return mySpells;
	}

//	public static Weapons myWeaponSingleton()
//	{
//		return myWeapon;
//	}


	  public static List<Spells> spellList() { return spellList; }


	public static List<WeaponManager> weaponsList()
	{
		return weaponList;
	}

	 public void removeEffect(String effect) {
	        // Logic to remove the effect from the character
	    }

	 public static TimeClock Singleton() {
		    return timeClock;
		}

}
