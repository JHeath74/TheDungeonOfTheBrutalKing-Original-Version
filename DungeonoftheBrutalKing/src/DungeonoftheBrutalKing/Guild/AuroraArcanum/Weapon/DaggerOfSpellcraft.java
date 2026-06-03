
package DungeonoftheBrutalKing.Guild.AuroraArcanum.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import java.util.Random;

public class DaggerOfSpellcraft extends WeaponManager {

    private static final int REQUIRED_INTELLIGENCE = 14;
    private static final int INTELLIGENCE_BONUS = 1;
    private static final int AGILITY_BONUS = 2;
    private static final double DEFENSE_BONUS = 0.03;
    private static final int ATTACK_DAMAGE = 2;
    private static final int WEIGHT = 1;

    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public DaggerOfSpellcraft(int requiredIntelligence, int damage, int weight, String effect) {
        super("Dagger of Spellcraft", requiredIntelligence, damage, effect, weight);
    }

    public static DaggerOfSpellcraft createDaggerOfSpellcraft(Character character, int damage, String effect) {
        int intelligence = Integer.parseInt(character.getCharInfo().get(6));
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new DaggerOfSpellcraft(REQUIRED_INTELLIGENCE, damage, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wield the Dagger of Spellcraft.");
    }

    @Override
    public boolean equip(Character wielder) {
        if (!isEquipped && wielder.getGuild() == GUILDname) {
            wielder.setIntelligence(wielder.getIntelligence() + INTELLIGENCE_BONUS);
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            int bonus = (int) Math.round(wielder.getDefense() * DEFENSE_BONUS);
            wielder.setDefense(wielder.getDefense() + bonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wielder) {
        if (isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() - INTELLIGENCE_BONUS);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            int bonus = (int) Math.round(wielder.getDefense() * DEFENSE_BONUS / (1 + DEFENSE_BONUS));
            wielder.setDefense(wielder.getDefense() - bonus);
            isEquipped = false;
        }
		return isEquipped;
    }

    public void attackDamage(Character wielder, Enemies enemy) {
        int intelligence = Integer.parseInt(wielder.getCharInfo().get(6));
        Random rand = new Random();
        int bonus = rand.nextInt((intelligence / 4) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage,null);
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
        return "Dagger of Spellcraft: A swift, enchanted dagger designed for quick magical strikes and agile spellcasters.";
    }
}
