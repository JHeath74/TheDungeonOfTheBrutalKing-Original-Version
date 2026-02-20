
package Guild.DawnwardPaladins.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;

public class DivineMace extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 10;
    private static final int STRENGTH_BONUS = 1;
    private static final int ATTACK_DAMAGE = 10;
    private static final int WEIGHT = 3;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public DivineMace(int requiredStrength, int damage, int weight, String effect) {
        super("Divine Mace", requiredStrength, damage, effect, weight);
    }

    public static DivineMace createDivineMace(Charecter character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new DivineMace(REQUIRED_STRENGTH, damage, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Divine Mace.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder.getGuild() == GUILDname) {
            wielder.setStrength(wielder.getStrength() + STRENGTH_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setStrength(wielder.getStrength() - STRENGTH_BONUS);
            isEquipped = false;
        }
        return isEquipped;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        int strength = Integer.parseInt(wielder.getCharInfo().get(3));
        int bonus = strength / 8;
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
        return "Divine Mace: A mace blessed by the gods, dealing extra damage to undead.";
    }
}
