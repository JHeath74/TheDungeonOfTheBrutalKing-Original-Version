
package DungeonoftheBrutalKing.Guild.CelestialArcaneOrder.Armour;

import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class VestureoftheDawnbound extends ArmourManager {

    private static final int REQUIRED_WISDOM = 12;
    private static final int ARMOUR_DEFENSE = 24;
    private static final int WEIGHT = 1;
    private static final int WISDOM_BONUS = 22;
    private static final Guild GUILD_NAME = Guild.CELESTIAL_ARCANE_ORDER;
    private static final GuildType GUILD_TYPE = GuildType.CLERIC;

    // Track equipped state and fire protection per wearer
    private Character equippedWearer = null;
    private boolean fireProtectionApplied = false;
    private boolean wearerHadFireProtection = false;

    public VestureoftheDawnbound(String effect) {
        // requiredStrength unused (wisdom-gated), so pass 0
        super("Vesture of the Dawnbound", 0, ARMOUR_DEFENSE, WEIGHT, effect);
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer != null
                && equippedWearer == null
                && wearer.getGuild() == GUILD_NAME
                && wearer.getWisdom() >= REQUIRED_WISDOM) {

            wearer.setEuippedArmour(getName());
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);

            wearerHadFireProtection = wearer.hasEffectProtection("fire");
            if (!wearerHadFireProtection) {
                wearer.setEffectProtection("fire", true);
                fireProtectionApplied = true;
            }

            equippedWearer = wearer;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (wearer != null && equippedWearer == wearer) {
            wearer.setEuippedArmour(null);
            wearer.setWisdom(wearer.getWisdom() - WISDOM_BONUS);

            if (fireProtectionApplied && !wearerHadFireProtection) {
                wearer.setEffectProtection("fire", false);
            }

            fireProtectionApplied = false;
            equippedWearer = null;
            return true;
        }
        return false;
    }

    public Guild getGuild() {
        return GUILD_NAME;
    }

    public GuildType getGuildType() {
        return GUILD_TYPE;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return (double) WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Vesture of the Dawnbound: Sacred vestment woven with celestial threads, favored by clerics of the Celestial Arcane Order. Enhances divine protection and spellcasting.";
    }
}
