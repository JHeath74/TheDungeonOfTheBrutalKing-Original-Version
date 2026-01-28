
package Guild.AuroraArcanum.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.EquipmentRequirement;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class WandOfArcaneFocus extends WeaponManager {

    private static final EquipmentRequirement REQUIREMENT = EquipmentRequirement.WAND_OF_ARCANE_FOCUS;
    private static final int INTELLIGENCE_BONUS = 1;
    private static final int WISDOM_BONUS = 1;
    private static final double DEFENSE_BONUS = 0.05;
    private static final int ATTACK_DAMAGE = 3;

    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;
    private int lastDefenseBonus = 0;

    public WandOfArcaneFocus(int requiredIntelligence, int damage, String effect, int weight) {
        super("Wand of Arcane Focus", requiredIntelligence, damage, effect, weight);
    }

    public static WandOfArcaneFocus createWandOfArcaneFocus(Charecter character, int damage, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIREMENT.getIntelligence()) {
            return new WandOfArcaneFocus(REQUIREMENT.getIntelligence(), damage, effect, REQUIREMENT.getWeight());
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wield the Wand of Arcane Focus.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() + INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() + WISDOM_BONUS);
            lastDefenseBonus = (int) Math.round(wielder.getDefense() * DEFENSE_BONUS);
            wielder.setDefense(wielder.getDefense() + lastDefenseBonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() - INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() - WISDOM_BONUS);
            wielder.setDefense(wielder.getDefense() - lastDefenseBonus);
            isEquipped = false;
        }
		return isEquipped;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        int intelligence = wielder.getIntelligence();
        Random rand = new Random();
        int bonus = rand.nextInt((intelligence / 3) + 1);
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
        return (double) REQUIREMENT.getWeight();
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
        return "Wand of Arcane Focus: A slender, precise wand ideal for quick spellcasting, enchantments, and magical duels.";
    }
}
