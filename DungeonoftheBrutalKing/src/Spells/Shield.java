
package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.EffectManager;
import DungeonoftheBrutalKing.Spells;
import DungeonoftheBrutalKing.TimeClock;

public class Shield extends Spells {

	 private int duration = 12; // Duration in in-game hours
	 private TimeClock timeClock = TimeClock.Singleton();
	 private EffectManager effectManager = EffectManager.Singleton();
	 Charecter myChar = Charecter.Singleton();

    public Shield() {
        String name = "Shield";
        int requiredint = 30;
        int requiredwis = 30;
        String charintelligence = Charecter.Singleton().CharInfo.get(8).toString();
        String charwisdom = Charecter.Singleton().CharInfo.get(9).toString();

    }


public void castSpell() {
    int startTime = timeClock.getCurrentHour(); // Record the start time
    Charecter character = Charecter.Singleton();
    int currentDefense = Integer.parseInt(character.CharInfo.get(23).toString());
    int extraDefense = 10; // Define the extra defense value
    int newDefense = currentDefense + extraDefense;
    character.CharInfo.set(23, String.valueOf(newDefense));

    // Register the effect with EffectManager
    effectManager.registerEffect("Shield", startTime, duration, this::removeSpellEffect);
}


    private void removeSpellEffect() {
        Charecter character = Charecter.Singleton();
        int currentDefense = Integer.parseInt(character.CharInfo.get(23).toString());
        int extraDefense = 10; // Match the extra defense value added
        int newDefense = currentDefense - extraDefense;
        character.CharInfo.set(23, String.valueOf(newDefense));
    }
}
