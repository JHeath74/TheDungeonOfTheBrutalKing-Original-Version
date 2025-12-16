
package Guild.AuroraArcanum.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class MagicalRod extends WeaponManager {

    private static final int REQUIRED_INTELLIGENCE = 20;
    private static final int INTELLIGENCE_BONUS = 2;
    private static final int WISDOM_BONUS = 1;
    private static final double DEFENSE_BONUS = 0.08;
    private static final int ATTACK_DAMAGE = 4;
    private static final int WEIGHT = 4;

    private static final Guild GUILDname = Guild.AURORA_ARCNUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public MagicalRod(int requiredIntelligence, int damage, String effect, int weight) {
        super("Magical Rod", requiredIntelligence, damage, effect, weight);
    }

    public static MagicalRod createMagicalRod(Charecter character, int damage, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new MagicalRod(REQUIRED_INTELLIGENCE, damage, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wield the Magical Rod.");
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
        int bonus = rand.nextInt((intelligence / 2) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage);
    }

    public void absorbSpell(Charecter wielder) {
        // Logic for absorbing a spell and converting it to mana or health
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
        return "Magical Rod: A powerful conduit for specialized magic—absorbing spells, controlling elements, or commanding constructs. Shorter than a staff, but potent in the right hands.";
    }
}
