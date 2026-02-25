
// src/Guild/DirgeweaversChorus/Weapon/RequiemboundWhisperbow.java
package Guild.DirgeweaversChorus.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Status.LifeStealStatus;
import Weapon.WeaponManager;

import java.util.Random;

public class RequiemboundWhisperbow extends WeaponManager {

    private static final int REQUIRED_CHARISMA = 12;
    private static final int CHARISMA_BONUS = 2;
    private static final int ATTACK_DAMAGE = 6;
    private static final int WEIGHT = 3;

    private static final int LIFE_STEAL_CHANCE_PERCENT = 25;
    private static final int LIFE_STEAL_AMOUNT = 2;
    private static final int LIFE_STEAL_DURATION = 2;

    private static final Guild REQUIRED_GUILD = Guild.DIRGEWEAVERS_CHORUS;
    private static final GuildType GUILD_TYPE = GuildType.BARD;

    private static final String WEAPON_NAME = "RequiemboundWhisperbow";
    private static final String DESCRIPTION =
            "RequiemboundWhisperbow: A whispering bow bound to a requiem vow, reserved for the Dirgeweavers Chorus. Grants charisma and may trigger Life Steal on hit.";

    private final Random random = new Random();
    private int lastCharismaBonus = 0;

    public RequiemboundWhisperbow(String effect) {
        super(WEAPON_NAME, REQUIRED_CHARISMA, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static RequiemboundWhisperbow createRequiemboundWhisperbow(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD) {
            throw new IllegalArgumentException("Only Dirgeweavers Chorus members can wield the RequiemboundWhisperbow.");
        }
        if (character.getCharisma() < REQUIRED_CHARISMA) {
            throw new IllegalArgumentException("Character does not have the required charisma to wield the RequiemboundWhisperbow.");
        }
        return new RequiemboundWhisperbow(effect);
    }

    private boolean isGuildMember(Charecter wielder) {
        return wielder != null && wielder.getGuild() == REQUIRED_GUILD;
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isGuildMember(wielder)) return false;

        if (wielder.getWeapon() == null || !wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(getName());
            lastCharismaBonus = CHARISMA_BONUS;
            wielder.setCharisma(wielder.getCharisma() + lastCharismaBonus);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;

        if (wielder.getWeapon() != null && wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(null);
            wielder.setCharisma(wielder.getCharisma() - lastCharismaBonus);
            lastCharismaBonus = 0;
            return true;
        }
        return false;
    }

    /**
     * Call after a successful hit where damage was dealt.
     *
     * @param attacker the character wielding the weapon
     * @param target the enemy that was hit
     * @param damageDealt final damage actually dealt
     */
    public void onHit(Charecter attacker, Enemies target, int damageDealt) {
        if (!isGuildMember(attacker)) return;

        if (target == null || target.isDead()) return;
        if (damageDealt <= 0) return;

        int roll = random.nextInt(100) + 1;
        if (roll > LIFE_STEAL_CHANCE_PERCENT) return;

        LifeStealStatus lifeSteal = new LifeStealStatus(LIFE_STEAL_AMOUNT, LIFE_STEAL_DURATION);

        // Apply immediately between attacker and target
        lifeSteal.apply(attacker, target);

        // Optional: track on target for UI/turn systems
        target.addStatus(lifeSteal);
    }

    public Guild getGuild() {
        return REQUIRED_GUILD;
    }

    public GuildType getGuildType() {
        return GUILD_TYPE;
    }

    @Override
    public String getName() {
        return WEAPON_NAME;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public double getDamage() {
        return ATTACK_DAMAGE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
