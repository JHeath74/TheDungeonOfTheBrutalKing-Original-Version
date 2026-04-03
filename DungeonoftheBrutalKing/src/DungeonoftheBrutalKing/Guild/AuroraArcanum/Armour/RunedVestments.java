package DungeonoftheBrutalKing.Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import java.util.HashMap;
import java.util.Map;

public class RunedVestments extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 10; // Example value
    private static final double DEFENSE_BONUS_PERCENT = 0.12; // 12%
    private static final int ARMOUR_DEFENSE = 1; // light base defence
    private static final int WEIGHT = 2;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;
    private boolean isEquipped = false;

    // Map of rune name to remaining charges
    private final Map<String, Integer> runeCharges;
    private final int maxChargesPerRune;

    public RunedVestments(Map<String, Integer> initialRunes, int maxChargesPerRune, String effect) {
        // requiredStrength unused for wizards, so pass 0; provide base defence and weight
        super("Runed Vestments", 0, ARMOUR_DEFENSE, WEIGHT, effect);
        this.runeCharges = new HashMap<>(initialRunes);
        this.maxChargesPerRune = maxChargesPerRune;
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped
                && wearer.getGuild() == GUILDname
                && wearer.getIntelligence() >= REQUIRED_INTELLIGENCE) {

            int bonus = (int) Math.round(wearer.getDefense() * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(wearer.getDefense() + bonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            int bonus = (int) Math.round(
                    wearer.getDefense() / (1 + DEFENSE_BONUS_PERCENT) * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(wearer.getDefense() - bonus);
            isEquipped = false;
            return true;
        }
        return false;
    }

    // Absorb magical damage using available runes
    public boolean absorbMagicDamage(String rune) {
        Integer charges = runeCharges.get(rune);
        if (charges != null && charges > 0) {
            runeCharges.put(rune, charges - 1);
            return true; // Damage absorbed
        }
        return false; // No charges left
    }

    // Recharge a specific rune
    public void rechargeRune(String rune) {
        runeCharges.put(rune, maxChargesPerRune);
    }

    public Map<String, Integer> getRuneCharges() {
        return new HashMap<>(runeCharges);
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
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
        return "Runed Vestments: Layered garments inscribed with protective wards. Each rune can absorb or deflect a limited amount of magical damage.";
    }
}
