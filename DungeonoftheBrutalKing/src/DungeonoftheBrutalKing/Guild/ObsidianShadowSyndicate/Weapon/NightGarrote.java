// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Weapons\NightGarrote.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import java.util.Random;

/**
 * NightGarrote - stealth utility weapon for thieves. Low base damage but high
 * chance to silence or immobilize targets based on wielder Intelligence.
 * Requires full membership in the Obsidian Shadow Syndicate.
 */
public class NightGarrote extends WeaponManager {

    private static final String WEAPON_NAME = "Night Garrote";
    private static final int REQUIRED_STRENGTH = 0; // engine compatibility
    private static final int REQUIRED_AGILITY = 13;
    private static final int REQUIRED_INTELLIGENCE = 14;
    private static final int WEIGHT = 8;
    private static final int BASE_DAMAGE = 12;

    private static final Random RNG = new Random();
    private static Character myChar = Character.getInstance();

    public NightGarrote(int damage, String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public NightGarrote(int requiredStrength, int damage, String effect) {
        super(WEAPON_NAME, requiredStrength, damage, effect, WEIGHT);
    }

    public static NightGarrote createNightGarrote(Character character, int damage, String effect) {
        try {
            if (character == null) throw new IllegalArgumentException("Character is null");
            if (!isGuildMember(character))
                throw new IllegalArgumentException("Character must be a full member of the Obsidian Shadow Syndicate to obtain the Night Garrote.");
            if (character.getAgility() < REQUIRED_AGILITY)
                throw new IllegalArgumentException("Character does not have the required Agility to wield the Night Garrote.");
            if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
                throw new IllegalArgumentException("Character does not have the required Intelligence to wield the Night Garrote.");
            return new NightGarrote(damage, effect);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to create Night Garrote");
        }
    }

    @Override
    public String getName() { return this.name; }

    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return base + (agi * 0.35) + (inti * 0.25);
        } catch (Exception e) {
            return base;
        }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.03;
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return baseCrit + (agi * 0.009) + (inti * 0.006);
        } catch (Exception e) {
            return baseCrit;
        }
    }

    public StatusType getEffect() { return super.getStatusEffect(); }

    @Override
    public void setEffect(String effect) { super.setEffect(effect); }

    public static boolean isGuildMember(Character character) {
        if (character == null) return false;
        try {
            return character.getCurrentGuild() == GuildType.THIEF
                    && character.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPurchasableBy(Character character) { return isGuildMember(character); }

    @Override
    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        try { wearer.setWeapon(this.getName()); return true; } catch (Exception e) { return false; }
    }

    /**
     * Apply special garrote effects on hit: chance to SILENCED or IMMOBILIZED based on wielder INT.
     * This method is intended to be called from combat resolution when this weapon hits a target.
     */
    @Override
    public void applyCombatEffect(Character target) {
        if (target == null) return;
        Character attacker = myChar != null ? myChar : Character.getInstance();
        int inti = (attacker != null) ? attacker.getIntelligence() : 0;
        // Base 20% chance + 2% per INT point
        double chance = 0.20 + (inti * 0.02);
        if (RNG.nextDouble() <= Math.min(0.9, chance)) {
            // Success: choose effect weighted by INT (higher INT favors SILENCED)
            double pick = RNG.nextDouble();
            if (pick < 0.6) {
                // apply silence for 2 turns
                try { target.applyStatusEffect(StatusType.SILENCED_STATUS, 2, 0, attacker); } catch (Exception ignored) {}
            } else {
                // apply immobilize for 1-2 turns
                try { target.applyStatusEffect(StatusType.IMMOBILIZED_STATUS, 1 + RNG.nextInt(2), 0, attacker); } catch (Exception ignored) {}
            }
        }
    }
}