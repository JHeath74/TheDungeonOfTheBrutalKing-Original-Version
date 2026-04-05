// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Weapons\RavenTalons.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import java.util.Random;

/**
 * RavenTalons - lightweight dual-claw gloves that favor Agility and cause bleeding.
 * Designed for agile thieves: high damage scaling with AGI, chance to apply BLEED_STATUS
 * that scales with AGI and INT. Requires full membership in the Obsidian Shadow Syndicate.
 */
public class RavenTalons extends WeaponManager {

    private static final String WEAPON_NAME = "Raven Talons";
    private static final int REQUIRED_STRENGTH = 2;
    private static final int REQUIRED_AGILITY = 15;
    private static final int REQUIRED_INTELLIGENCE = 12;
    private static final int WEIGHT = 12;
    private static final int BASE_DAMAGE = 24;

    private static final Random RNG = new Random();
    private static Charecter myChar = Charecter.getInstance();

    public RavenTalons(int damage, String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public RavenTalons(int requiredStrength, int damage, String effect) {
        super(WEAPON_NAME, requiredStrength, damage, effect, WEIGHT);
    }

    public static RavenTalons createRavenTalons(Charecter character, int damage, String effect) {
        try {
            if (character == null) throw new IllegalArgumentException("Character is null");
            if (!isGuildMember(character))
                throw new IllegalArgumentException("Character must be a full member of the Obsidian Shadow Syndicate to obtain the Raven Talons.");
            if (character.getAgility() < REQUIRED_AGILITY)
                throw new IllegalArgumentException("Character does not have the required Agility to wield the Raven Talons.");
            if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
                throw new IllegalArgumentException("Character does not have the required Intelligence to wield the Raven Talons.");
            return new RavenTalons(damage, effect);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to create Raven Talons");
        }
    }

    @Override
    public String getName() { return this.name; }

    public int getRequiredStrength() { return REQUIRED_STRENGTH; }
    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return base + (agi * 0.6) + (inti * 0.15);
        } catch (Exception e) {
            return base;
        }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.06;
        try {
            int agi = (myChar != null) ? myChar.getAgility() : 0;
            int inti = (myChar != null) ? myChar.getIntelligence() : 0;
            return baseCrit + (agi * 0.012) + (inti * 0.004);
        } catch (Exception e) {
            return baseCrit;
        }
    }

    public StatusType getEffect() { return super.getStatusEffect(); }
    @Override
    public void setEffect(String effect) { super.setEffect(effect); }

    public static boolean isGuildMember(Charecter character) {
        if (character == null) return false;
        try {
            return character.getCurrentGuild() == GuildType.THIEF
                    && character.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPurchasableBy(Charecter character) { return isGuildMember(character); }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        try { wearer.setWeapon(this.getName()); return true; } catch (Exception e) { return false; }
    }

    /**
     * On hit: chance to apply BLEED_STATUS. Chance = 18% base + AGI*0.015 + INT*0.01, capped at 90%.
     * Duration = 1 + (AGI / 10) turns.
     */
    @Override
    public void applyCombatEffect(Charecter target) {
        if (target == null) return;
        Charecter attacker = myChar != null ? myChar : Charecter.getInstance();
        int agi = (attacker != null) ? attacker.getAgility() : 0;
        int inti = (attacker != null) ? attacker.getIntelligence() : 0;
        double chance = 0.18 + (agi * 0.015) + (inti * 0.01);
        chance = Math.min(0.90, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, agi / 10);
            try { target.applyStatusEffect(StatusType.BLEED_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}