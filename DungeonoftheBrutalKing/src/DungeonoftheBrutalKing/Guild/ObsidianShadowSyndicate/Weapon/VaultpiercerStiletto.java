package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Weapon.WeaponManager;

import java.util.concurrent.ThreadLocalRandom;

public class VaultpiercerStiletto extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.OBSIDIAN_SHADOW_SYNDICATE;

    private static final String WEAPON_NAME = "VaultpiercerStiletto";
    private static final String EFFECT =
            "VaultpiercerStiletto: A rigid stiletto built to punch through gaps in armor and locks alike. " +
            "Increases attack and sharpens technique via agility. " +
            "Has a chance to inflict a random debilitating status on hit.";

    private static final int WEIGHT = 2;

    private static final int REQUIRED_AGILITY = 15;

    private static final int ATTACK_INCREASE = 7;
    private static final int AGILITY_BONUS = 2;

    private static final int STATUS_PROC_CHANCE_PERCENT = 18;

    private int lastAttackBonus = 0;
    private int lastAgiBonus = 0;

    private static final String[] RANDOM_STATUSES = new String[] {
            "REDUCE_DEFENSE_STATUS",
            "BLEED_STATUS",
            "POISON_STATUS",
            "STUN_STATUS"
    };

    private VaultpiercerStiletto() {
        super(WEAPON_NAME, 0, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static VaultpiercerStiletto createVaultpiercerStiletto(Character character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Obsidian Shadow Syndicate members can wield the VaultpiercerStiletto.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wield the VaultpiercerStiletto.");

        return new VaultpiercerStiletto();
    }

    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastAgiBonus = AGILITY_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);

        return true;
    }

    public boolean unequip(Character wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastAgiBonus != 0) wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));

        lastAttackBonus = 0;
        lastAgiBonus = 0;

        return true;
    }

    public String tryApplyRandomStatus(Character attacker, Character target) {
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