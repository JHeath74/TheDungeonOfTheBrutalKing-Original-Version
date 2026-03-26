
package Guild.DirgeweaversChorus.Armour;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;

import java.util.Random;

public class DirgeweaversFuneralWeave {

    private static final Guild REQUIRED_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private static final String ARMOUR_NAME = "DirgeweaversFuneralWeave";
    private static final String DESCRIPTION =
            "DirgeweaversFuneralWeave: Ritual cloth for the Dirgeweavers Chorus. Grants vitality and defense; when struck, it has a 10% chance to reflect 10% of the damage taken.";

    private static final int REQUIRED_VITALITY = 12;
    private static final int VITALITY_BONUS = 6;
    private static final int DEFENSE_BONUS = 3;

    private static final int REFLECT_CHANCE_PERCENT = 10;
    private static final double REFLECT_RATIO = 0.10;

    private final Random random = new Random();

    // Track last applied bonuses for correct removal
    private int lastVitBonus = 0;
    private int lastDefBonus = 0;

    public static DirgeweaversFuneralWeave createDirgeweaversFuneralWeave(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Dirgeweavers Chorus members can wear the DirgeweaversFuneralWeave.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wear the DirgeweaversFuneralWeave.");
        return new DirgeweaversFuneralWeave();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;

        lastVitBonus = VITALITY_BONUS;
        lastDefBonus = DEFENSE_BONUS;

        wearer.setVitality(wearer.getVitality() + lastVitBonus);
        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastVitBonus != 0) wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));
        if (lastDefBonus != 0) wearer.setDefense(wearer.getDefense() - lastDefBonus);

        lastVitBonus = 0;
        lastDefBonus = 0;
        return true;
    }

    /**
     * Call this after damage is applied to the wearer.
     *
     * @param wearer The character wearing this armour.
     * @param attacker The enemy that dealt the damage.
     * @param damageTaken The final damage actually taken.
     * @param isReflectedDamage True if this damage event was caused by reflection (prevents loops).
     */
    public void onDamaged(Charecter wearer, Enemies attacker, int damageTaken, boolean isReflectedDamage) {
        if (wearer == null || attacker == null) return;
        if (wearer.getGuild() != REQUIRED_GUILD) return;
        if (isReflectedDamage) return;
        if (damageTaken <= 0) return;
        if (attacker.isDead()) return;

        int roll = random.nextInt(100) + 1;
        if (roll > REFLECT_CHANCE_PERCENT) return;

        int reflected = (int) Math.floor(damageTaken * REFLECT_RATIO);
        if (reflected < 1) reflected = 1;

        int cap = attacker.getHitPoints();
        if (cap <= 0) return;

        attacker.takeDamage(Math.min(reflected, cap));
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
}
