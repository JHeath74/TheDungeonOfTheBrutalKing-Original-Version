
package Guild.DirgeweaversChorus.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Status.FireStatus;
import Status.IceStatus;
import Status.LightningStatus;
import Weapon.WeaponManager;
import java.util.Random;

public class NocturneOfTheBlackRoseScepter extends WeaponManager {

    private static final int REQUIRED_STAT = 0;
    private static final int ATTACK_DAMAGE = 2;
    private static final int WEIGHT = 2;

    private static final Guild GUILDname = Guild.DIRGEWEAVERS_CHORUS;
    private static final GuildType GUILDtype = GuildType.BARD;

    // Element proc tuning
    private static final int ELEMENT_PROC_CHANCE_PERCENT = 15;
    private static final int ELEMENT_DURATION_TURNS = 3;

    private final Random random = new Random();
    private boolean isEquipped = false;

    private enum Element {
        FIRE, ICE, LIGHTNING
    }

    public NocturneOfTheBlackRoseScepter(int requiredStat, int damage, int weight, String effect) {
        super("Nocturne of the Black Rose Scepter", requiredStat, damage, effect, weight);
    }

    public static NocturneOfTheBlackRoseScepter createNocturneOfTheBlackRoseScepter(int damage, String effect) {
        return new NocturneOfTheBlackRoseScepter(REQUIRED_STAT, damage, WEIGHT, effect);
    }

    private boolean isGuildMember(Charecter wielder) {
        return wielder != null && wielder.getGuild() == GUILDname;
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && isGuildMember(wielder)) {
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            isEquipped = false;
        }
        return isEquipped;
    }

    /**
     * Call after a successful hit where damage was dealt.
     */
    public void onHit(Charecter attacker, Enemies target, int damageDealt) {
        if (!isEquipped) return;
        if (!isGuildMember(attacker)) return;

        if (target == null || target.isDead()) return;
        if (damageDealt <= 0) return;

        // roll proc chance
        int roll = random.nextInt(100) + 1;
        if (roll > ELEMENT_PROC_CHANCE_PERCENT) return;

        // randomly pick element and apply its status
        Element element = Element.values()[random.nextInt(Element.values().length)];
        switch (element) {
            case FIRE:
                target.addStatus(new FireStatus());
                break;
            case ICE:
                target.addStatus(new IceStatus());
                break;
            case LIGHTNING:
                target.addStatus(new LightningStatus(ELEMENT_DURATION_TURNS));
                break;
        }
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public double getWeight() {
        return (double) WEIGHT;
    }

    @Override
    public double getDamage() {
        return (double) ATTACK_DAMAGE;
    }

    @Override
    public String getDescription() {
        return "Nocturne of the Black Rose Scepter: A Dirgeweavers Chorus scepter with a chance to apply Fire, Ice, or Lightning.";
    }
}
