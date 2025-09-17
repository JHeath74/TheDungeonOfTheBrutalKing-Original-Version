
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

import Armour.ArmourManager;
import Enemies.Enemies;
import Spells.Spells;
import Weapon.WeaponManager;

public class Singleton
{
    public static final String CharInfo = null;
    private static Singleton instance;
    private int[] charInfo = new int[25]; // Example: [x, y, z, ..., other indices]
    private static TimeClock timeClock = TimeClock.Singleton(); // Initialize the singleton instance

    public Singleton() {
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

    public int getCharInfo(int index) {
        return charInfo[index];
    }

    private static Charecter myChar = Charecter.Singleton();

    private static WeaponManager myWeapon = WeaponManager.Singleton();

    private static ArmourManager myArmour = ArmourManager.Singleton();
    private static ShieldManager myShields = ShieldManager.Singleton();
    private static Spells mySpells = Spells.Singleton();
    // Removed invalid Enemies.Singleton() usage
    // private static Enemies myMonsters = Enemies.Singleton();

    private static List<ArmourManager> armourList = addArmour();
    private static List<WeaponManager> weaponList = addWeapon();
    private static List<Spells> spellList = addSpells();
    private static List<ShieldManager> shieldList = addShields();

    //Singletons

    private static List<ArmourManager> addArmour()
    {
        armourList = new ArrayList<>();
        return armourList;
    }

    private static List<ShieldManager> addShields() {
        shieldList = new ArrayList<>();
        return shieldList;
    }

    private static List<Spells> addSpells()
    {
        spellList = new ArrayList<>();
        return spellList;
    }

    private static List<WeaponManager> addWeapon()
    {
        weaponList = new ArrayList<>();
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
