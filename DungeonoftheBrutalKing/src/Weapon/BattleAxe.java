
// BattleAxe.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.EffectManager;
import DungeonoftheBrutalKing.WeaponManager;

public class BattleAxe extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    
    private String weaponname;
    private EffectManager effectManager = new EffectManager();


    public EffectManager getEffectManager() {
        return effectManager;
    }

    public BattleAxe(int attackDamage, String effect) {
        super("Battle Axe", REQUIRED_STRENGTH, attackDamage, effect);
        this.weaponname = "Battle Axe";
        
    }

    public BattleAxe(int requiredStrength, int attackDamage, String effect) {
        super("Battle Axe", requiredStrength, attackDamage, effect);
        this.weaponname = "Battle Axe";
    }

    public static BattleAxe createBattleAxe(Character character, int attackDamage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new BattleAxe(attackDamage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return Integer.parseInt(myChar.CharInfo.get(8));
    }

    public void setRequiredStrength(int requiredStrength) {
        super.setRequiredStrength(requiredStrength);
    }

    public int getAttackDamage() {
        return super.getAttackDamage();
    }

    public void setAttackDamage(int attackDamage) {
        super.setAttackDamage(attackDamage);
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
