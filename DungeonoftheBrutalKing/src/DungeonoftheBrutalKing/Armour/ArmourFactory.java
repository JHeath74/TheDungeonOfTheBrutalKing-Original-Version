
package DungeonoftheBrutalKing.Armour;

import DungeonoftheBrutalKing.Character;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ArmourFactory {
    private static final Map<String, BiFunction<Character, String, ArmourManager>> registry = new HashMap<>();

    public static void register(String name, BiFunction<Character, String, ArmourManager> creator) {
        registry.put(name.toLowerCase(), creator);
    }

    public static ArmourManager create(String name, Character owner, String effect) {
        BiFunction<Character, String, ArmourManager> creator = registry.get(name.toLowerCase());
        if (creator != null) {
            return creator.apply(owner, effect);
        }
        return null;
    }
}
