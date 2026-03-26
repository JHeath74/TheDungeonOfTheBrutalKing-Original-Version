
// src/Guild/DirgeweaversChorus/Armour/ChorusKnightsLamentPlate.java
package Guild.DirgeweaversChorus.Armour;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;

import java.util.Random;

public class ChorusKnightsLamentPlate {

    private static final Guild REQUIRED_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final String ARMOUR_NAME = "ChorusKnightsLamentPlate";
    private static final String DESCRIPTION =
            "ChorusKnightsLamentPlate: Funeral plate reserved for the Dirgeweavers Chorus. Grants vitality and defense, and may afflict foes with a lamenting status.";

    private static final int REQUIRED_VITALITY = 15;
    private static final int VITALITY_BONUS = 4;
    private static final int DEFENSE_BONUS = 6;

    private static final int STATUS_CHANCE_PERCENT = 20;

    // If your project uses an enum similar to WeaponManager.StatusEffect, wire it here.
    // This is kept local to avoid depending on unknown armour base classes.
    public enum StatusEffect {
        NONE,
        SONGBOUND,
        WEAKENED,
        SILENCED
    }

    private final StatusEffect statusEffect;

    // Track last applied bonuses for correct removal
    private int lastVitBonus = 0;
    private int lastDefBonus = 0;

    public ChorusKnightsLamentPlate(StatusEffect statusEffect) {
        this.statusEffect = (statusEffect == null) ? StatusEffect.NONE : statusEffect;
    }

    public static ChorusKnightsLamentPlate createChorusKnightsLamentPlate(Charecter character, StatusEffect statusEffect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Dirgeweavers Chorus members can wear the ChorusKnightsLamentPlate.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wear the ChorusKnightsLamentPlate.");
       return new ChorusKnightsLamentPlate(statusEffect);
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;

        // If you have a dedicated armour slot/string on Charecter, replace these guards accordingly.
        // This mirrors the weapon equip pattern without assuming a specific armour API exists.
        lastVitBonus = VITALITY_BONUS;
        lastDefBonus = DEFENSE_BONUS;

        wearer.setVitality(wearer.getVitality() + lastVitBonus);

        // Defense setter name may differ in your project (e.g., setDefense, setArmour, setDamageReduction).
        // Update these two lines to match your Charecter API.
        wearer.setDefense(wearer.getDefense() + lastDefBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastVitBonus != 0) {
            wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));
        }

        if (lastDefBonus != 0) {
            wearer.setDefense(wearer.getDefense() - lastDefBonus);
        }

        lastVitBonus = 0;
        lastDefBonus = 0;

        return true;
    }

    /**
     * Call this from your combat flow when the wearer hits an enemy (or when the enemy hits the wearer,
     * depending on the design you want).
     */
    public void onHit(Charecter wearer, Enemies enemy) {
        if (wearer == null || enemy == null) return;
        if (wearer.getGuild() != REQUIRED_GUILD) return;
        if (statusEffect == StatusEffect.NONE) return;

        Random rand = new Random();
        int roll = rand.nextInt(100) + 1;
        if (roll <= STATUS_CHANCE_PERCENT) {
            // Hook into your enemy status system here.
            // Example placeholders (adjust to your actual API):
            // enemy.applyStatus(statusEffect);
            // enemy.addStatus(new Songbound("Songbound", 1));
        }
    }

    public String getName() {
        return ARMOUR_NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public int getVitalityBonus() {
        return VITALITY_BONUS;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }
}
