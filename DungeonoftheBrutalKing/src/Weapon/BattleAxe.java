
// BattleAxe.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.EffectManager;
import DungeonoftheBrutalKing.WeaponManager;

public class BattleAxe extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();


    private String weaponname = "Battle Axe";
    private static double weight = 25.0;
    private static double damage = 35;
    private static final int REQUIRED_STRENGTH = 30;





	private EffectManager effectManager = new EffectManager();


    public EffectManager getEffectManager() {
        return effectManager;
    }

    public BattleAxe(double damage, String effect) {
        super("Battle Axe", REQUIRED_STRENGTH, damage, effect, weight);
        this.weaponname = "Battle Axe";
        BattleAxe.weight = weight;

    }

    public BattleAxe(int requiredStrength, double damage, String effect) {
        super("Battle Axe", requiredStrength, damage, effect, weight);
        this.weaponname = "Battle Axe";
        BattleAxe.weight = weight;
    }

    public static BattleAxe createBattleAxe(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new BattleAxe(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    @Override
	public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return Integer.parseInt(myChar.CharInfo.get(8));
    }


    @Override
	public double getWeight() {
        return weight;
    }

    @Override
	public double getDamage() {
		return damage;
	}


    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
	public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
