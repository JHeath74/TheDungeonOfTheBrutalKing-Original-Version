package DungeonoftheBrutalKing.Weapon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import DungeonoftheBrutalKing.Charecter;

/**
 * Minimal WeaponFactory: register weapons by name with a factory function.
 * The factory function receives (Charecter, String effect) and returns Object (weapon instance) or null.
 */
public final class WeaponFactory {
    private static final Map<String, BiFunction<Charecter,String,Object>> registry = new ConcurrentHashMap<>();

    public static void register(String name, BiFunction<Charecter,String,Object> creator) {
        if (name == null || name.isBlank() || creator == null) return;
        registry.put(name.toLowerCase(), creator);
    }

    public static Object create(String name, Charecter owner, String effect) {
        if (name == null) return null;
        BiFunction<Charecter,String,Object> f = registry.get(name.toLowerCase());
        if (f == null) return null;
        try { return f.apply(owner, effect); } catch (Exception e) { return null; }
    }

    public static boolean isRegistered(String name) { return name != null && registry.containsKey(name.toLowerCase()); }
}
