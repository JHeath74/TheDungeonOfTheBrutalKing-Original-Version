
// File: AuroraArcanum.java
package Guild;

import javax.swing.*;
import DungeonoftheBrutalKing.Charecter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class HarmonicLightEnsemble extends JPanel {

    private static final int BASE_SPELL_LIMIT = 0;
    private static final int MAX_SPELL_LIMIT = 6;
    private static final int SPELL_COST = 50; // Cost of each spell in gold

    public HarmonicLightEnsemble(boolean isMember) throws IOException, InterruptedException, ParseException {

        JButton buySpellsButton = new JButton("Buy Spells");

        buySpellsButton.addActionListener(event -> {
            Charecter character = Charecter.Singleton();
            ArrayList<String> spells = character.GuildSpells; // Guild spells list
            int wisdom = Integer.parseInt(character.CharInfo.get(10)); // Wisdom value
            int gold = Integer.parseInt(character.CharInfo.get(12)); // Gold value

            if (!isMember) {
                JOptionPane.showMessageDialog(this, "You must be a member of the guild to buy spells.");
                return;
            }

            if (gold < SPELL_COST) {
                JOptionPane.showMessageDialog(this, "You do not have enough gold to buy a spell.");
                return;
            }

            int spellLimit = BASE_SPELL_LIMIT + Math.min(wisdom / 8, MAX_SPELL_LIMIT - BASE_SPELL_LIMIT);

            if (spells.size() >= spellLimit) {
                JOptionPane.showMessageDialog(this, "You cannot carry more spells.");
                return;
            }

            String newSpell = JOptionPane.showInputDialog(this, "Enter the name of the spell to buy:");
            if (newSpell != null && !newSpell.isEmpty()) {
                spells.add(newSpell);
                character.updateGold(gold - SPELL_COST); // Deduct gold
                JOptionPane.showMessageDialog(this, "You purchased the spell: " + newSpell);
            }
        });

        add(buySpellsButton);
    }
}
