// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Weapons\EbonBlowpipe.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import java.util.Random;

/**
 * EbonBlowpipe - a short-range stealth weapon that fires poisoned darts.
 * Low physical damage but applies a poison status whose chance scales with INT.
 * Requires full membership in the Obsidian Shadow Syndicate.
 */
public class EbonBlowpipe extends WeaponManager {

    private static final String WEAPON_NAME = "Ebon Blowpipe";
    private static final int REQUIRED_STRENGTH = 3;
    private static final int REQUIRED_AGILITY = 12;
    private static final int REQUIRED_INTELLIGENCE = 15;
    private static final int WEIGHT = 6;
    private static final int BASE_DAMAGE = 10;

    private static final Random RNG = new Random();
    private static Charecter myChar = Charecter.getInstance();

    public EbonBlowpipe(int damage, String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public EbonBlowpipe(int requiredStrength, int damage, String effect) {
        super(WEAPON_NAME, requiredStrength, damage, effect, WEIGHT);
    }

    public static EbonBlowpipe createEbonBlowpipe(Charecter character, int damage, String effect) {
        try {
            if (character == null) throw new IllegalArgumentException("Character is null");
            if (!isGuildMember(character))
                throw new IllegalArgumentException("Character must be a full member of the Obsidian Shadow Syndicate to obtain the Ebon Blowpipe.");
            if (character.getAgility() < REQUIRED_AGILITY)
                throw new IllegalArgumentException("Character does not have the required Agility to wield the Ebon Blowpipe.");
            if (character.getIntelligence() < REQUIRED_INTELLIGENCE)
                throw new IllegalArgumentException("Character does not have the required Intelligence to wield the Ebon Blowpipe.");
            return new EbonBlowpipe(damage, effect);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to create Ebon Blowpipe");
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
            return base + (agi * 0.25) + (inti * 0.15);
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
            return baseCrit + (agi * 0.008) + (inti * 0.006);
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
     * On hit: chance to apply POISON_STATUS. Chance = 15% base + INT * 0.02, capped at 85%.
     * If applied, duration scales with INT (1 + INT/10) turns.
     */
    @Override
    public void applyCombatEffect(Charecter target) {
        if (target == null) return;
        Charecter attacker = myChar != null ? myChar : Charecter.getInstance();
        int inti = (attacker != null) ? attacker.getIntelligence() : 0;
        double chance = 0.15 + (inti * 0.02);
        chance = Math.min(0.85, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, inti / 10);
            try { target.applyStatusEffect(StatusType.POISON_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}