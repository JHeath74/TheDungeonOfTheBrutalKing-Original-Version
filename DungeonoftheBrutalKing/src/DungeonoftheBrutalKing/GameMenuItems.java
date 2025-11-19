
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import SharedData.GameSettings;

public class GameMenuItems {

    Character myChar = Character.getInstance();
    GameSettings myGamePreferences = new GameSettings();

    public void Stats() {
        JFrame statsFrame = new JFrame("Charecter Stats");
        JTextArea statsArea = new JTextArea();
        JButton closeStats = new JButton("Ok");

        closeStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.dispose();
            }
        });

        statsFrame.add(statsArea, BorderLayout.CENTER);
        statsFrame.add(closeStats, BorderLayout.SOUTH);

        statsArea.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        statsArea.setForeground(myGamePreferences.getColorWhite());
        statsArea.setBackground(myGamePreferences.getColorBlack());
        statsArea.setVisible(true);

        statsFrame.setSize(400, 600);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
        statsFrame.toFront();
        statsFrame.requestFocus();

        statsArea.setText("Name: " + myChar.getName() + "\n");
        statsArea.append("Class: " + myChar.getClassName() + "\n");
        statsArea.append("Level: " + myChar.getLevel() + "\n");
        statsArea.append("Experience: " + myChar.getExperience() + "\n");
        statsArea.append("Location:  \n"
          + "\tDungeon level: " + (int)myChar.getZ() + "\n"
          + "\tX: " + (int)myChar.getX() + "\n"
          + "\tY: " + (int)myChar.getY() + "\n");
        statsArea.append("Hit Points: " + myChar.getHitPoints() + "\n");
        statsArea.append("Stamina: " + myChar.getStamina() + "\n");
        statsArea.append("Charisma: " + myChar.getCharisma() + "\n");
        statsArea.append("Strength: " + myChar.getStrength() + "\n");
        statsArea.append("Intelligence: " + myChar.getIntelligence() + "\n");
        statsArea.append("Wisdom: " + myChar.getWisdom() + "\n");
        statsArea.append("Agility: " + myChar.getAgility() + "\n");
        statsArea.append("Alignment: " + myChar.getAlignment() + "\n");
        statsArea.append("Equipped Weapon: " + myChar.getEquippedWeapon() + "\n");
        statsArea.append("Equipped Armor: " + myChar.getEquippedArmor() + "\n");
        statsArea.append("Equipped Shield: " + myChar.getEquippedShield() + "\n");
    }

    public void Inventory() {
        JFrame invFrame = new JFrame("Inventory");
        JTextArea invArea = new JTextArea();
        JButton closeStats = new JButton("Ok");

        closeStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invFrame.dispose();
            }
        });

        invFrame.add(invArea, BorderLayout.CENTER);
        invFrame.add(closeStats, BorderLayout.SOUTH);

        invArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        Color sab = new Color(25, 50, 75);
        Color sabText = new Color(255, 255, 255);
        invArea.setForeground(sabText);
        invArea.setBackground(sab);
        invArea.setVisible(true);

        invFrame.setLocationRelativeTo(null);
        invFrame.setSize(400, 600);
        invFrame.setVisible(true);
        invFrame.toFront();
        invFrame.requestFocus();

        StringBuilder sb = new StringBuilder();
        int width = 40;
        sb.append(String.format("%" + ((width + "Money".length()) / 2) + "s", "Money") + "\n");
        sb.append("Gold: ").append(myChar.getGold()).append("\n");
        sb.append("Gems: ").append(myChar.getGems()).append("\n");
        sb.append(String.format("%" + ((width + "Misc".length()) / 2) + "s", "Misc") + "\n");
        sb.append("Food: ").append(myChar.getFood()).append("\n");
        sb.append("Torches: ").append(myChar.getTorches()).append("\n");
        sb.append(String.format("%" + ((width + "Weapons and Armour".length()) / 2) + "s", "Weapons and Armour") + "\n");
        sb.append("Equipped Weapon: ").append(myChar.getEquippedWeapon()).append("\n");
        sb.append("Equipped Armor: ").append(myChar.getEquippedArmor()).append("\n");
        sb.append("Equipped Shield: ").append(myChar.getEquippedShield()).append("\n");
        sb.append(String.format("%" + ((width + "Your Spells".length()) / 2) + "s", "Your Spells") + "\n");
        if (myChar.getSpellsLearned() != null && !myChar.getSpellsLearned().isEmpty()) {
            for (String spell : myChar.getSpellsLearned()) {
                sb.append(spell).append("\n");
            }
        } else {
            sb.append("No spells learned yet.\n");
        }
        invArea.setText(sb.toString());
    }
}
