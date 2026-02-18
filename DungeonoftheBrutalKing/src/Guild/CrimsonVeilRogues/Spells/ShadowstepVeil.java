
package Guild.CrimsonVeilRogues.Spells;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import Status.AccuracyStatus;

public class ShadowstepVeil {

    private static final Guild REQUIRED_GUILD = Guild.CRIMSON_VEIL_ROGUES;
    private static final String SKILL_NAME = "Shadowstep Veil";
    private static final String DESCRIPTION = "The rogue melts into a nearby shadow. For one turn, attacks from the targeted enemy have reduced accuracy.";
    private static final int ACCURACY_DEBUFF = -30; // Reduces enemy accuracy by 30
    private static final int DURATION = 1; // 1 turn

    public static boolean use(Charecter user, Enemies enemy) {
        if (user == null || enemy == null) return false;
        if (user.getGuild() != REQUIRED_GUILD) return false;

        AccuracyStatus debuff = new AccuracyStatus(
            "Shadowstep Veil Debuff",
            DURATION,
            true,
            ACCURACY_DEBUFF
        );
        enemy.addStatus(debuff); // Use the correct method to add a status

        return true;
    }

    public static String getName() {
        return SKILL_NAME;
    }

    public static String getDescription() {
        return DESCRIPTION;
    }
}
