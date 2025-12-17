package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

public class ArcaneMissile implements Spell {
    private int basePower = 10;
    SharedData.GuildType guildType = SharedData.GuildType.WIZARD;
    

    public int calculatePower(Charecter charecter) {
        int intelligence = charecter.getIntelligence();
        int level = charecter.getLevel();
        return basePower + (intelligence * 2) + (level * 1);
    }


	@Override
	public boolean isGuildSpell() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Guild getSpellGuild() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getRequiredMagicPoints() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void cast(int toonWisdom) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void castWithIntelligence(int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cast(int toonWisdom, int toonIntelligence) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
