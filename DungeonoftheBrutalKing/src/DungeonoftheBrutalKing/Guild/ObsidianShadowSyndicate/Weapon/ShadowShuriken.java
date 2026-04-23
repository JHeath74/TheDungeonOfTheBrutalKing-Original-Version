// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Weapons\ShadowShuriken.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import java.util.Random;

/**
 * ShadowShuriken - a light throwing weapon for thieves. Ranged finesse weapon
 * that scales with Agility and uses Intelligence to improve status application
 * and range utility. Must be a full member of the Obsidian Shadow Syndicate to
 * obtain or equip.
 */
public class ShadowShuriken extends WeaponManager {

    private static final String WEAPON_NAME = "Shadow Shuriken";
    private static final String EFFECT = "A set of razor-sharp obsidian shuriken favored by the Obsidian Shadow Syndicate. "
            + "Highly accurate at range; Agility increases damage and crit chance, Intelligence increases chance to apply poison/bleed.";

    // Compatibility: WeaponManager expects a required strength field. This weapon requires low strength.
    private static final int REQUIRED_STRENGTH = 5;
    private static final int REQUIRED_AGILITY = 12;
    private static final int REQUIRED_INTELLIGENCE = 12;
    private static final int WEIGHT = 10;
    private static final int BASE_DAMAGE = 18;

    private static final Random RNG = new Random();

    private static Character myChar = Character.getInstance();

    public ShadowShuriken(int damage, String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public ShadowShuriken(int requiredStrength, int damage, String effect) {
        super(WEAPON_NAME, requiredStrength, damage, effect, WEIGHT);
    }

    public static ShadowShuriken createShadowShuriken(Character character, int damage, String effect) {
        try {
            if (character == null) throw new IllegalArgumentException("Character is null");
            if (!isGuildMember(character)) {
                throw new IllegalArgumentException("Character must be a full member of the Obsidian Shadow Syndicate to obtain the Shadow Shuriken.");
            }
            if (character.getAgility() < REQUIRED_AGILITY) {
                throw new IllegalArgumentException("Character does not have the required Agility to wield the Shadow Shuriken.");
            }
            if (character.getIntelligence() < REQUIRED_INTELLIGENCE) {
                throw new IllegalArgumentException("Character does not have the required Intelligence to wield the Shadow Shuriken.");
            }
            return new ShadowShuriken(damage, effect);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to create Shadow Shuriken");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getRequiredStrength() { return REQUIRED_STRENGTH; }
    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return base + (agi * 0.4) + (inti * 0.15);
        } catch (Exception e) {
            return base;
        }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.04;
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return baseCrit + (agi * 0.009) + (inti * 0.004);
        } catch (Exception e) {
            return baseCrit;
        }
    }

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    public static boolean isGuildMember(Character character) {
        if (character == null) return false;
        try {
            return character.getCurrentGuild() == GuildType.THIEF
                    && character.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPurchasableBy(Character character) {
        return isGuildMember(character);
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        try {
            wearer.setWeapon(this.getName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * On hit: chance to apply POISON or BLEED. Base chance = 0.12 + INT*0.02 (capped 0.85).
     * Higher INT biases toward POISON; otherwise BLEED. Durations scale with INT or AGI.
     */
    @Override
    public void applyCombatEffect(Character target) {
        if (target == null) return;
        Character attacker = myChar != null ? myChar : Character.getInstance();
        int inti = (attacker != null) ? attacker.getIntelligence() : 0;
        int agi = (attacker != null) ? attacker.getAgility() : 0;
        double chance = 0.12 + (inti * 0.02);
        chance = Math.min(0.85, chance);
        if (RNG.nextDouble() <= chance) {
            double poisonBias = 0.5 + (inti * 0.01); // >0.5 favors poison with higher INT
            if (RNG.nextDouble() <= Math.min(0.95, poisonBias)) {
                int duration = 1 + Math.max(0, inti / 10);
                try { target.applyStatusEffect(StatusType.POISON_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
            } else {
                int duration = 1 + Math.max(0, agi / 10);
                try { target.applyStatusEffect(StatusType.BLEED_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
            }
        }
    }
}