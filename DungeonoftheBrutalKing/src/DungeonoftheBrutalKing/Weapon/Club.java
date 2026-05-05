
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Club extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 15;
    private static final int DEFAULT_WEIGHT = 20;
    private static final int DEFAULT_DAMAGE = 20;

    public Club(Character owner, String effect) {
        super("Club", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
        // Optionally, check owner's strength here and throw if not enough
    }

    public Club(int requiredStrength, int damage, String effect) {
        super("Club", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Club createClub(Character character, Character damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Club(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Club.");
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    @Override
    public double getDamage() {
        return super.getDamage();
    }

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
