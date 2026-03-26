
// src/Guild/ObsidianHexCoven/Weapon/UmbralRuneStaff.java
package Guild.ObsidianHexCoven.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Status.FearStatus;
import Status.HasHitPoints;
import Status.LifeStealStatus;
import Status.Status;
import Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class UmbralRuneStaff extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final String WEAPON_NAME = "UmbralRuneStaff";
    private static final String EFFECT =
            "UmbralRuneStaff: A rune-etched staff reserved for the Obsidian Hex Coven. " +
            "Increases attack and amplifies arcane power by raising intelligence and wisdom while wielded. " +
            "Has a chance to proc FEAR_STATUS and LIFESTEAL_STATUS on hit.";

    private static final int WEIGHT = 5;

    private static final int REQUIRED_INTELLIGENCE = 15;
    private static final int REQUIRED_WISDOM = 13;

    private static final int ATTACK_INCREASE = 6;
    private static final int INTELLIGENCE_BONUS = 4;
    private static final int WISDOM_BONUS = 3;

    // On-hit Fear proc tuning
    private static final int FEAR_PROC_CHANCE_PERCENT = 20;
    private static final int FEAR_DURATION_TURNS = 2;

    // On-hit LifeSteal proc tuning
    private static final int LIFESTEAL_PROC_CHANCE_PERCENT = 20;
    private static final int LIFESTEAL_DURATION_TURNS = 1; // lifesteal happens on applyEffect; duration can be 1

    private int lastAttackBonus = 0;
    private int lastIntBonus = 0;
    private int lastWisBonus = 0;

    private UmbralRuneStaff() {
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static UmbralRuneStaff createUmbralRuneStaff(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Hex Coven members can wield the UmbralRuneStaff.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
            throw new IllegalArgumentException("Character does not have the required intelligence to wield the UmbralRuneStaff.");
        if (character.getWisdom() < REQUIRED_WISDOM)
            throw new IllegalArgumentException("Character does not have the required wisdom to wield the UmbralRuneStaff.");

        return new UmbralRuneStaff();
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
     * Call this after a successful hit.
     * \- Returns LifeSteal first (if it procs and both sides support HP)
     * \- Otherwise returns Fear (if it procs)
     * \- Otherwise null
     */
    public Status tryProcOnHitStatus(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        // Try LifeSteal
        if (attacker instanceof HasHitPoints && target instanceof HasHitPoints) {
            int roll = ThreadLocalRandom.current().nextInt(100);
            if (roll < LIFESTEAL_PROC_CHANCE_PERCENT) {
                return new LifeStealStatus(LIFESTEAL_DURATION_TURNS)
                        .withTarget((HasHitPoints) target);
            }
        }

        // Try Fear
        {
            int roll = ThreadLocalRandom.current().nextInt(100);
            if (roll < FEAR_PROC_CHANCE_PERCENT) {
                return new FearStatus(FEAR_DURATION_TURNS);
            }
        }

        return null;
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

    public int getFearProcChancePercent() {
        return FEAR_PROC_CHANCE_PERCENT;
    }

    public int getFearDurationTurns() {
        return FEAR_DURATION_TURNS;
    }

    public int getLifeStealProcChancePercent() {
        return LIFESTEAL_PROC_CHANCE_PERCENT;
    }

    public int getLifeStealDurationTurns() {
        return LIFESTEAL_DURATION_TURNS;
    }
}
