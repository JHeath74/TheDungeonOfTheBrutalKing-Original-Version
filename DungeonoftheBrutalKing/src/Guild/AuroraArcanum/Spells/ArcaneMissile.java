package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;

public class ArcaneMissile {
    private int basePower = 10;
    SharedData.GuildType guildType = SharedData.GuildType.WIZARD;
    

    public int calculatePower(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return basePower + (intelligence * 2) + (level * 1);
    }
}
