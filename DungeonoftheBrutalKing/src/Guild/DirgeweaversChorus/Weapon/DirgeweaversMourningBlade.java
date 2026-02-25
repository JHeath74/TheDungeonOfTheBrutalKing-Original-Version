
// src/Guild/DirgeweaversChorus/Weapon/DirgeweaversMourningBlade.java
package Guild.DirgeweaversChorus.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Status.PoisonStatus;
import Weapon.WeaponManager;

import java.util.Random;

public class DirgeweaversMourningBlade extends WeaponManager {

    public DirgeweaversMourningBlade(String name, int requiredStrength, int damage, String effect, int weight) {
		super(name, requiredStrength, damage, effect, weight);
		// TODO Auto-generated constructor stub
	}

	private static final String WEAPON_NAME = "DirgeweaversMourningBlade";
    private static final String DESCRIPTION =
            "DirgeweaversMourningBlade: A mourning blade that may poison those it cuts.";

    private static final int POISON_CHANCE_PERCENT = 15;
    private static final int POISON_DURATION_TURNS = 3;

    private static final Guild REQUIRED_GUILD = Guild.DIRGEWEAVERS_CHORUS;

    private final Random random = new Random();

    private boolean isGuildMember(Charecter wielder) {
        return wielder != null && wielder.getGuild() == REQUIRED_GUILD;
    }

    /**
     * Call after a successful hit where damage was dealt.
     *
     * @param attacker the character wielding the weapon
     * @param target the enemy that was hit
     * @param damageDealt final damage actually dealt
     */
    public void onHit(Charecter attacker, Enemies target, int damageDealt) {
        if (!isGuildMember(attacker)) return;

        if (target == null || target.isDead()) return;
        if (damageDealt <= 0) return;

        int roll = random.nextInt(100) + 1;
        if (roll > POISON_CHANCE_PERCENT) return;

        target.addStatus(new PoisonStatus(POISON_DURATION_TURNS));
    }

    public String getName() {
        return WEAPON_NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
