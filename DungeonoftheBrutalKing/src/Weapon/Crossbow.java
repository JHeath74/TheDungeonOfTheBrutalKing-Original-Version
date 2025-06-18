package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Crossbow extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static double weight = 20.0;
	private static int damage = 20;
	private static String weaponName = "Crossbow";



    @Override
	public void setWeight(double weight) {
        Crossbow.weight = weight;
    }

    @Override
	public double getWeight() {
		return weight;
	}

	@Override
	public double getDamage() {
		return damage;
	}



    public Crossbow(double damage, String effect) {
        super("Club", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Crossbow(int requiredStrength, double damage, String effect) {
        super("Crossbow", requiredStrength, damage, effect, weight);
    }

    public static Crossbow createCrossbow(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Crossbow(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    // Getters and Setters
    @Override
	public String getName() {
        return name;
    }

    public int getRequiredStrength() {

        return REQUIRED_STRENGTH;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
	public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
