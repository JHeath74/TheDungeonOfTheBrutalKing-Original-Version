
package DungeonoftheBrutalKing.Weapon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import DungeonoftheBrutalKing.Character;

/**
 * WeaponFactory: register weapons by name with a factory function.
 * The factory function receives (Character, String effect) and returns a WeaponManager instance or null.
 */
public final class WeaponFactory {
    private static final Map<String, BiFunction<Character, String, WeaponManager>> registry = new ConcurrentHashMap<>();

    public static void register(String name, BiFunction<Character, String, WeaponManager> creator) {
        if (name == null || name.isBlank() || creator == null) return;
        registry.put(name.toLowerCase(), creator);
    }

    public static WeaponManager create(String name, Character owner, String effect) {
        if (name == null) return null;
        BiFunction<Character, String, WeaponManager> f = registry.get(name.toLowerCase());
        if (f == null) return null;
        try { return f.apply(owner, effect); } catch (Exception e) { return null; }
    }

    public static boolean isRegistered(String name) {
        return name != null && registry.containsKey(name.toLowerCase());
    }
}
