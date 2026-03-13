
// `src/Guild/ObsidianHexCoven/Weapon/VoidScriptGrimoire.java`
package Guild.ObsidianHexCoven.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Status.DrainStatus;
import Status.Status;
import Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class VoidScriptGrimoire extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final String WEAPON_NAME = "VoidScriptGrimoire";
    private static final String EFFECT =
            "VoidScriptGrimoire: A chained grimoire inked with void-script, permitted only to the Obsidian Hex Coven. " +
            "Increases attack and expands spell knowledge by raising intelligence and wisdom while wielded. " +
            "Has a chance to proc DRAIN_STATUS on hit.";

    private static final int WEIGHT = 3;

    private static final int REQUIRED_INTELLIGENCE = 16;
    private static final int REQUIRED_WISDOM = 14;

    private static final int ATTACK_INCREASE = 7;
    private static final int INTELLIGENCE_BONUS = 6;
    private static final int WISDOM_BONUS = 3;

    // On-hit Drain proc tuning
    private static final int DRAIN_PROC_CHANCE_PERCENT = 20;
    private static final int DRAIN_DURATION_TURNS = 2;
    private static final double DRAIN_PERCENT_PER_TURN = 0.10;
    private static final DrainStatus.DrainType DRAIN_TYPE = DrainStatus.DrainType.MAGIC;

    // Track last applied bonuses for correct removal
    private int lastAttackBonus = 0;
    private int lastIntBonus = 0;
    private int lastWisBonus = 0;

    private VoidScriptGrimoire() {
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static VoidScriptGrimoire createVoidScriptGrimoire(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Hex Coven members can wield the VoidScriptGrimoire.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
            throw new IllegalArgumentException("Character does not have the required intelligence to wield the VoidScriptGrimoire.");
        if (character.getWisdom() < REQUIRED_WISDOM)
            throw new IllegalArgumentException("Character does not have the required wisdom to wield the VoidScriptGrimoire.");

        return new VoidScriptGrimoire();
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
     * Call this after a successful hit in your combat pipeline.
     * @return a new DrainStatus to apply, or null if it did not proc.
     */
    public Status tryProcDrainStatus(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= DRAIN_PROC_CHANCE_PERCENT) return null;

        return new DrainStatus(DRAIN_DURATION_TURNS, DRAIN_PERCENT_PER_TURN, DRAIN_TYPE);
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
}
