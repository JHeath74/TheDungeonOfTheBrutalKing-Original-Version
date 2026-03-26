
package Spells;

import java.util.HashMap;
import java.util.Map;

public class GuildSpellsRegistry {
    private final Map<String, SpellsManager> guildManagers = new HashMap<>();

    public SpellsManager getOrCreateManager(String guildId) {
        return guildManagers.computeIfAbsent(guildId, id -> new SpellsManager());
    }

    public SpellsManager getManager(String guildId) {
        return guildManagers.get(guildId);
    }
}
