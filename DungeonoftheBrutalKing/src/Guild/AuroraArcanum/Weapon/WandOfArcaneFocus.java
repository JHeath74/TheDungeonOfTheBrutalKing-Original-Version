
package Guild.AuroraArcanum.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class WandOfArcaneFocus extends WeaponManager {

    private static final int REQUIRED_INTELLIGENCE = 18;
    private static final int INTELLIGENCE_BONUS = 1;
    private static final int WISDOM_BONUS = 1;
    private static final double DEFENSE_BONUS = 0.05;
    private static final int ATTACK_DAMAGE = 3;
    private static final int WEIGHT = 3;

    private static final Guild GUILDname = Guild.AURORA_ARCNUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public WandOfArcaneFocus(int requiredIntelligence, int damage, String effect, int weight) {
        super("Wand of Arcane Focus", requiredIntelligence, damage, effect, weight);
    }

    public static WandOfArcaneFocus createWandOfArcaneFocus(Charecter character, int damage, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new WandOfArcaneFocus(REQUIRED_INTELLIGENCE, damage, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wield the Wand of Arcane Focus.");
    }

    public void equip(Charecter wielder) {
        if (!isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() + INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() + WISDOM_BONUS);
            int newDefense = (int) (wielder.getDefense() + DEFENSE_BONUS);
            wielder.setDefense(newDefense);
            isEquipped = true;
        }
    }

    public void unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() - INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() - WISDOM_BONUS);
            int newDefense = (int) (wielder.getDefense() - DEFENSE_BONUS);
            wielder.setDefense(newDefense);
            isEquipped = false;
        }
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
        return "Wand of Arcane Focus: A slender, precise wand ideal for quick spellcasting, enchantments, and magical duels.";
    }
}
