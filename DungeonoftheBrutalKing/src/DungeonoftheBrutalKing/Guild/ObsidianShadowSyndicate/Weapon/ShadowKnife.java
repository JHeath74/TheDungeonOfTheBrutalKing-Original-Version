// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Weapons\ShadowKnife.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import java.util.Random;

/**
 * ShadowKnife - a thief-appropriate finesse weapon that scales with Agility
 * and uses Intelligence to augment special effects (poison/stealth/backstab).
 */
public class ShadowKnife extends WeaponManager {

    private static final String WEAPON_NAME = "Shadow Knife";
    private static final String EFFECT =
            "Shadow Knife: A sleek, razor-sharp dagger forged from enchanted obsidian, favored by the Obsidian Shadow Syndicate. " +
            "This weapon excels in stealth and precision, allowing the wielder to strike swiftly and silently. " +
            "Damage scales with Agility, while Intelligence enhances its special effects like poison application and backstab damage.";

    // The game engine expects a "required strength" field for weapons; this weapon uses AGI/INT instead.
    private static final int REQUIRED_STRENGTH = 0;
    private static final int REQUIRED_AGILITY = 14;
    private static final int REQUIRED_INTELLIGENCE = 13;
    private static final int WEIGHT = 15;
    private static final int BASE_DAMAGE = 22;

    private static final Random RNG = new Random();



    private static Charecter myChar = Charecter.getInstance();

    public ShadowKnife(int damage, String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public ShadowKnife(int requiredStrength, int damage, String effect) {
        super(WEAPON_NAME, requiredStrength, damage, effect, WEIGHT);
    }

    /**
     * Factory that validates the wearer's strength and guild membership before creating the weapon.
     */
    public static ShadowKnife createShadowKnife(Charecter character, int damage, String effect) {
        try {
            if (character == null) throw new IllegalArgumentException("Character is null");
            if (!isGuildMember(character)) {
                throw new IllegalArgumentException("Character must be a full member of the Obsidian Shadow Syndicate to obtain the Shadow Knife.");
            }
            if (character.getAgility() < REQUIRED_AGILITY) {
                throw new IllegalArgumentException("Character does not have the required Agility to wield the Shadow Knife.");
            }
            if (character.getIntelligence() < REQUIRED_INTELLIGENCE) {
                throw new IllegalArgumentException("Character does not have the required Intelligence to wield the Shadow Knife.");
            }
            return new ShadowKnife(damage, effect);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to create Shadow Knife");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

    /**
     * Damage scales primarily with Agility (AGI) and secondarily with Intelligence (INTI).
     * Formula: base + (AGI * 0.5) + (INTI * 0.2)
     */
    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return base + (agi * 0.5) + (inti * 0.2);
        } catch (Exception e) {
            return base;
        }
    }

    /**
     * Critical chance increases with AGI and slightly with INTI.
     * baseCrit = 0.05; crit = base + AGI*0.01 + INTI*0.005
     */
    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.05;
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return baseCrit + (agi * 0.01) + (inti * 0.005);
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

    /**
     * Convenience: returns true if this attack should be treated as a backstab (hidden attacker).
     * Callers must determine "hidden" state; this is just a numeric helper.
     */
    public double applyBackstabMultiplier(boolean isHidden) {
        return isHidden ? 2.0 : 1.0; // backstab doubles damage when attacker is hidden
    }

    /**
     * Returns true if the character is a FULL_MEMBER of the Thief guild (required to buy/wield).
     */
    public static boolean isGuildMember(Charecter character) {
        if (character == null) return false;
        try {
            return character.getCurrentGuild() == GuildType.THIEF
                    && character.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Convenience for shop logic: whether the weapon may be purchased by the character.
     */
    public static boolean isPurchasableBy(Charecter character) {
        return isGuildMember(character);
    }

    /**
     * Equip the weapon if the wearer meets guild and strength requirements. Returns true on success.
     */
    @Override
    public boolean equip(Charecter wearer) {
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
     * ApplyCombatEffect: small chance to poison the target. Chance scales with INT.
     * Base chance = 0.15 + INT * 0.02 (capped at 0.8). Duration = 1 + INT/10.
     */
    @Override
    public void applyCombatEffect(Charecter target) {
        if (target == null) return;
        Charecter attacker = myChar != null ? myChar : Charecter.getInstance();
        int inti = (attacker != null) ? attacker.getIntelligence() : 0;
        double chance = 0.15 + (inti * 0.02);
        chance = Math.min(0.80, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, inti / 10);
            try { target.applyStatusEffect(StatusType.POISON_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}