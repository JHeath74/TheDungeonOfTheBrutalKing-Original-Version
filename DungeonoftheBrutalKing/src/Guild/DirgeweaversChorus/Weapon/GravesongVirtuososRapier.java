
// src/Guild/DirgeweaversChorus/Weapon/GravesongVirtuososRapier.java
package Guild.DirgeweaversChorus.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Status.BleedStatus;
import Weapon.WeaponManager;

import java.util.Random;

public class GravesongVirtuososRapier extends WeaponManager {

    private static final int ATTACK_DAMAGE = 2;
    private static final int WEIGHT = 2;

    private static final int BLEED_CHANCE_PERCENT = 15;
    private static final int BLEED_DURATION_TURNS = 3;

    private static final Guild GUILDname = Guild.DIRGEWEAVERS_CHORUS;
    private static final GuildType GUILDtype = GuildType.BARD;

    private final Random random = new Random();
    private boolean isEquipped = false;

    public GravesongVirtuososRapier(int requiredStat, int damage, int weight, String effect) {
        super("Gravesong Virtuoso's Rapier", requiredStat, damage, effect, weight);
    }

    public static GravesongVirtuososRapier createGravesongVirtuososRapier(int damage, String effect) {
        return new GravesongVirtuososRapier(0, damage, WEIGHT, effect);
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder != null && wielder.getGuild() == GUILDname) {
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
        if (attacker == null || attacker.getGuild() != GUILDname) return;

        if (target == null || target.isDead()) return;
        if (damageDealt <= 0) return;

        int roll = random.nextInt(100) + 1;
        if (roll > BLEED_CHANCE_PERCENT) return;

        target.addStatus(new BleedStatus());
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
        return "Gravesong Virtuoso's Rapier: A Dirgeweavers Chorus weapon that may inflict Bleed.";
    }
}
