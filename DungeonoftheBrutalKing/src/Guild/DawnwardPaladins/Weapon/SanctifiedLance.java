
package Guild.DawnwardPaladins.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;

public class SanctifiedLance extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 14;
    private static final int STRENGTH_BONUS = 1;
    private static final int DEFENSE_BONUS = 2;
    private static final int ATTACK_DAMAGE = 13;
    private static final int WEIGHT = 4;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public SanctifiedLance(int requiredStrength, int damage, int weight, String effect) {
        super("Sanctified Lance", requiredStrength, damage, effect, weight);
    }

    public static SanctifiedLance createSanctifiedLance(Charecter character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new SanctifiedLance(REQUIRED_STRENGTH, damage, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Sanctified Lance.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder.getGuild() == GUILDname) {
            wielder.setStrength(wielder.getStrength() + STRENGTH_BONUS);
            wielder.setDefense(wielder.getDefense() + DEFENSE_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setStrength(wielder.getStrength() - STRENGTH_BONUS);
            wielder.setDefense(wielder.getDefense() - DEFENSE_BONUS);
            isEquipped = false;
        }
        return isEquipped;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        int strength = Integer.parseInt(wielder.getCharInfo().get(3));
        int bonus = strength / 7;
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage);
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public String getName() {
        return super.getName();
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
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Sanctified Lance: A lance imbued with holy power, piercing the armor of evil foes.";
    }
}
