
package Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class ShadowstitchDagger extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final String WEAPON_NAME = "ShadowstitchDagger";
    private static final String EFFECT =
            "ShadowstitchDagger: A needle-slim dagger favored by the Obsidian Shadow Syndicate. " +
            "Increases attack and rewards precise strikes via agility. " +
            "Has a chance to inflict a random debilitating status on hit.";

    private static final int WEIGHT = 1;

    private static final int REQUIRED_AGILITY = 12;

    private static final int ATTACK_INCREASE = 4;
    private static final int AGILITY_BONUS = 2;

    private static final int STATUS_PROC_CHANCE_PERCENT = 15;

    private int lastAttackBonus = 0;
    private int lastAgiBonus = 0;

    private static final String[] RANDOM_STATUSES = new String[] {
            "BLEED_STATUS",
            "POISON_STATUS",
            "BLIND_STATUS",
            "DAZE_STATUS",
            "ACCURACY_STATUS",
            "REDUCE_DEFENSE_STATUS",
            "IMMOBILIZED_STATUS"
    };

    private ShadowstitchDagger() {
        // WeaponManager signature: (name, requiredStrength, attackIncrease, effect, weight)
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static ShadowstitchDagger createShadowstitchDagger(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Shadow Syndicate members can wield the ShadowstitchDagger.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wield the ShadowstitchDagger.");

        return new ShadowstitchDagger();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastAgiBonus = AGILITY_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastAgiBonus != 0) wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));

        lastAttackBonus = 0;
        lastAgiBonus = 0;

        return true;
    }

    /**
     * Call this from your combat/on-hit pipeline, e.g. right after damage is dealt.
     * @return the applied status name, or null if nothing procced.
     */
    public String tryApplyRandomStatus(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= STATUS_PROC_CHANCE_PERCENT) return null;

        return RANDOM_STATUSES[ThreadLocalRandom.current().nextInt(RANDOM_STATUSES.length)];
    }

    @Override
    public String getName() {
        return WEAPON_NAME;
    }

    public String getEffect() {
        return EFFECT;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public int getRequiredAgility() {
        return REQUIRED_AGILITY;
    }

    public int getAttackIncrease() {
        return ATTACK_INCREASE;
    }

    public int getAgilityBonus() {
        return AGILITY_BONUS;
    }

    public int getStatusProcChancePercent() {
        return STATUS_PROC_CHANCE_PERCENT;
    }
}
