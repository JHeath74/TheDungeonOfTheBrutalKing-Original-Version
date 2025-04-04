
package Spells;

import DungeonoftheBrutalKing.Spells;
import DungeonoftheBrutalKing.Charecter;

public class Shield extends Spells {

    public Shield() {
        String name = "Shield";
        int requiredint = 30;
        int requiredwis = 30;
        String charintelligence = myChar.myCharSingleton().CharInfo.get(8).toString();
        String charwisdom = myChar.myCharSingleton().CharInfo.get(9).toString();
       
    }

    public void castSpell() {
        Charecter character = Charecter.Singleton();
        int currentDefense = Integer.parseInt(character.CharInfo.get(23).toString());
        int extraDefense = 10; // Define the extra defense value
        int newDefense = currentDefense + extraDefense;
        character.CharInfo.set(23, String.valueOf(newDefense));
    }
}
