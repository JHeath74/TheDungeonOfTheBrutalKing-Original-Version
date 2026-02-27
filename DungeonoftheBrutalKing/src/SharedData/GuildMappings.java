
package SharedData;

import java.util.EnumMap;
import java.util.Map;

public final class GuildMappings {
    private GuildMappings() {}

    public static final Map<Guild, GuildType> GUILD_TO_TYPE = Map.ofEntries(
            Map.entry(Guild.AURORA_ARCANUM, GuildType.WIZARD),
            Map.entry(Guild.CELESTIAL_ARCANE_ORDER, GuildType.CLERIC),
            Map.entry(Guild.CRIMSON_BLADES, GuildType.WARRIOR),
            Map.entry(Guild.CRIMSON_VEIL_ROGUES, GuildType.ROGUE),
            Map.entry(Guild.DAWNWARD_PALADINS, GuildType.PALADIN),
            Map.entry(Guild.DIRGEWEAVERS_CHORUS, GuildType.BARD),
            Map.entry(Guild.HARMONILIC_LIGHT_ENSEMBLE, GuildType.MINSTREL),
            Map.entry(Guild.NIGHT_SHADE_HUNTERS, GuildType.HUNTER),
            Map.entry(Guild.OBSIDIAN_HEX_COVEN, GuildType.MAGE),
            Map.entry(Guild.OBSIDIAN_SHADOW_SYNDICATE, GuildType.THIEF),
            Map.entry(Guild.SILVERWARD_SENTINELS, GuildType.RANGER),
            Map.entry(Guild.NON_GUILD, null)
    );

    public static final Map<Guild, Alignment> GUILD_TO_ALIGNMENT = Map.ofEntries(
            Map.entry(Guild.AURORA_ARCANUM, Alignment.GOOD),
            Map.entry(Guild.CELESTIAL_ARCANE_ORDER, Alignment.GOOD),
            Map.entry(Guild.CRIMSON_BLADES, Alignment.EVIL),
            Map.entry(Guild.CRIMSON_VEIL_ROGUES, Alignment.EVIL),
            Map.entry(Guild.DAWNWARD_PALADINS, Alignment.GOOD),
            Map.entry(Guild.DIRGEWEAVERS_CHORUS, Alignment.EVIL),
            Map.entry(Guild.HARMONILIC_LIGHT_ENSEMBLE, Alignment.GOOD),
            Map.entry(Guild.NIGHT_SHADE_HUNTERS, Alignment.EVIL),
            Map.entry(Guild.OBSIDIAN_HEX_COVEN, Alignment.EVIL),
            Map.entry(Guild.OBSIDIAN_SHADOW_SYNDICATE, Alignment.EVIL),
            Map.entry(Guild.SILVERWARD_SENTINELS, Alignment.GOOD),
            Map.entry(Guild.NON_GUILD, null)
    );

    public static GuildType typeOf(Guild guild) {
        return GUILD_TO_TYPE.get(guild);
    }

    public static Alignment alignmentOf(Guild guild) {
        return GUILD_TO_ALIGNMENT.get(guild);
    }
}
