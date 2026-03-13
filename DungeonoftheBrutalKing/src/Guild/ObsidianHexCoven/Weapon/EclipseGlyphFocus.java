
// src/Guild/ObsidianHexCoven/Weapon/EclipseGlyphFocus.java
package Guild.ObsidianHexCoven.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class EclipseGlyphFocus extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_HEX_COVEN;

    private static final String WEAPON_NAME = "EclipseGlyphFocus";
    private static final String EFFECT =
            "EclipseGlyphFocus: A palm-sized obsidian focus etched with an eclipse glyph, sanctioned by the Obsidian Hex Coven. " +
            "Increases attack and deepens spell control by raising intelligence and wisdom while wielded. " +
            "Has a chance to proc STUN_STATUS on hit.";

    private static final int WEIGHT = 2;

    private static final int REQUIRED_INTELLIGENCE = 14;
    private static final int REQUIRED_WISDOM = 13;

    private static final int ATTACK_INCREASE = 6;
    private static final int INTELLIGENCE_BONUS = 5;
    private static final int WISDOM_BONUS = 4;

    // Percent chance to proc the single status on hit
    private static final int STATUS_PROC_CHANCE_PERCENT = 20;

    // Single picked status represented as a related class
    private static final Class<?> PROC_STATUS_CLASS = StunStatus.class;

    // Track last applied bonuses for correct removal
    private int lastAttackBonus = 0;
    private int lastIntBonus = 0;
    private int lastWisBonus = 0;

    private EclipseGlyphFocus() {
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static EclipseGlyphFocus createEclipseGlyphFocus(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Hex Coven members can wield the EclipseGlyphFocus.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
            throw new IllegalArgumentException("Character does not have the required intelligence to wield the EclipseGlyphFocus.");
        if (character.getWisdom() < REQUIRED_WISDOM)
            throw new IllegalArgumentException("Character does not have the required wisdom to wield the EclipseGlyphFocus.");

        return new EclipseGlyphFocus();
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
     * Call this from your combat/on-hit pipeline after a successful hit.
     * Returns the single status class to instantiate/apply (or null if it did not proc).
     */
    public Class<?> tryProcStatusClass(Charecter attacker, Charecter target) {
        if (attacker == null || target == null) return null;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= STATUS_PROC_CHANCE_PERCENT) return null;

        return PROC_STATUS_CLASS;
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

    // Related status class placeholder
    public static final class StunStatus {}
}
