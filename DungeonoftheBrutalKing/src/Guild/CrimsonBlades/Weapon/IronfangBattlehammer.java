
// src/Guild/CrimsonBlades/Weapon/IronfangBattlehammer.java
package Guild.CrimsonBlades.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class IronfangBattlehammer extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 17;
    private static final int STRENGTH_BONUS = 4;
    private static final int AGILITY_BONUS = 0;
    private static final double DEFENSE_BONUS = 0.08;
    private static final int ATTACK_DAMAGE = 7;
    private static final int WEIGHT = 5;

    private static final Guild GUILDname = Guild.CRIMSON_BLADES;
    private static final GuildType GUILDtype = GuildType.WARRIOR;

    private boolean isEquipped = false;

    public IronfangBattlehammer(int requiredStrength, int damage, String effect, int weight) {
        super("Ironfang Battlehammer", requiredStrength, damage, effect, weight);
    }

    public static IronfangBattlehammer createIronfangBattlehammer(Charecter character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(2));
        if (strength >= REQUIRED_STRENGTH) {
            return new IronfangBattlehammer(REQUIRED_STRENGTH, damage, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Ironfang Battlehammer.");
    }

    public boolean equip(Charecter wielder) {
        if (!isEquipped) {
            wielder.setStrength(wielder.getStrength() + STRENGTH_BONUS);
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            int newDefense = (int) (wielder.getDefense() + DEFENSE_BONUS);
            wielder.setDefense(newDefense);
            isEquipped = true;
            return true;
        }
        return false;
    }

    public void unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setStrength(wielder.getStrength() - STRENGTH_BONUS);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            int newDefense = (int) (wielder.getDefense() - DEFENSE_BONUS);
            wielder.setDefense(newDefense);
            isEquipped = false;
        }
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        int strength = Integer.parseInt(wielder.getCharInfo().get(2));
        Random rand = new Random();
        int bonus = rand.nextInt((strength / 3) + 1);
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
        return "Ironfang Battlehammer: A brutal hammer forged for the Crimson Blades, delivering crushing blows and bolstering the wielder's strength and defense.";
    }
}
