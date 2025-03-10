package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

import Armour.BreastPlate;
import Armour.Chainmail;
import Armour.Cloth;
import Armour.Leather;
import Armour.Plate;
import Armour.Skin;
import Armour.StuddedLeather;
import Monsters.Rats;
import Monsters.Skeletons;
import Monsters.Spiders;
import Shields.Magical_Shield;
import Shields.Magical_Small_Shield;
import Shields.Magical_Spiked_Shield;
import Shields.Small_Shield;
import Shields.Spiked_Shield;
import Shields.Wooden_Shield;
import Weapons.BattleAxe;
import Weapons.BattleHammer;
import Weapons.Crossbow;
import Weapons.Daggger;
import Weapons.Dart;
import Weapons.Flail;
import Weapons.Hand;
import Weapons.Javelin;
import Weapons.LongSword;
import Weapons.Longbow;
import Weapons.ShortSword;
import Weapons.Shortbow;
import Weapons.Sling;
import Weapons.Stilleto;
import Weapons.Sword;
import Weapons.WarNet;
import Weapons.Whip;

public class Singleton
{

	private static Charecter myChar = Charecter.Singleton();

	private static Weapons myWeapon = Weapons.Singleton();
	private static Armour myArmour = Armour.Singleton();
	private static Shields myShields = Shields.Singleton();
	private static Spells mySpells = Spells.Singleton();
	private static Enemies myMonsters = Enemies.Singleton();


	private static List<Armour> armourList = addArmour();
	private static List<Weapons> weaponList = addWeapon();
	private static List<Spells> spellList = addSpells();
	private static List<Shields> shieldList = addShields();

	//Singletons

	private static List<Armour> addArmour()
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

	private static List<Shields> addShields() {

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


	private static List<Weapons> addWeapon()
	{
		weaponList = new ArrayList<>();

	//	weaponList.add(new ARTDHand());
	//	weaponList.add(new ARTDDaggger());
	//	weaponList.add(new ARTDStilleto());
//		weaponList.add(new ARTDDart());
//		weaponList.add(new ARTDSling());
//		weaponList.add(new ARTDShortSword());
//		weaponList.add(new ARTDShortbow());
//		weaponList.add(new ARTDWarNet());
	//	weaponList.add(new ARTDFlail());
	//	weaponList.add(new ARTDSword());
	//	weaponList.add(new ARTDWhip());
	//	weaponList.add(new ARTDCrossbow());
	//	weaponList.add(new ARTDLongSword());
	//	weaponList.add(new ARTDLongbow());
	//	weaponList.add(new ARTDJavelin());
	//	weaponList.add(new ARTDBattleAxe());
	//	weaponList.add(new ARTDBattleHammer());


		return weaponList;

	}

	public static List<Armour> armourList()
	{
		return armourList;
	}

	public static Armour myArmourSingleton()
	{
		return myArmour;
	}


	public static Charecter myCharSingleton()
	{
		return myChar;
	}





	public static Spells spellListSingleton()
	{
		return mySpells;
	}

	public static Weapons myWeaponSingleton()
	{
		return myWeapon;
	}


	  public static List<Spells> spellList() { return spellList; }


	public static List<Weapons> weaponsList()
	{
		return weaponList;
	}

}
