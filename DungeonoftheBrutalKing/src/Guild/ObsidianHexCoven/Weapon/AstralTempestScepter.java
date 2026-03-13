
package Guild.ObsidianHexCoven.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class AstralTempestScepter extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final String WEAPON_NAME = "AstralTempestScepter";
    private static final String EFFECT =
            "AstralTempestScepter: A storm-scribed scepter of the Obsidian Hex Coven. " +
            "Increases attack and enhances spellcraft via intelligence and wisdom. " +
            "Has a chance to inflict a random debilitating status on hit.";

    private static final int WEIGHT = 3;

    private static final int REQUIRED_INTELLIGENCE = 16;
    private static final int REQUIRED_WISDOM = 14;

    private static final int ATTACK_INCREASE = 6;
    private static final int INTELLIGENCE_BONUS = 5;
    private static final int WISDOM_BONUS = 4;

    // Proc chance (percent) to apply 1 random status on hit
    private static final int STATUS_PROC_CHANCE_PERCENT = 20;

    // Track last applied bonuses for correct removal
    private int lastAttackBonus = 0;
    private int lastIntBonus = 0;
    private int lastWisBonus = 0;

    // Keep as Strings to avoid coupling to your Status enum/package name.
    // Replace with your actual Status type if you have one (e.g., StatusEffect, StatusType, etc.).
    private static final String[] RANDOM_STATUSES = new String[] {
            "ACCURACY_STATUS",
            "BLEED_STATUS",
            "BLIND_STATUS",
            "DAZE_STATUS",
            "DRAIN_STATUS",
            "DEFENSE_DOWN_STATUS",
            "ECHO_OF_ETERNITY_STATUS",
            "ETHEREAL_CHAINS_STATUS",
            "FEAR_STATUS",
            "FIRE_STATUS",
            "ICE_STATUS",
            "ILLUSORY_DOUBLE_STATUS",
            "LIFE_STEAL_STATUS",
            "MIND_PROBE_STATUS",
            "POISON_STATUS",
            "RADIANT_STATUS",
            "REDUCE_DEFENSE_STATUS",
            "REDUCE_STRENGTH_STATUS",
            "IMMOBILIZED_STATUS",
            "DIVINE_INTERVENTION_STATUS",
            "RESILIENCE_STATUS",
            "STUN_STATUS",
            "VOID_ECHO_STATUS",
            "ASTRAL_WARD_STATUS",
            "LIGHTNING_STATUS"
    };

    private AstralTempestScepter() {
        // WeaponManager signature: (name, requiredStrength, attackIncrease, effect, weight)
        // Mage weapon enforces INT/WIS locally, so requiredStrength is 0.
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static AstralTempestScepter createAstralTempestScepter(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Hex Coven members can wield the AstralTempestScepter.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
            throw new IllegalArgumentException("Character does not have the required intelligence to wield the AstralTempestScepter.");
        if (character.getWisdom() < REQUIRED_WISDOM)
            throw new IllegalArgumentException("Character does not have the required wisdom to wield the AstralTempestScepter.");

        return new AstralTempestScepter();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastIntBonus = INTELLIGENCE_BONUS;
        lastWisBonus = WISDOM_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setIntelligence(wearer.getIntelligence() + lastIntBonus);
        wearer.setWisdom(wearer.getWisdom() + lastWisBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastIntBonus != 0) wearer.setIntelligence(Math.max(0, wearer.getIntelligence() - lastIntBonus));
        if (lastWisBonus != 0) wearer.setWisdom(Math.max(0, wearer.getWisdom() - lastWisBonus));

        lastAttackBonus = 0;
        lastIntBonus = 0;
        lastWisBonus = 0;

        return true;
    }

    /**
     * Call this from your combat/on-hit pipeline, e.g. right after damage is dealt.
     * \@return the applied status name, or null if nothing procced.
     */
    public String tryApplyRandomStatus(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= STATUS_PROC_CHANCE_PERCENT) return null;

        String status = RANDOM_STATUSES[ThreadLocalRandom.current().nextInt(RANDOM_STATUSES.length)];

        // TODO: Replace this with your real status application API.
        // Examples (adjust to your codebase):
        // target.applyStatus(Status.valueOf(status));
        // target.getStatusManager().addStatus(StatusType.valueOf(status), durationTurns);
        //
        // For now, return the chosen status so the caller can apply it.
        return status;
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

    public int getRequiredIntelligence() {
        return REQUIRED_INTELLIGENCE;
    }

    public int getRequiredWisdom() {
        return REQUIRED_WISDOM;
    }

    public int getAttackIncrease() {
        return ATTACK_INCREASE;
    }

    public int getIntelligenceBonus() {
        return INTELLIGENCE_BONUS;
    }

    public int getWisdomBonus() {
        return WISDOM_BONUS;
    }

    public int getStatusProcChancePercent() {
        return STATUS_PROC_CHANCE_PERCENT;
    }
}
