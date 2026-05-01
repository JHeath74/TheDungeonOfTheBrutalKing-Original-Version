package DungeonoftheBrutalKing.Weapon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Registry for weapon types, allowing lookup and instantiation by name.
 */
public class WeaponRegistry {

    /** Ensures weapons are registered only once. */
    private static boolean initialized = false;

    /**
     * Registers all weapon types with the WeaponFactory.
     */
    public static void registerAll() {
        if (initialized) return;
        WeaponFactory.register("battleaxe", (owner, effect) -> new BattleAxe(owner, effect));
        WeaponFactory.register("battlehammer", (owner, effect) -> new BattleHammer(owner, effect));
        WeaponFactory.register("club", (owner, effect) -> new Club(owner, effect));
        WeaponFactory.register("crossbow", (owner, effect) -> new Crossbow(owner, effect));
        WeaponFactory.register("dagger", (owner, effect) -> new Dagger(owner, effect));
        WeaponFactory.register("dart", (owner, effect) -> new Dart(owner, effect));
        WeaponFactory.register("flail", (owner, effect) -> new Flail(owner, effect));
        WeaponFactory.register("hand", (owner, effect) -> new Hand(owner, effect));
        WeaponFactory.register("javelin", (owner, effect) -> new Javelin(owner, effect));
        WeaponFactory.register("longbow", (owner, effect) -> new Longbow(owner, effect));
        WeaponFactory.register("longsword", (owner, effect) -> new LongSword(owner, effect));
        WeaponFactory.register("shortbow", (owner, effect) -> new Shortbow(owner, effect));
        WeaponFactory.register("shortsword", (owner, effect) -> new ShortSword(owner, effect));
        WeaponFactory.register("sling", (owner, effect) -> new Sling(owner, effect));
        WeaponFactory.register("stillegto", (owner, effect) -> new Stillegto(owner, effect));
        WeaponFactory.register("sword", (owner, effect) -> new Sword(owner, effect));
        WeaponFactory.register("warnet", (owner, effect) -> new WarNet(owner, effect));
        WeaponFactory.register("whip", (owner, effect) -> new Whip(owner, effect));
        initialized = true;
    }

    /**
     * Returns a new WeaponManager instance for the given weapon name.
     * @param weaponName The name of the weapon to look up
     * @return A new WeaponManager instance, or null if not found
     */
    public static WeaponManager getWeaponByName(String weaponName) {
        registerAll(); // Ensure registry is initialized
        return WeaponFactory.create(weaponName, null, null);
    }
}
