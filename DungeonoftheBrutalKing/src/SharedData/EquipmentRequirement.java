package SharedData;

/**
 * Central enum for all weapons, armours, and spells.
 * Stores required stats, type, and guild restriction.
 */
public enum EquipmentRequirement {

    // --- Armours ---
    // Format: (strengthRequired, intelligenceRequired, dexterityRequired, weight, type, guild)

    // Heavy magical chainmail, offers strong defense for wizards.
    ASTRAL_CHAIN(10, 0, 0, 8, EquipmentType.ARMOUR, Guild.AURORA_ARCANUM),

    // Mantle infused with elemental energy, grants resistance and channeling abilities.
    ELEMENTAL_MANTLE(0, 14, 0, 4, EquipmentType.ARMOUR, Guild.AURORA_ARCANUM),

    // Lightweight cloak providing concealment and resistance to certain damage types.
    MYSTIC_CLOAK(0, 0, 0, 2, EquipmentType.ARMOUR, Guild.AURORA_ARCANUM),

    // Robes that greatly enhance magical power and defense for high-level wizards.
    ROBES_OF_THE_MAGI(0, 20, 0, 3, EquipmentType.ARMOUR, Guild.AURORA_ARCANUM),

    // Vestments inscribed with runes, offering balanced protection and magical benefits.
    RUNED_VESTMENTS(8, 0, 0, 6, EquipmentType.ARMOUR, Guild.AURORA_ARCANUM),

    // Heavy plate armor for warriors, symbolizes valor and provides high defense.
    CRIMSON_PLATE_OF_VALOR(17, 0, 0, 15, EquipmentType.ARMOUR, Guild.CRIMSON_BLADES),

    // Blood-forged mail, extremely durable and suited for frontline fighters.
    BLOODFORGED_BATTLEMAIL(18, 0, 0, 18, EquipmentType.ARMOUR, Guild.CRIMSON_BLADES),

    // Cuirass designed for blade wardens, offers agility and strong protection.
    BLADEWARDEN_CUIRASS(16, 0, 0, 14, EquipmentType.ARMOUR, Guild.CRIMSON_BLADES),

    // --- Weapons ---
    // Format: (strengthRequired, intelligenceRequired, dexterityRequired, weight, type, guild)

    // Dagger that enhances spellcasting, quick and light.
    DAGGER_OF_SPELLCRAFT(0, 14, 2, 1, EquipmentType.WEAPON, Guild.AURORA_ARCANUM),

    // Rod imbued with magical energy, boosts spell power.
    MAGICAL_ROD(0, 20, 0, 2, EquipmentType.WEAPON, Guild.AURORA_ARCANUM),

    // Spellbook granting access to powerful arcane spells.
    SPELLBOOK_OF_ARCANE_MIGHT(0, 22, 0, 2, EquipmentType.WEAPON, Guild.AURORA_ARCANUM),

    // Staff that channels immense magical force.
    STAFF_OF_POWER(0, 30, 0, 5, EquipmentType.WEAPON, Guild.AURORA_ARCANUM),

    // Wand that focuses arcane energy for precise casting.
    WAND_OF_ARCANE_FOCUS(0, 18, 0, 1, EquipmentType.WEAPON, Guild.AURORA_ARCANUM),

    // --- Spells ---
    // Format: (strengthRequired, intelligenceRequired, dexterityRequired, weight, type, guild)

    // Launches a missile of pure arcane energy.
    ARCANE_MISSILE(0, 12, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Step through the astral plane to evade attacks.
    ASTRAL_STEP(0, 15, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Summons a celestial shield for protection.
    CELESTIAL_WARD(0, 18, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Echoes of time strike enemies with arcane force.
    ECHO_OF_ETERNITY(0, 10, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Fires a concentrated ray of elemental power.
    ELEMENTAL_RAY(0, 10, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Chains of ethereal energy bind the target.
    ETHEREAL_CHAINS(0, 10, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Creates an illusory duplicate to confuse foes.
    ILLUSORY_DOUBLE(0, 10, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Surges mana to restore magical energy quickly.
    MANA_SURGE(0, 8, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Probes the mind of a target for secrets.
    MIND_PROBE(0, 14, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Calls down a shower of falling stars.
    STARFALL(0, 16, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Slows the flow of time for enemies.
    TIME_DILATION(0, 18, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM),

    // Summons echoes from the void to attack enemies.
    VOID_ECHO(0, 15, 0, 0, EquipmentType.SPELL, Guild.AURORA_ARCANUM);

    // Required strength to equip/use
    public final int strengthRequired;
    // Required intelligence to equip/use
    public final int intelligenceRequired;
    // Required dexterity to equip/use
    public final int dexterityRequired;
    // Weight of the equipment
    public final int weight;
    // Type of equipment (armour, weapon, spell)
    public final EquipmentType type;
    // Guild restriction for the equipment
    public final Guild guild;

    EquipmentRequirement(int str, int intel, int dex, int weight, EquipmentType type, Guild guild) {
        this.strengthRequired = str;
        this.intelligenceRequired = intel;
        this.dexterityRequired = dex;
        this.weight = weight;
        this.type = type;
        this.guild = guild;
    }

    // Returns required intelligence
    public int getIntelligence() {
        return this.intelligenceRequired;
    }

    // Returns required strength
    public int getStrength() {
        return this.strengthRequired;
    }

    // Returns equipment weight
    public int getWeight() {
        return this.weight;
    }
}
