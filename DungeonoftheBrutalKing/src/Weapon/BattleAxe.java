package Weapon;

import DungeonoftheBrutalKing.Charecter;
import Effect.EffectManager;

public class BattleAxe extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();
    private static int weight = 25;
    private static int damage = 35;
    private static final int REQUIRED_STRENGTH = 30;
    private EffectManager effectManager = new EffectManager();

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public BattleAxe(int damage, String effect, int weight) {
        super("Battle Axe", REQUIRED_STRENGTH, damage, effect, weight);
        BattleAxe.weight = weight;
    }

    public BattleAxe(int requiredStrength, int damage, String effect, int weight) {
        super("Battle Axe", requiredStrength, damage, effect, weight);
        BattleAxe.weight = weight;
    }

    public static BattleAxe createBattleAxe(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new BattleAxe(damage, effect, weight);
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
        return Integer.parseInt(myChar.getCharInfo().get(8));
    }

    @Override
    public double getWeight() {
        return (double) weight;
    }

    @Override
    public double getDamage() {
        return (double) damage;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
